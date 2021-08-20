# OnlineExams——在线测验平台

一些部署和配置的细节会放在博客上，所使用的到的技术栈会在这里更新。

## JWT

> JWT是JSON WEB TOKEN的缩写，它是基于 RFC 7519 标准定义的一种可以安全传输的的JSON对象，由于使用了数字签名，所以是可信任和安全的。

### JWT的组成

+ JWT token的格式：header.payload.signature

+ header中用于存放签名的生成算法

  ```json
  {"alg": "HS512"}
  ```

+ payload中用于存放用户名、token的生成时间、过期时间和分发时间等

  ```json
  {"sub":"admin","created":1489079981393,"exp":1489684781,"iat":1489684781}
  ```

+ signature为以header和payload生成的签名，一旦header和payload被篡改，验证将失败

  ```java
  //secret为加密算法的密钥
  String signature = HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
  ```

### JWT实例

这是一个JWT的字符串

```json
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaGVybWFuIiwibmJmIjoxNjI5MDk5OTIxLCJjcmVhdGVkIjoxNjI5MDk5OTIxODg1LCJpc0FkbWluIjp0cnVlLCJleHAiOjE2MjkxMTQzMjEsImlhdCI6MTYyOTA5OTkyMX0.rk2RyFerDcO4FXRc3VXPDT5eMQqu0RrQeSgCE93zf7T01_ha3FgNBHatbTTi8hlziKmBdA_SpYej_S8erjFqrw
```

可以在该网站上获得解析结果：https://www.box3.cn/tools/jwt.html

![jwt](https://cdn.jsdelivr.net/gh/GodWuzZ/CDN@1.4/pics/jwt.png)

### JWT实现认证和授权的原理

- 用户调用登录接口，登录成功后获取到JWT的token；

- 之后用户每次调用接口都在http的header中添加一个叫Authorization的头，值为JWT的token；

- 后台程序通过对Authorization头中信息的解码及数字签名校验来获取其中的用户信息。

- 由于管理员和考生的是独立的两张表，**所以在token中增加了一个`isAdmin`字段表示用户身份**，方便后续根据这个身份标识进入不同的filter。

- jwt是**无状态**的认证授权方式，但是在实际应用中考虑到如果同一个管理员账号同时刻有多个用户在线，可能会造成一些意料之外的状况，所以项目整合redis对同一账号在线人数做了一些约束。

  > + 首先在token中添加iat(issued at)字段，表示token的分发时间。
  > + 每次用户登录时在redis中更新当前登录的时间。
  > + 每次发起http请求解析token字段时做一个额外的判断，**如果iat在当前账号上次登录时间之前，则token失效需要重新登录**。

### 核心类分析

`JwtTokenUtil`——JwtToken生成的工具类，主要的作用如下：

+ 根据用户登录信息生成token
+ 从token中获取指定的信息
+ 判断token是否过期

## SpringSecurity

使用springsecurity进行权限控制。和权限控制有关的表共有三张，admin管理员表、role角色表、permission权限表，全部是多对多的关系。三张表中的每条记录都有一个`status`字段表示禁用或者启用。

大部分接口都使用了`@PreAuthorize("hasAuthority('ums:admin:read')")`注解设置了访问所需的权限。

### 核心类分析

#### SecurityConfig

`SecurityConfig`——springsecurity的核心配置类。

##### 相关依赖和方法说明

- configure(HttpSecurity httpSecurity)：用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；

- configure(AuthenticationManagerBuilder auth)：用于配置UserDetailsService及PasswordEncoder；

- RestfulAccessDeniedHandler：当用户没有访问权限时的处理器，用于返回JSON格式的处理结果；

- RestAuthenticationEntryPoint：当未登录或token失效时，返回JSON格式的结果；

- UserDetailsService:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；

- UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；

- PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；

- JwtAuthenticationTokenFilter：在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。

  ```java
  @Bean
      public UserDetailsService userDetailsService() {
          //获取登录用户信息
          return username -> {
              UmsAdmin admin = adminService.getAdminByUsername(username);
              if (admin != null) {
                  List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                  return new AdminUserDetails(admin,permissionList);
              }
              throw new UsernameNotFoundException("用户名或密码错误");
          };
      }
  ```

  UserDetailsService是需要自己实现的核心接口，这里我们实现了`loadUserByUsername`方法，返回了用户信息和对应的权限(permission)。

#### JwtAuthenticationTokenFilter

`JwtAuthenticationTokenFilter`——JWT登录授权过滤器，整合security和jwt的核心类。

##### 授权流程

+ 获取token中的用户名，并调用实现的`UserDetailsService`接口的`loadUserByUserName`方法获取用户信息和权限。
+ 判断token是否合法，判断是否此账号是否被其他人登录需要重新认证。
+ 将用户信息和权限信息注入，完成用户认证。
+ 根据用户具有的权限判断是否能够访问当前接口。

#### 其他

`RestfulAccessDeniedHandler`——当访问接口没有权限时，自定义的返回结果。

`RestAuthenticationEntryPoint`——当未登录或者token失效访问接口时，自定义的返回结果。

## Websocket

整合websocket实现广播消息和单点消息的实时推送，主要应用为公告的接收和自动保存。

```java
// 此为广播消息
    public void sendAllMessage(String message,Object data) {
        JSONObject result=new JSONObject();
        Map<String,Object>map = new HashMap<>();
        map.put("message",message);
        map.put("data",data);
        result.putAll(map);
        for(WebSocket webSocket : webSockets) {
            LOGGER.info("[websocket消息]广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String username, String message) {
        JSONObject result=new JSONObject();
        result.putOnce("message",message);
        LOGGER.info("[websocket消息]单点消息:"+message);
        Session session = sessionPool.get(username);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

## Mybatis generator

使用mybatis generator自动生成model和mapper文件。

### 配置文件

`generatorConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <properties resource="generator.properties"/>
    <context id="MySqlContext" targetRuntime="MyBatis3" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <property name="javaFileEncoding" value="UTF-8"/>
        <!-- 为模型生成序列化方法-->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
        <!-- 为生成的Java模型创建一个toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
        <!--可以自定义生成model的代码注释-->
        <commentGenerator type="sast.onlineexams.mbg.CommentGenerator">
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="true"/>
            <property name="suppressDate" value="true"/>
            <property name="addRemarkComments" value="true"/>
        </commentGenerator>
        <!--配置数据库连接-->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.connectionURL}"
                        userId="${jdbc.userId}"
                        password="${jdbc.password}">
            <!--解决mysql驱动升级到8.0后不生成指定数据库代码的问题-->
            <property name="nullCatalogMeansCurrent" value="true" />
        </jdbcConnection>
        <!--指定生成model的路径-->
        <javaModelGenerator targetPackage="sast.onlineexams.mbg.model" targetProject="src\main\java"/>
        <!--指定生成mapper.xml的路径-->
        <sqlMapGenerator targetPackage="sast.onlineexams.mbg.mapper" targetProject="src\main\resources"/>
        <!--指定生成mapper接口的的路径-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="sast.onlineexams.mbg.mapper"
                             targetProject="src\main\java"/>
        <!--生成全部表tableName设为%-->
        <table tableName="cms_answers">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>

```

## 自定义封装返回类

自定义`CommonResult`类作为通用的返回对象，同时使用`@JsonView()`注解指定返回的字段。

> + 有些字段在返回给前端时是需要隐藏的，例如密码和答案。
> + 可以在dto中自定义传输对象，也可以用`@JsonView`或`@JsonIgnore`注解设置。

