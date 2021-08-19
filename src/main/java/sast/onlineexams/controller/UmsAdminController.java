package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.common.utils.RedisUtil;
import sast.onlineexams.dto.UmsAdminLoginParam;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsGroups;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.service.UmsAdminService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-01 9:30
 * @description 后台用户管理
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UmsAdminController.class);
    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${redis.key.token.prefix}")
    private String redisPrefix;
    @Value("${redis.key.token.expire}")
    private Long redisExpiration;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed("注册失败");
        }
        return CommonResult.success(umsAdmin,"管理员注册成功");
    }

    @JsonView(UmsAdmin.AdminSimpleView.class)
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        redisUtil.set(redisPrefix+"admin_"+umsAdminLoginParam.getUsername(),new Date());
        redisUtil.expire(redisPrefix+"admin_"+umsAdminLoginParam.getUsername(),redisExpiration);
        umsAdminService.updateLoginTime(umsAdminLoginParam.getUsername());
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        UmsAdmin admin = umsAdminService.getAdminByUsername(umsAdminLoginParam.getUsername());
        tokenMap.put("adminInfo",admin);
        return CommonResult.success(tokenMap,"管理员登录成功");
    }

    @PreAuthorize("hasAuthority('ums:admin:read')")
    @GetMapping("/permission/{adminId}")
    public CommonResult<List<UmsPermission>>getPermissionList(@PathVariable Long adminId){
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList,"管理员用户权限");
    }

    @JsonView(UmsAdmin.AdminSimpleView.class)
    @PreAuthorize("hasAuthority('ums:admin:create')")
    @PostMapping("/userInfo")
    public CommonResult<UmsAdmin> updateAdmin(@RequestBody UmsAdmin umsAdmin){
        int flag=0;
        if (umsAdmin.getId()!=null)
            flag=1;
        UmsAdmin admin = umsAdminService.updateAdmin(umsAdmin);
        if (admin==null)
            return CommonResult.failed("用户名重复");
        if(flag==0)
            return CommonResult.success(admin,"管理员添加成功");
        return CommonResult.success(admin,"管理员修改成功");
    }

    @PreAuthorize("hasAuthority('ums:admin:delete')")
    @DeleteMapping("/userInfo")
    public CommonResult<Long> deleteAdmin(@RequestParam long id){
        umsAdminService.deleteAdmin(id);
        return CommonResult.success(null,"管理员删除成功");
    }

    @JsonView(UmsAdmin.AdminSimpleView.class)
    @PreAuthorize("hasAuthority('ums:admin:read')")
    @GetMapping("/userInfo")
    public CommonResult adminList(){
        Map<String,Object>map=new HashMap<>();
        map.put("adminList",umsAdminService.adminList());
        map.put("roles",umsAdminService.getRoleList());
        map.put("groups",umsAdminService.getGroups());
        return CommonResult.success(map,"获取管理员列表成功");
    }

    @PreAuthorize("hasAuthority('ums:groups:create')")
    @PostMapping("/group")
    public CommonResult updateGroup(@RequestBody UmsGroups group){
        int flag=0;
        if (group.getId()!=null)
            flag=1;
        UmsGroups umsGroup = umsAdminService.updateGroup(group);
        if (umsGroup==null)
            return CommonResult.failed("小组名重复");
        if (flag==0)
            return CommonResult.success(umsGroup,"添加小组成功");
        return CommonResult.success(umsGroup,"修改小组成功");
    }

    @PreAuthorize("hasAuthority('ums:groups:delete')")
    @DeleteMapping("/group")
    public CommonResult deleteGroup(@RequestParam Long id){
        umsAdminService.deleteGroup(id);
        return CommonResult.success(null,"删除小组成功");
    }

    @PreAuthorize("hasAuthority('ums:groups:read')")
    @GetMapping("/group")
    public CommonResult getGroups(){
        return CommonResult.success(umsAdminService.getGroups(),"获取小组列表成功");
    }
}
