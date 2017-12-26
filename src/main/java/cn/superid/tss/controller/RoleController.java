package cn.superid.tss.controller;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.service.impl.RoleService;
import cn.superid.tss.vo.RoleGroup;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);


    @ApiOperation(value = "课程人员页面获得所有成员", response = RoleGroup.class)
    @RequestMapping(value = "/getCourseRole",method = RequestMethod.GET)
    public SimpleResponse getCourseRoleList(@RequestParam(value = "courseId")long courseId){
        logger.info("courseid=" + courseId);

        List<RoleGroup> groups = roleService.getRoleByCourseId(courseId);

        return SimpleResponse.ok(groups);
    }

    @ApiOperation(value = "从课程里面删除人员",response = SimpleResponse.class)
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    public SimpleResponse deleteRoleFromCourse(@RequestParam(value = "roleId")long roleId,
                                               @RequestParam(value = "courseId") long courseId){
        roleService.deleteRole(roleId,courseId);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "邀请老师",response = SimpleResponse.class)
    @RequestMapping(value = "/inviteTeacher",method = RequestMethod.POST)
    public SimpleResponse inviteTeacher(@RequestParam(value = "userId") long userId,
                                        @RequestParam(value = "courseId") long courseId){
        long roleId = roleService.addToCourse(userId,courseId, UserType.TEACHER.name());
        return SimpleResponse.ok(roleId);
    }

    @ApiOperation(value = "邀请助教",response = SimpleResponse.class)
    @RequestMapping(value = "/inviteTutor",method = RequestMethod.POST)
    public SimpleResponse inviteTutor(@RequestParam(value = "userId") long userId,
                                        @RequestParam(value = "courseId") long courseId){

        long roleId = roleService.addToCourse(userId,courseId, UserType.TUTOR.name());
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "通过邀请码加入课程",response = SimpleResponse.class)
    @RequestMapping(value = "/joinCourseByCode",method = RequestMethod.POST)
    public SimpleResponse joinCourseByCode(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                           @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                           @RequestParam(value = "code") String code,
                                           @RequestParam(value = "courseId")long courseId){

        long allocatedRoleId = roleService.joinCourseByCode(userId,courseId,code);

        return SimpleResponse.ok(allocatedRoleId);
    }

    @ApiOperation(value = "退出课程",response = SimpleResponse.class)
    @RequestMapping(value = "/quitCourse",method = RequestMethod.POST)
    public SimpleResponse quitCourse(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                     @RequestParam(value = "courseId") long courseId){

        roleService.deleteRole(roleId,courseId);
        return SimpleResponse.ok(null);
    }

}
