package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.service.IGroupService;
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

    @ApiOperation(value = "创建小组", response = SimpleResponse.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public SimpleResponse createGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestParam("name")String name,
                                      @RequestParam("description")String description){
        //TODO 仅该课程的学生能创建小组，其他不做限制
        groupService.createGroup(userId,roleId,courseId,name,description);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "获取一个课程下面的所有小组",response = GroupDetail.class)
    @RequestMapping(value = "/getGroupsOfCourse", method = RequestMethod.GET)
    public SimpleResponse getGroups(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                    @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                    @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId){
        Map<String,List<GroupDetail>> map = new HashMap<>();
        List<GroupDetail> myGroup = new ArrayList<>();
        myGroup.add(GroupDetail.mockMyGroup());
        map.put("我的小组",myGroup);
        List<GroupDetail> otherGroups = new ArrayList<>();
        for (int i = 0;i<10;i++){
            otherGroups.add(GroupDetail.mockOtherGroup());
        }
//        List<GroupSimple> myGroup2 = groupService.getMyGroups(courseId,userId,true);
        map.put("所有小组",otherGroups);
        return SimpleResponse.ok(map);
    }

    @ApiOperation(value = "退出小组", response = SimpleResponse.class)
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public SimpleResponse exitGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                    @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                    @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId){
        // 只有组员能退出小组
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "删除小组", response = SimpleResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public SimpleResponse deleteGroup(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                    @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId){
        // 只有组长能删除小组
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "申请加入小组",response = SimpleResponse.class)
    @RequestMapping(value = "/applyToJoin", method = RequestMethod.GET)
    public SimpleResponse applyToJoin(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId){
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "邀请组员",response = SimpleResponse.class)
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public SimpleResponse invite(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                 @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                 @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId,
                                 @RequestParam("userId")long invitedId){
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "获取小组所有成员(组长，组员，助教，老师)",response = RoleGroup.class)
    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public SimpleResponse getRoles(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long groupId){

        return SimpleResponse.ok(groupService.getRolesOfGroup(groupId));
    }


}

