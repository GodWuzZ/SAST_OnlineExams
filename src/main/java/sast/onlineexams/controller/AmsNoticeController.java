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
    public CommonResult sendNotice(@RequestBody AmsAnnouncements announcement){
        webSocket.sendAllMessage("小冯给你发来的公告内容哦",announcement);
        return CommonResult.success(announcement,"发送公告成功");
    }

    @PostMapping("/admin/sendMessage")
    @PreAuthorize("hasAuthority('ams:announcements:create')")
    public CommonResult sendMessage(@RequestParam("username") String username,@RequestParam("message") String message){
        webSocket.sendOneMessage(username,message);
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("message",message);
        return CommonResult.success(map,"单点发送成功");
    }

    @PreAuthorize("hasAuthority('ams:announcements:create')")
    @PostMapping("/admin/notice")
    public CommonResult updateNotice(@RequestBody AmsAnnouncements announcement){
        Long id = announcement.getId();
        amsNoticeService.updateNotice(announcement);
        if (id==null) {
            return CommonResult.success(announcement, "添加成功");
        }
        return CommonResult.success(announcement,"修改成功");
    }

    @PreAuthorize("hasAuthority('ams:announcements:delete')")
    @DeleteMapping("/admin/notice")
    public CommonResult deleteNotice(@RequestParam Long id){
        amsNoticeService.deleteNotice(id);
        return CommonResult.success(null,"删除成功");
    }


    @GetMapping("/notice")
    public CommonResult getNotices(){
        return CommonResult.success(amsNoticeService.getNoticeList(),"获取公告列表成功");
    }

    @GetMapping("/notice/{id}")
    public CommonResult getNotice(@PathVariable("id")Long id){
        return CommonResult.success(amsNoticeService.getNoticeById(id),"获取公告成功");
    }
}
