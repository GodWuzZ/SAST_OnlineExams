package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.dto.UmsStudentLoginParam;
import sast.onlineexams.mbg.model.UmsStudent;
import sast.onlineexams.service.UmsStudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-11 10:25
 * @description 学生用户管理
 */
@RestController
public class UmsStudentController {
    @Autowired
    private UmsStudentService umsStudentService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/login")
    @JsonView(UmsStudent.StudentDetailView.class)
    public CommonResult login(@RequestBody UmsStudentLoginParam umsStudentLoginParam, BindingResult result) {
        String token = umsStudentService.login(umsStudentLoginParam.getUsername(), umsStudentLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        UmsStudent student = umsStudentService.getStudentByUsername(umsStudentLoginParam.getUsername());
        tokenMap.put("studentInfo",student);
        return CommonResult.success(tokenMap);
    }

    @PreAuthorize("hasAuthority('ums:student:create')")
    @PostMapping("/admin/studentInfo")
    public CommonResult<UmsStudent> insertStudent(@RequestBody UmsStudent umsStudent){
        umsStudentService.insertStudent(umsStudent);
        return CommonResult.success(umsStudent);
    }

    @PreAuthorize("hasAuthority('ums:student:update')")
    @PutMapping("/admin/studentInfo")
    public CommonResult<UmsStudent> updateStudent(@RequestBody UmsStudent umsStudent){
        umsStudentService.updateStudent(umsStudent);
        return CommonResult.success(umsStudent);
    }

    @PreAuthorize("hasAuthority('ums:student:delete')")
    @DeleteMapping("/admin/studentInfo")
    public CommonResult<Long> deleteStudent(@RequestParam long id){
        umsStudentService.deleteStudent(id);
        return CommonResult.success(id);
    }

    @JsonView(UmsStudent.StudentDetailView.class)
    @PreAuthorize("hasAuthority('ums:student:read')")
    @GetMapping("/admin/studentInfo")
    public CommonResult<List<UmsStudent>> studentList(){
        return CommonResult.success(umsStudentService.studentList());
    }

    @PreAuthorize("hasAuthority('ums:student:create')")
    @PostMapping("/admin/addStudentMassively")
    public CommonResult insertStudentMassively(@RequestBody List<UmsStudent> Students){
        return CommonResult.success(umsStudentService.insertStudentMassively(Students));
    }

}
