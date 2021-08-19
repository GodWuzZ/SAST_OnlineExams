package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.common.utils.RedisUtil;
import sast.onlineexams.dto.UmsStudentLoginParam;
import sast.onlineexams.mbg.model.UmsStudent;
import sast.onlineexams.service.UmsStudentService;

import java.util.Date;
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
    @Value("${redis.key.token.prefix}")
    private String redisPrefix;
    @Value("${redis.key.token.expire}")
    private Long redisExpiration;
    @Autowired
    private RedisUtil redisUtil;
    @PostMapping("/login")
    @JsonView(UmsStudent.StudentDetailView.class)
    public CommonResult login(@RequestBody UmsStudentLoginParam umsStudentLoginParam, BindingResult result) {
        String token = umsStudentService.login(umsStudentLoginParam.getUsername(), umsStudentLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        redisUtil.set(redisPrefix+umsStudentLoginParam.getUsername(),new Date());
        redisUtil.expire(redisPrefix+umsStudentLoginParam.getUsername(),redisExpiration);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        UmsStudent student = umsStudentService.getStudentByUsername(umsStudentLoginParam.getUsername());
        tokenMap.put("studentInfo",student);
        return CommonResult.success(tokenMap,"登陆成功");
    }

    @PreAuthorize("hasAuthority('ums:student:create')")
    @PostMapping("/admin/studentInfo")
    public CommonResult<UmsStudent> updateStudent(@RequestBody UmsStudent umsStudent){
        int flag=0;
        if (umsStudent.getId()!=null)
            flag=1;
        UmsStudent student = umsStudentService.updateStudent(umsStudent);
        if (student==null)
            return CommonResult.failed("学生用户名重复");
        if(flag==0)
            return CommonResult.success(student,"新增成功");
        return CommonResult.success(student,"修改成功");
    }

    @PreAuthorize("hasAuthority('ums:student:delete')")
    @DeleteMapping("/admin/studentInfo")
    public CommonResult<Long> deleteStudent(@RequestParam long id){
        umsStudentService.deleteStudent(id);
        return CommonResult.success(null,"删除成功");
    }

    @JsonView(UmsStudent.StudentDetailView.class)
    @PreAuthorize("hasAuthority('ums:student:read')")
    @GetMapping("/admin/studentInfo")
    public CommonResult<List<UmsStudent>> studentList(){
        return CommonResult.success(umsStudentService.studentList(),"学生信息列表");
    }

    @PreAuthorize("hasAuthority('ums:student:create')")
    @PostMapping("/admin/updateStudentMassively")
    public CommonResult updateStudentMassively(@RequestBody List<UmsStudent> Students){
        return CommonResult.success(umsStudentService.updateStudentMassively(Students),"批量添加/修改成功");
    }
}
