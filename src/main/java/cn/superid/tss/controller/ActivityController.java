package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.service.impl.ActivityService;
import cn.superid.tss.vo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liushao
 * 提供课程活动/小组活动相关接口
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/getActivities",method = RequestMethod.GET)
    public SimpleResponse getAllActivities(@RequestParam(value = "courseId") long courseId){
        List<Activity> activities = activityService.getAllActivites(1234567l);
        return SimpleResponse.ok(activities);

    }



}
