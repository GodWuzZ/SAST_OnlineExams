package sast.onlineexams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.component.WebSocket;
import sast.onlineexams.mbg.model.AmsAnnouncements;
import sast.onlineexams.service.AmsNoticeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-12 10:35
 * @description
 */
@RestController
public class AmsNoticeController {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private AmsNoticeService amsNoticeService;

    @PostMapping("/admin/sendNotice")
    @PreAuthorize("hasAuthority('ams:announcements:create')")
    public CommonResult sendNotice(@RequestBody List<AmsAnnouncements> announcements){
        webSocket.sendAllMessage("小冯给你发来的公告内容哦",announcements);
        return CommonResult.success(announcements);
    }

    @PostMapping("/admin/sendMessage")
    @PreAuthorize("hasAuthority('ams:announcements:create')")
    public CommonResult sendMessage(@RequestParam("username") String username,@RequestParam("message") String message){
        webSocket.sendOneMessage(username,message);
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("message",message);
        return CommonResult.success(map);
    }

    @PreAuthorize("hasAuthority('ams:announcements:create')")
    @PostMapping("/admin/notice")
    public CommonResult insertNotice(@RequestBody AmsAnnouncements announcement){
        return CommonResult.success(amsNoticeService.insertNotice(announcement));
    }

    @PreAuthorize("hasAuthority('ams:announcements:delete')")
    @DeleteMapping("/admin/notice")
    public CommonResult deleteNotice(@RequestParam Long id){
        return CommonResult.success(amsNoticeService.deleteNotice(id));
    }

    @PreAuthorize("hasAuthority('ams:announcements:update')")
    @PutMapping("/admin/notice")
    public CommonResult updateNoice(@RequestBody AmsAnnouncements announcement){
        return CommonResult.success(amsNoticeService.updateNotice(announcement));
    }

    @GetMapping("/notice")
    public CommonResult getNotices(){
        return CommonResult.success(amsNoticeService.getNoticeList());
    }

    @GetMapping("/notice/{id}")
    public CommonResult getNotice(@PathVariable("id")Long id){
        return CommonResult.success(amsNoticeService.getNoticeById(id));
    }
}
