package sast.onlineexams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.component.WebSocket;

import java.util.HashMap;
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

    @Autowired
    WebSocket webSocket;
    @RequestMapping("/helloWorld")
    @PreAuthorize("hasAuthority('ums:admin:create')")
    public Map<String,String> helloWorld(){
        Map<String,String> res = new HashMap<String,String>();
        res.put("success","true");
        res.put("msg","hello world!");
        logger.info("test controllerfdasfad");
        return res;
    }


    @GetMapping("/testCommonResult")
    @PreAuthorize("hasAuthority('ums:groups')")
    public CommonResult testCommonResult(){
        Map<String,String> map = new HashMap<>();
        map.put("author","sherman");
        map.put("organization","SAST");
        logger.info(map.get("author"));
        logger.info(CommonResult.success(map).toString());
        return CommonResult.success(map,"CommonResult测试");
    }

    @GetMapping("/sendAllWebSocket")
    public String testWebSocket(){
        String text="你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return text;
    }

    @GetMapping("/sendOneWebSocket/{username}")
    public String sendOneWebSocket(@PathVariable("username") String username){
        String text=username+" 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(username,text);
        return text;
    }

    @GetMapping("/testSecurity")
    public String security(){
        return "fdsafafa";
    }
}
