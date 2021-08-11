package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.dto.UmsAdminLoginParam;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.mbg.model.UmsStudent;
import sast.onlineexams.service.UmsAdminService;

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
    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping("/permission/{adminId}")
    public CommonResult<List<UmsPermission>>getPermissionList(@PathVariable Long adminId){
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }

    @PreAuthorize("hasAuthority('ums:admin:create')")
    @PostMapping("/userInfo")
    public CommonResult insertAdmin(@RequestBody UmsAdmin umsAdmin){
        umsAdminService.insertAdmin(umsAdmin);
        return CommonResult.success(umsAdmin);
    }

    @PreAuthorize("hasAuthority('ums:admin:update')")
    @PutMapping("/userInfo")
    public CommonResult updateAdmin(@RequestBody UmsAdmin umsAdmin){
        umsAdminService.updateAdmin(umsAdmin);
        return CommonResult.success(umsAdmin);
    }

    @PreAuthorize("hasAuthority('ums:admin:delete')")
    @DeleteMapping("/userInfo")
    public CommonResult deleteAdmin(@RequestParam long id){
        umsAdminService.deleteAdmin(id);
        return CommonResult.success(id);
    }

    @JsonView(UmsAdmin.AdminDetailView.class)
    @PreAuthorize("hasAuthority('ums:admin:read')")
    @GetMapping("/userInfo")
    public List<UmsAdmin> adminList(){
        return umsAdminService.adminList();
    }
}
