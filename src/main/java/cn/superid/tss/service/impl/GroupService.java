package cn.superid.tss.service.impl;

import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.MsgType;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.AffairDTO;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.common.rest.forms.AffairCreateForm;
import cn.superid.id_client.IdClient;
import cn.superid.tss.constant.*;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.msg.MsgComponent;
import cn.superid.tss.service.IGroupService;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.util.JSONObjectBuilder;
import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfo;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午11:08
 **/
@Service
public class GroupService implements IGroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    BusinessClient businessClient;

    @Autowired
    IRoleService roleService;

    @Autowired
    IdClient idClient;

    @Autowired
    MsgComponent msgComponent;


    @Override
    public Map<String, List> getGroupsOfCourse(long courseId, long userId) {
        List<? extends GroupSimple> myGroups = getMyGroups(courseId,userId,true);
        Set<Long> myGroupIds = myGroups.stream().map(group -> group.getId()).collect(Collectors.toSet());
        List<? extends GroupSimple> allGroups = getAllGroups(courseId,true);
        List<? extends GroupSimple> allOtherGroups = allGroups.stream().filter(group -> ! myGroupIds.contains(group.getId())).collect(Collectors.toList());
        Map<String,List> result = new LinkedHashMap<>();
        result.put("我的小组",myGroups);
        result.put("所有小组",allOtherGroups);
        return result;
    }

    public List<? extends GroupSimple> getMyGroups(long courseId, long userId, boolean detailed){
        //TODO 3 获取我的小组
        List<AffairDTO> myAffairs = businessClient.getMyChildrenAffair(userId, courseId, AffairType.GROUP.getIndex(), 0, detailed);
        List<GroupSimple> myGroups = myAffairs.stream().map(affairDTO -> {
            if (detailed){
                //TODO 3 获取小组所有角色
                List<RoleInfoDTO> roleInfoDTOS = businessClient.getAffairAllRoles(affairDTO.getId(), StateType.NORMAL.getIndex());
                //默认是组员
                final int[] roleType = {UserType.MEMBER.getIndex()};
                List<Role> roles = roleInfoDTOS.stream().map(roleInfoDTO -> {
                    if (roleInfoDTO.getUserId() == userId){
                        //TODO 3
                        roleType[0] = roleInfoDTO.getMold();
                    }
                    return new Role(roleInfoDTO);
                }).collect(Collectors.toList());
                //TODO 3
                return new GroupDetail(affairDTO.getId(),affairDTO.getName(),true, affairDTO.getDescription(), roles, roleType[0]);
            }else{
                return new GroupSimple(affairDTO.getId(),affairDTO.getName(),true);
            }
        }).collect(Collectors.toList());
        return myGroups;
    }

    public List<? extends GroupSimple> getAllGroups(long courseId, boolean detailed){
        //TODO 3 获取该课程下所有小组（包括自己的小组）
        List<AffairDTO> allAffairs = businessClient.getChildrenAffairByType(courseId, AffairType.GROUP.getIndex(), StateType.NORMAL.getIndex(), true);
        List<GroupSimple> allGroups = allAffairs.stream().map(affairDTO -> {
            if (detailed){
                //TODO 3 获取小组所有角色
                List<RoleInfoDTO> roleInfoDTOS = businessClient.getAffairAllRoles(affairDTO.getId(), StateType.NORMAL.getIndex());
                List<Role> roles = roleInfoDTOS.stream().map(roleInfoDTO -> new Role(roleInfoDTO)).collect(Collectors.toList());
                //TODO 3
                return new GroupDetail(affairDTO.getId(),affairDTO.getName(),false, affairDTO.getDescription(), roles, -1);
            }else{
                return new GroupSimple(affairDTO.getId(),affairDTO.getName(),false);
            }
        }).collect(Collectors.toList());
        return allGroups;
    }

    @Override
    public List<RoleGroup> getRolesOfGroup(long groupId) {
        //TODO 3
        List<RoleGroup> roleGroups = new ArrayList<>();
        List<RoleInfoDTO> leaderInfoDTOS = businessClient.getRolesByType(groupId, UserType.LEADER.getIndex(), StateType.NORMAL.getIndex());
        List<RoleInfoDTO> memberInfoDTOS = businessClient.getRolesByType(groupId, UserType.MEMBER.getIndex(), StateType.NORMAL.getIndex());
        List<RoleInfoDTO> tutorInfoDTOS = businessClient.getRolesByType(groupId, UserType.TUTOR.getIndex(), StateType.NORMAL.getIndex());
        roleGroups.add(new RoleGroup(UserType.LEADER.getIndex(),
                leaderInfoDTOS.stream().map(roleInfoDTO -> new Role(roleInfoDTO)).collect(Collectors.toList())));
        roleGroups.add(new RoleGroup(UserType.MEMBER.getIndex(),
                memberInfoDTOS.stream().map(roleInfoDTO -> new Role(roleInfoDTO)).collect(Collectors.toList())));
        roleGroups.add(new RoleGroup(UserType.TUTOR.getIndex(),
                tutorInfoDTOS.stream().map(roleInfoDTO -> new Role(roleInfoDTO)).collect(Collectors.toList())));
        return roleGroups;
    }



    @Override
    public long createGroup(long userId, long roleId, long courseId, String name, String description) {
        long groupId = idClient.nextId("business","affair");
        AffairCreateForm affairCreateForm = new AffairCreateForm();
        affairCreateForm.setId(groupId);
        affairCreateForm.setDescription(description);
        affairCreateForm.setName(name);
        affairCreateForm.setParentAffairId(courseId);
        affairCreateForm.setUserId(userId);
        affairCreateForm.setOperationRoleId(roleId);
        //TODO 3 获取角色详情，判断是老师，助教还是学生。自动把老师加入小组
        List<RoleInfoDTO> roleInfoDTOS = businessClient.fillRole(new Long[]{roleId});
        if (roleInfoDTOS == null || roleInfoDTOS.size() == 0){
            throw new ErrorCodeException(ResponseCode.PARAM_ERROR,"找不到对应的角色!");
        }
        RoleInfoDTO roleInfoDTO = roleInfoDTOS.get(0);
        if (roleInfoDTO.getMold() == UserType.STUDENT.getIndex()){
            affairCreateForm.setOwnerRoleMold(UserType.LEADER.getIndex());
            affairCreateForm.setOwnerRoleTitle(UserType.LEADER.getName());
        }else{
            affairCreateForm.setOwnerRoleMold(roleInfoDTO.getMold());
            affairCreateForm.setOwnerRoleTitle(UserType.getName(roleInfoDTO.getMold()));
        }
        affairCreateForm.setAffairMold(AffairType.GROUP.getIndex());
//        logger.info("affairCreateForm:"+JSON.toJSONString(affairCreateForm));
        SimpleResponse simpleResponse = businessClient.createAffair(affairCreateForm);
//        logger.info("simpleResponse:"+JSON.toJSONString(simpleResponse));
        if (simpleResponse.getCode() == 0){
//            return new Long((Integer) simpleResponse.getData());
            List<RoleInfoDTO> teachers = businessClient.getRolesByType(courseId, UserType.TEACHER.getIndex(), StateType.NORMAL.getIndex());
            if (teachers != null){
                Long[] roleIds = teachers.stream().filter(t -> t.getUserId() != userId).map(t -> t.getRoleId()).toArray(Long[]::new);
                if (roleIds.length != 0){
                    roleService.addMember(groupId, roleId, roleIds, UserType.TEACHER.getName(), UserType.TEACHER.getIndex(), AffairType.GROUP);
                }
            }
        }else{
            throw new ErrorCodeException(ResponseCode.CATCH_EXCEPTION,(String) simpleResponse.getData());
        }
        return groupId;

    }

    @Override
    public void deleteGroup(long roleId, long groupId) {
        //TODO 3
        List<Long> receiverIds = roleService.getRoleIdsInAffair(groupId);
        JSONObject jsonObject = new JSONObjectBuilder().put("affairType",AffairType.GROUP.getChName()).getJsonObject();
        CommonMessage commonMessage = msgComponent.genCommonMsg(groupId, roleId, receiverIds, MsgType.GROUP, ResourceType.AFFAIR, groupId, MsgTemplateType.TSS_DELETE_AFFAIR, jsonObject);
        businessClient.disableAffair(roleId, groupId, commonMessage);

    }

    @Override
    public List<RoleInfoDTO> getLeadersOfGroup(long groupId) {
        return businessClient.getRolesByType(groupId, UserType.LEADER.getIndex(), StateType.NORMAL.getIndex());
    }
}
