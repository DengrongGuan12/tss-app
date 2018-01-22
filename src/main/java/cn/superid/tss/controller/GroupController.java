package cn.superid.tss.controller;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.common.utils.auth.PermissionConstants;
import cn.superid.tss.constant.AffairType;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.service.IGroupService;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.vo.*;
import com.blueskykong.auth.starter.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午10:03
 **/
@RestController
@RequestMapping("/api/group")
public class GroupController {

    Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    IGroupService groupService;

    @Autowired
    IRoleService roleService;

    @Autowired
    BusinessClient businessClient;

    @Autowired
    IActivityService activityService;

    @ApiOperation(value = "创建小组", response = SimpleResponse.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST)

    public SimpleResponse createGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestParam("name") String name,
                                      @RequestParam("description") String description) {
        //TODO 仅该课程的学生能创建小组，其他不做限制
        long groupId = groupService.createGroup(userId, roleId, courseId, name, description);
        return SimpleResponse.ok(groupId);
    }

    @ApiOperation(value = "获取一个课程下面的所有小组", response = GroupDetail.class)
    @RequestMapping(value = "/getGroupsOfCourse", method = RequestMethod.GET)
    public SimpleResponse getGroups(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                    @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                    @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId) {
//        Map<String,List<GroupDetail>> map = new HashMap<>();
//        List<GroupDetail> myGroup = new ArrayList<>();
//        myGroup.add(GroupDetail.mockMyGroup());
//        map.put("我的小组",myGroup);
//        List<GroupDetail> otherGroups = new ArrayList<>();
//        for (int i = 0;i<10;i++){
//            otherGroups.add(GroupDetail.mockOtherGroup());
//        }
//        map.put("所有小组",otherGroups);

        Map<String, List> map = groupService.getGroupsOfCourse(courseId, userId);
        return SimpleResponse.ok(map);
    }

    @ApiOperation(value = "退出小组", response = SimpleResponse.class)
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public SimpleResponse exitGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                    @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                    @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                    @RequestParam long groupId) {
        Role role = roleService.getRoleInAffair(groupId, userId);
        if (role.getRoleType() == UserType.LEADER.getIndex()) {
            throw new ErrorCodeException(ResponseCode.UNSUPPORTED_OPERATION_EXCEPTION, "只有组员能退出小组");
        } else {
            roleService.deleteRole(role.getId(), role.getId(), groupId, AffairType.GROUP, true);
            return SimpleResponse.ok(null);
        }
    }

    @ApiOperation(value = "删除小组", response = SimpleResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public SimpleResponse deleteGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "groupId") long groupId) {
        //TODO 只有组长能删除小组
        Role role = roleService.getRoleInAffair(groupId,userId);
        if (role.getRoleType() == UserType.LEADER.getIndex()){
            groupService.deleteGroup(role.getId(),groupId);
            return SimpleResponse.ok(null);
        }
        return SimpleResponse.exception(new ErrorCodeException(403,"无操作权限"));
    }

    @ApiOperation(value = "邀请组员", response = SimpleResponse.class)
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public SimpleResponse invite(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                 @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                 @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId,
                                 @RequestParam(value = "roleIds") Long[] invitedIds) {
        //TODO 只有组长老师助教能邀请组员
        List<cn.superid.common.rest.dto.RoleInfoDTO> roleInfoDTOs = businessClient.getRoles(invitedIds);
        Map<Integer, List<cn.superid.common.rest.dto.RoleInfoDTO>> moldRoleInfoListMap = new HashMap<>();
        List<ResultVO> resultVOS = new ArrayList<>();
        roleInfoDTOs.forEach(roleInfoDTO -> {
            if (roleService.getRoleInAffair(groupId, roleInfoDTO.getUserId()) != null){
                resultVOS.add(new ResultVO(ResponseCode.INVITE_ROLE_FAILURE, "重复邀请", roleInfoDTO.getId()+""));
            }else{
                if (moldRoleInfoListMap.containsKey(roleInfoDTO.getMold())){
                    moldRoleInfoListMap.get(roleInfoDTO.getMold()).add(roleInfoDTO);
                }else{
                    List<cn.superid.common.rest.dto.RoleInfoDTO> list = new ArrayList<>();
                    list.add(roleInfoDTO);
                    moldRoleInfoListMap.put(roleInfoDTO.getMold(), list);
                }
                resultVOS.add(new ResultVO(0,null,roleInfoDTO.getId()+""));
            }
        });
        if (moldRoleInfoListMap.containsKey(UserType.TEACHER.getIndex())){
            Long[] ids = moldRoleInfoListMap.get(UserType.TEACHER.getIndex()).stream().map(r -> r.getId()).toArray(Long[]::new);
            roleService.addMember(groupId, roleId, ids, UserType.TEACHER, AffairType.GROUP);
        }
        if (moldRoleInfoListMap.containsKey(UserType.TUTOR.getIndex())){
            Long[] ids = moldRoleInfoListMap.get(UserType.TUTOR.getIndex()).stream().map(r -> r.getId()).toArray(Long[]::new);
            roleService.addMember(groupId, roleId, ids, UserType.TUTOR, AffairType.GROUP);
        }
        if (moldRoleInfoListMap.containsKey(UserType.STUDENT.getIndex())){
            List<Long> ids = moldRoleInfoListMap.get(UserType.STUDENT.getIndex()).stream().map(r -> r.getId()).collect(Collectors.toList());
            List<RoleInfoDTO> leaders = groupService.getLeadersOfGroup(groupId);
            int i = 0;
            if (leaders.size() == 0) {
                //如果小组里还没有组长就设置该《学生》为组长
                roleService.addMember(groupId, roleId, new Long[]{ids.get(0)}, UserType.LEADER, AffairType.GROUP);
                i = 1;
            }
            List<Long> subIdList = ids.subList(i,ids.size());
            if (subIdList.size() > 0){
                Long[] subIds = new Long[subIdList.size()];
                subIdList.toArray(subIds);
                roleService.addMember(groupId, roleId, subIds, UserType.MEMBER, AffairType.GROUP);
            }
        }

        return SimpleResponse.ok(resultVOS);
    }

    @ApiOperation(value = "获取小组所有成员(组长，组员，助教，老师)", response = RoleGroup.class)
    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
//    @PreAuth(value = PermissionConstants.ENTER_ROLE_STORE)
    public SimpleResponse getRoles(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                   @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                   @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId) {

//        List<RoleGroup> roleGroups = new ArrayList<>();
//        roleGroups.add(RoleGroup.mockGroupLeader());
//        roleGroups.add(RoleGroup.mockGroupMember());
        List<RoleGroup> roleGroups = groupService.getRolesOfGroup(groupId);
        return SimpleResponse.ok(roleGroups);
    }

    @ApiOperation(value = "创建活动",response = Long.class)
    @RequestMapping(value = "/createActivity",method = RequestMethod.POST)
    @PreAuth(value = PermissionConstants.CREATE_PUBLISH)
    public SimpleResponse createActivity(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                         @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                         @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId,
                                         @RequestBody AddActivityForm form
    ){
        logger.info("form {}",form);

        long activityId = activityService.createActivity(form,groupId,
                roleId,userId);
        return SimpleResponse.ok("success");
    }


}

