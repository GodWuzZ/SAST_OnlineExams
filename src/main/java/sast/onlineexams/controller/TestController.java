package sast.onlineexams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sherman
 * @create 2021-07-24 21:53
 * @description 测试用控制器
 */

@RestController
@RequestMapping("/test")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @ResponseBody
    @RequestMapping("/helloWorld")
    public Map<String,String> helloWorld(){
        Map<String,String> res = new HashMap<String,String>();
        res.put("success","true");
        res.put("msg","hello world!");
        logger.info("test controller");
        return res;
    }
}
