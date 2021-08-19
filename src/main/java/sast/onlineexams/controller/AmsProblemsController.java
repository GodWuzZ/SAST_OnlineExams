package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.dto.AmsProblemDetails;
import sast.onlineexams.mbg.model.AmsProblems;
import sast.onlineexams.service.AmsProblemService;
import sast.onlineexams.service.impl.AmsProblemServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-12 11:26
 * @description
 */
@RestController
public class AmsProblemsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmsProblemServiceImpl.class);
    @Autowired
    private AmsProblemService amsProblemService;

    @PostMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:create')")
    public CommonResult updateProblem(@RequestBody AmsProblemDetails amsProblemDetails){
        Long id = amsProblemDetails.getProblem().getId();
        amsProblemService.updateProblemDetails(amsProblemDetails);
        if (id == null){
            return CommonResult.success(amsProblemDetails,"添加题目成功");
        }
        return CommonResult.success(amsProblemDetails,"修改题目成功");
    }

    @DeleteMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:delete')")
    public CommonResult deleteProblem(@RequestBody Map<String,Long> idList){
        amsProblemService.deleteProblemDetails(idList);
        return CommonResult.success(null,"删除成功");
    }

    @GetMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:read')")
    public CommonResult getProblems(){
        return CommonResult.success(amsProblemService.getProblemList());
    }

    @GetMapping("/admin/problems/{id}")
    @PreAuthorize("hasAuthority('ams:problems:read')")
    public CommonResult getAdminProblem(@PathVariable("id") Long id){
        return CommonResult.success(amsProblemService.getProblem(id));
    }

    @JsonView(AmsProblems.ProblemSimpleView.class)
    @GetMapping("/problems")
    public CommonResult getProblemsList(){
        return CommonResult.success(amsProblemService.getProblemList());
    }

    @JsonView(AmsProblems.ProblemSimpleView.class)
    @GetMapping("/problems/{id}")
    public CommonResult getProblem(@PathVariable("id")Long id){
        return CommonResult.success(amsProblemService.getProblem(id));
    }

    @PostMapping("/admin/updateProblemsMassively")
    @PreAuthorize("hasAuthority('ams:problems:create')")
    public CommonResult updateProblemsMassively(@RequestBody List<AmsProblemDetails>problemDetails){
        amsProblemService.updateProblemsMassively(problemDetails);
        return CommonResult.success(null,"批量添加/修改成功");
    }
}
