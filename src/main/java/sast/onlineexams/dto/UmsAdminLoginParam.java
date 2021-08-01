package sast.onlineexams.dto;


import javax.validation.constraints.NotEmpty;

/**
 * @author sherman
 * @create 2021-08-01 9:34
 * @description 用户登录参数
 */
public class UmsAdminLoginParam {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
