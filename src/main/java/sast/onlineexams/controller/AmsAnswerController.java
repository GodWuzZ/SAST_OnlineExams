package sast.onlineexams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.mbg.model.CmsAnswers;
import sast.onlineexams.service.AmsAnswerService;

/**
 * @author sherman
 * @create 2021-08-13 9:09
 * @description
 */
@RestController
public class AmsAnswerController {
    @Autowired
    private AmsAnswerService amsAnswerService;

    @GetMapping("/duetime")
    public CommonResult getDueTime(){
        return CommonResult.success(amsAnswerService.getDueTime());
    }

    @PostMapping("/submit")
    public CommonResult submit(@RequestBody CmsAnswers answer){
        return CommonResult.success(submit(answer));
    }

    @GetMapping("/submitted")
    public CommonResult getSubmitted(){
        return CommonResult.success(amsAnswerService.getSubmitted());
    }
}
