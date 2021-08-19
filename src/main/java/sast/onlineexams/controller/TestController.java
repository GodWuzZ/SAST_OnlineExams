package sast.onlineexams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.common.utils.JwtTokenUtil;
import sast.onlineexams.common.utils.RedisUtil;
import sast.onlineexams.component.WebSocket;
import sast.onlineexams.mbg.mapper.AmsProblemOptionsMapper;
import sast.onlineexams.mbg.mapper.AmsProblemsMapper;
import sast.onlineexams.mbg.model.AmsProblemOptions;
import sast.onlineexams.mbg.model.AmsProblems;
import sast.onlineexams.mbg.model.AmsProblemsExample;
import sast.onlineexams.service.UmsAdminService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
    private WebSocket webSocket;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    AmsProblemOptionsMapper optionsMapper;
    @Autowired
    private AmsProblemsMapper problemsMapper;
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
        String text="你们好！测试json格式转换！";
        List<String> list = new ArrayList<>();
        list.add("dfaf");
        list.add("fdafda");
        webSocket.sendAllMessage(text,list);
        return text;
    }

    @GetMapping("/ws/{username}")
    public String sendOneWebSocket(@PathVariable("username") String username){
        String text=username+" 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(username,text);
        return text;
    }

    @GetMapping("/testSecurity")
    public CommonResult security(@RequestParam String token){
        return CommonResult.success(jwtTokenUtil.getIatFromToken(token),"获取iat成功");
    }

    @GetMapping("/testRedis")
    public CommonResult redis() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return CommonResult.success(redisUtil.get("date"),"redis日期存储测试");
    }

    @GetMapping("/problems")
    public CommonResult option(){
        AmsProblemsExample example = new AmsProblemsExample();
        example.createCriteria();
        List<AmsProblems> problems = problemsMapper.selectByExampleWithBLOBs(example);
        return CommonResult.success(problems,"成功");
    }
}
