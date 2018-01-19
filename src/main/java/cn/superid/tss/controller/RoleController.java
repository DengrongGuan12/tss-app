package cn.superid.tss.controller;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.common.utils.auth.PermissionConstants;
import cn.superid.common.utils.auth.ResourceTypeEnum;
import cn.superid.tss.constant.*;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.service.impl.RoleService;
import cn.superid.tss.service.impl.UserService;
import cn.superid.tss.vo.ResultVO;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import com.blueskykong.auth.starter.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    BusinessClient businessClient;

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);


    @ApiOperation(value = "课程人员页面获得所有成员", response = RoleGroup.class)
    @RequestMapping(value = "/getCourseRole",method = RequestMethod.GET)

    //@PreAuth(value = PermissionConstants.ENTER_ROLE_STORE)

    public SimpleResponse getCourseRoleList(@RequestParam(value = "courseId")long courseId){
        logger.info("courseid {}" ,courseId);

        return SimpleResponse.ok(roleService.getRoleByCourseId(courseId));
    }

    @ApiOperation(value = "从课程/小组里面删除人员",response = SimpleResponse.class)
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.DELETE_ROLE)
    public SimpleResponse deleteRoleFromCourse(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                               @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                               @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long affairId,
                                                @RequestParam(value = "toDeleteRoleId")long toDeleteRoleId,
                                               @RequestParam(value = "affairType")int affairType
                                                ){
        long result = roleService.deleteRole(roleId,toDeleteRoleId,affairId,AffairType.getType(affairType),false);
        return SimpleResponse.ok("success");
    }

    @ApiOperation(value = "邀请老师",response = SimpleResponse.class)
    @RequestMapping(value = "/inviteTeacher",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.CREATE_ROLE)
    public SimpleResponse inviteTeacher(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                        @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                        @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                        @RequestParam(value = "roleIds")Long[] invitedIds){
        List<cn.superid.common.rest.dto.RoleInfoDTO> roleInfoDTOs = businessClient.getRoles(invitedIds);
        List<ResultVO> resultVOS = new ArrayList<>();
        List<Long> toInvitedIds = new ArrayList<>();
        roleInfoDTOs.forEach(roleInfoDTO -> {
            if (roleService.getRoleInAffair(courseId, roleInfoDTO.getUserId()) != null){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE, "重复邀请", roleInfoDTO.getId()+""));
            }else if (roleInfoDTO.getMold() != UserType.TEACHER.getIndex()){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE,"不是教师",roleInfoDTO.getId()+""));
            }else{
                resultVOS.add(new ResultVO(ResponseCode.OK,null,roleInfoDTO.getId()+""));
                toInvitedIds.add(roleInfoDTO.getId());
            }
        });
        roleService.addMember(courseId,roleId,toInvitedIds.stream().toArray(Long[]::new),
                UserType.TEACHER, AffairType.COURSE);
        return SimpleResponse.ok(resultVOS);
    }

    @ApiOperation(value = "邀请助教",response = SimpleResponse.class)
    @RequestMapping(value = "/inviteTutor",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.CREATE_ROLE)
    public SimpleResponse inviteTutor(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "roleIds")Long[] invitedIds){
        List<cn.superid.common.rest.dto.RoleInfoDTO> roleInfoDTOs = businessClient.getRoles(invitedIds);
        List<ResultVO> resultVOS = new ArrayList<>();
        List<Long> toInvitedIds = new ArrayList<>();
        roleInfoDTOs.forEach(roleInfoDTO -> {
            if (roleService.getRoleInAffair(courseId, roleInfoDTO.getUserId()) != null){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE, "重复邀请", roleInfoDTO.getId()+""));
            }else if (roleInfoDTO.getMold() != UserType.STUDENT.getIndex()){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE,"不是学生",roleInfoDTO.getId()+""));
            }else{
                resultVOS.add(new ResultVO(ResponseCode.OK,null,roleInfoDTO.getId()+""));
                toInvitedIds.add(roleInfoDTO.getId());
            }
        });
        roleService.addMember(courseId,roleId,toInvitedIds.stream().toArray(Long[]::new),
                UserType.TUTOR, AffairType.COURSE);
        return SimpleResponse.ok(resultVOS);
    }

    @ApiOperation(value = "邀请学生（批准学生进入课程）",response = SimpleResponse.class)
    @RequestMapping(value = "/inviteStudent",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.CREATE_ROLE)
    public SimpleResponse inviteStudent(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "roleIds")Long[] invitedIds){
        List<cn.superid.common.rest.dto.RoleInfoDTO> roleInfoDTOs = businessClient.getRoles(invitedIds);
        List<ResultVO> resultVOS = new ArrayList<>();
        List<Long> toInvitedIds = new ArrayList<>();
        roleInfoDTOs.forEach(roleInfoDTO -> {
            if (roleService.getRoleInAffair(courseId, roleInfoDTO.getUserId()) != null){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE, "重复邀请", roleInfoDTO.getId()+""));
            }else if (roleInfoDTO.getMold() != UserType.STUDENT.getIndex()){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE,"不是学生",roleInfoDTO.getId()+""));
            }else{
                resultVOS.add(new ResultVO(ResponseCode.OK,null,roleInfoDTO.getId()+""));
                toInvitedIds.add(roleInfoDTO.getId());
            }
        });
        roleService.addMember(courseId,roleId,toInvitedIds.stream().toArray(Long[]::new),
                UserType.STUDENT, AffairType.COURSE);
        return SimpleResponse.ok(resultVOS);
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
                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId){

        roleService.deleteRole(roleId,roleId,courseId, AffairType.COURSE, true);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "获取一个用户在事务中的角色,在进入一个课程或小组的时候调用", response = Role.class)
    @RequestMapping(value = "/getRoleInAffair", method = RequestMethod.GET)
    public SimpleResponse getRoleInAffair(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                          @RequestParam(value = "affairId")long affairId){
        return SimpleResponse.ok(roleService.getRoleInAffair(affairId,userId));
    }

    @ApiOperation(value = "拒绝学生加入课程或小组", response = SimpleResponse.class)
    @RequestMapping(value = "/rejectJoin", method = RequestMethod.GET)
    @PreAuth(value = PermissionConstants.CREATE_ROLE)
    public SimpleResponse rejectJoin(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long affairId,
                                     @RequestParam(value = "roleId")long rejectedId,
                                     @RequestParam(value = "reason")String reason,
                                     @RequestParam(value = "affairType")int affairType){
        roleService.rejectJoin(affairId, roleId, AffairType.getType(affairType), rejectedId, reason);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "学生申请加入课程或小组", response = SimpleResponse.class)
    @RequestMapping(value = "/applyJoin", method = RequestMethod.GET)
    public SimpleResponse applyJoin(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long affairId,
                                     @RequestParam(value = "reason")String reason,
                                     @RequestParam(value = "applyId")long applyId,
                                     @RequestParam(value = "affairType")int affairType){
        roleService.applyJoin(applyId, roleId, AffairType.getType(affairType), reason);
        return SimpleResponse.ok(null);
    }



}
