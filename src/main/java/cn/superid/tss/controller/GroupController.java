package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.service.IGroupService;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午10:03
 **/
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    IGroupService groupService;

    @Autowired
    IRoleService roleService;

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
            roleService.deleteRole(role.getId(), role.getId(), groupId);
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
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "申请加入小组", response = SimpleResponse.class)
    @RequestMapping(value = "/applyToJoin", method = RequestMethod.POST)
    public SimpleResponse applyToJoin(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "reason") String reason,
                                      @RequestParam(value = "groupId") long groupId) {
        //TODO 1
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "邀请组员", response = SimpleResponse.class)
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public SimpleResponse invite(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                 @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                 @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId,
                                 @RequestParam("userId") long invitedId) {
        //TODO 只有组长老师助教能邀请组员
//        Role role = roleService.getRoleById()
        List<RoleInfoDTO> leaders = groupService.getLeadersOfGroup(groupId);
        String roleTitle = UserType.MEMBER.getName();
        int roleType = UserType.MEMBER.getIndex();
        if (leaders.size() == 0) {
            //如果小组里还没有组长就设置该《学生》为组长
            roleTitle = UserType.LEADER.getName();
            roleType = UserType.LEADER.getIndex();
        }
        roleService.addMember(groupId, roleId, invitedId, roleTitle, roleType);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "获取小组所有成员(组长，组员，助教，老师)", response = RoleGroup.class)
    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public SimpleResponse getRoles(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                   @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                   @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId) {

        List<RoleGroup> roleGroups = new ArrayList<>();
        roleGroups.add(RoleGroup.mockGroupLeader());
        roleGroups.add(RoleGroup.mockGroupMember());
//        List<RoleGroup> roleGroups = groupService.getRolesOfGroup(groupId);
        return SimpleResponse.ok(roleGroups);
    }


}

