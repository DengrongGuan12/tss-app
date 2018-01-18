package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.utils.auth.PermissionConstants;
import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.vo.Activity;
import com.blueskykong.auth.starter.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    IActivityService activityService;

    @ApiOperation(value = "获得课程活动列表",response = Activity.class)
    @RequestMapping(value = "/getActivities",method = RequestMethod.GET)
    @PreAuth(value = PermissionConstants.ENTER_PUBLISH_STORE)
    public SimpleResponse getAllActivities(@RequestParam(value = "courseId") long courseId){
        logger.info("/getActivities","courseId=",courseId);
        List<Activity> activities = activityService.getAllActivites(courseId);
        return SimpleResponse.ok(activities);
    }

    @ApiOperation(value = "获得活动详情",response = Activity.class)
    @RequestMapping(value = "/getActivityDetail",method = RequestMethod.GET)
    @PreAuth(value = PermissionConstants.ENTER_PUBLISH_STORE)
    public SimpleResponse getActivity(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "activityId")long activityId){
        Activity activity =  activityService.getActivity(activityId);
        return SimpleResponse.ok(activity);
    }

//    @ApiOperation(value = "获得作业详情",response = Homework.class)
//    @RequestMapping(value = "/getHomeworkDetail",method = RequestMethod.GET)
//    public SimpleResponse getHomework(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
//                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
//                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
//                                      @RequestParam(value = "activityId")long activityId){
//        Homework activity =  activityService.getHomework(activityId);
//        return SimpleResponse.ok(activity);
//    }



//    @ApiOperation(value = "创建教学",response = Long.class)
//    @RequestMapping(value = "/createTeaching",method = RequestMethod.POST)
//    public SimpleResponse createTeaching(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
//                                         @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
//                                         @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
//                                         @RequestBody AddTeachingForm teaching){
//        long activityId = activityService.createActivity(teaching, ActivityType.Teaching.getIndex(),courseId,roleId,userId);
//        return SimpleResponse.ok(activityId);
//    }
//
//    @ApiOperation(value = "创建作业",response = Long.class)
//    @RequestMapping(value = "/createHomework",method = RequestMethod.POST)
//    public SimpleResponse createHomework(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
//                                         @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
//                                         @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
//                                         @RequestBody AddHomeworkform homeworkForm){
//
//        long activityId = activityService.createHomework(homeworkForm,courseId,roleId,userId);
//        return SimpleResponse.ok(activityId);
//    }
//
//    @ApiOperation(value = "创建考试",response = Long.class)
//    @RequestMapping(value = "/createExam",method = RequestMethod.POST)
//    public SimpleResponse createExam(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
//                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
//                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
//                                     @RequestBody AddActivityForm examform){
//
//        long activityId = activityService.createActivity(examform,ActivityType.Exam.getIndex(),courseId,
//                                                            roleId,userId);
//        return SimpleResponse.ok(activityId);
//    }
//

    @ApiOperation(value = "创建活动",response = Long.class)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.CREATE_PUBLISH)
    public SimpleResponse createActivity(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                     @RequestBody AddActivityForm form
                                      ){
        logger.info("form {}",form);

        long activityId = activityService.createActivity(form,courseId,
                roleId,userId);
        return SimpleResponse.ok("success");
    }

//
//    @ApiOperation(value = "创建项目活动",response = Long.class)
//    @RequestMapping(value = "/createTeamActivity",method = RequestMethod.POST)
//    public SimpleResponse createTeamActivity(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
//                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
//                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
//                                      @RequestBody AddActivityForm form){
//
//        long activityId = activityService.createActivity(form,ActivityType.Project.getIndex(),courseId,
//                roleId,userId);
//        return SimpleResponse.ok(activityId);
//    }
//

}
