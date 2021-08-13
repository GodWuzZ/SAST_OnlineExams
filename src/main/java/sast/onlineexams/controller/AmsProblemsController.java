package sast.onlineexams.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.dto.AmsProblemDetails;
import sast.onlineexams.mbg.model.AmsProblems;
import sast.onlineexams.service.AmsProblemService;

import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-12 11:26
 * @description
 */
@RestController
public class AmsProblemsController {
    @Autowired
    private AmsProblemService amsProblemService;

    @PostMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:create')")
    public CommonResult insertProblem(@RequestBody AmsProblemDetails amsProblemDetails){
        return CommonResult.success(amsProblemService.insertProblemDetails(amsProblemDetails));
    }

    @PutMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:update')")
    public CommonResult updateProblem(@RequestBody AmsProblemDetails amsProblemDetails){
        return CommonResult.success(amsProblemService.updateProblemDetails(amsProblemDetails));
    }

    @DeleteMapping("/admin/problems")
    @PreAuthorize("hasAuthority('ams:problems:delete')")
    public CommonResult deleteProblem(@RequestBody Map<String,Long> idList){
        return CommonResult.success(amsProblemService.deleteProblemDetails(idList));
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

    @PostMapping("/admin/addProblemsMassively")
    @PreAuthorize("hasAuthority('ams:problems:create')")
    public CommonResult addProblemsMassively(@RequestBody List<AmsProblemDetails>problemDetails){
        return CommonResult.success(amsProblemService.addProblemsMassively(problemDetails));
    }
}
