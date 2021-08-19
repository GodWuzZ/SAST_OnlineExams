package sast.onlineexams.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sast.onlineexams.common.api.CommonResult;
import sast.onlineexams.mbg.model.CmsAnswerDispatches;
import sast.onlineexams.service.CmsMarkService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
        return CommonResult.success(markService.getGroupProblemRelation(),"成功获取小组题目对应关系");
    }

    @PreAuthorize("hasAuthority('cms:mark:read')")
    @PostMapping("/mark/request")
    public CommonResult getMarkContent(@RequestParam Long problem_id,@RequestParam Long user_id, @RequestParam int num){
        return CommonResult.success(markService.getMarkContent(problem_id,user_id,num),"成功获取批改内容");
    }


    @PreAuthorize("hasAuthority('cms:mark:create')")
    @PostMapping("/mark/submit")
    public CommonResult markSubmit(@RequestBody CmsAnswerDispatches answerDispatches){
        int flag = markService.markSubmit(answerDispatches);
        if(flag==0){
            return CommonResult.failed("当前批改题目已过期");
        }
        return CommonResult.success(null,"批改提交成功");
    }

    @PreAuthorize("hasAuthority('cms:mark:read')")
    @GetMapping("/mark/progress")
    public CommonResult markProgress(){
        return CommonResult.success(markService.markProgress(),"成功获取批改进度");
    }

    @PreAuthorize("hasAuthority('cms:mark')")
    @PostMapping("/admin/mark/auto")
    public CommonResult autoCorrect(@RequestParam("missing_part_score") int missing_part_score){
        markService.autoCorrect(missing_part_score);
        return CommonResult.success(null,"自动批改完成");
    }

//    @PreAuthorize("hasAuthority('cms:mark')")
    @GetMapping("/admin/mark/import")
    public void resultImport(HttpServletResponse response) throws IOException {
        if (markService.isCorrectingCompleted()){
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分数统计结果", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream()).head(markService.head()).sheet("分数统计结果").doWrite(markService.dataList());
        }else {
            JSONObject result=new JSONObject();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "批改未全部完成!");
            result.putAll(map);
            response.getWriter().println(result);

        }
    }
}
