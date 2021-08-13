package sast.onlineexams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.mbg.model.CmsAnswerDispatches;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.service.CmsMarkService;

/**
 * @author sherman
 * @create 2021-08-13 9:52
 * @description
 */
@RestController
public class CmsMarkController {
    @Autowired
    private CmsMarkService markService;

    @PreAuthorize("hasAuthority('cms:mark:read')")
    @GetMapping("/mark/groups")
    public CommonResult getGroupProblemInfo(){
        return CommonResult.success(markService.getGroupProblemRelation());
    }

    @PreAuthorize("hasAuthority('cms:mark:read')")
    @GetMapping("/mark/request")
    public CommonResult getMarkContent(@RequestParam Long problem_id,@RequestParam int num){
        return CommonResult.success(markService.getMarkContent(problem_id,num));
    }

    @PreAuthorize("hasAuthority('cms:mark:create')")
    @PostMapping("/mark/submit")
    public CommonResult markSubmit(@RequestBody CmsAnswerDispatches answerDispatches){
        return CommonResult.success(markService.markSubmit(answerDispatches));
    }

    @PreAuthorize("hasAuthority('cms:mark:read')")
    @PostMapping("/mark/progress")
    public CommonResult markProgress(){
        return CommonResult.success(markService.markProgress());
    }
}
