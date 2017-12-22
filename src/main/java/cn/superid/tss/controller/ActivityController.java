package cn.superid.tss.controller;

import cn.superid.tss.service.IActivityService;
import cn.superid.tss.service.impl.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushao
 * 提供课程活动/小组活动相关接口
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;


}
