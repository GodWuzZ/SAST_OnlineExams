package sast.onlineexams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sast.onlineexams.common.api.CommonResult;

import java.util.HashMap;
import java.util.Map;


/**
 * @author sherman
 * @create 2021-07-24 21:53
 * @description 测试用控制器
 */

@RestController
@RequestMapping("/test")
@PreAuthorize("hasAuthority('ums:admin')")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @ResponseBody
    @RequestMapping("/helloWorld")
    public Map<String,String> helloWorld(){
        Map<String,String> res = new HashMap<String,String>();
        res.put("success","true");
        res.put("msg","hello world!");
        logger.info("test controllerfdasfad");
        return res;
    }


    @GetMapping("/testCommonResult")
    @ResponseBody
    public CommonResult testCommonResult(){
        Map<String,String> map = new HashMap<>();
        map.put("author","sherman");
        map.put("organization","SAST");
        logger.info(map.get("author"));
        logger.info(CommonResult.success(map).toString());
        return CommonResult.success(map,"CommonResult测试");
    }
}
