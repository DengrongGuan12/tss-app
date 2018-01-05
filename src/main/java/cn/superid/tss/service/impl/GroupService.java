package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.AffairDTO;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.common.rest.forms.AffairCreateForm;
import cn.superid.tss.constant.AffairType;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.service.IGroupService;
import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午11:08
 **/
@Service
public class GroupService implements IGroupService {
    @Autowired
    BusinessClient businessClient;

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
        List<AffairDTO> myAffairs = businessClient.getMyChildrenAffair(userId, courseId, AffairType.GROUP.getIndex(), detailed);
        List<GroupSimple> myGroups = myAffairs.stream().map(affairDTO -> {
            if (detailed){
                //TODO 3 获取小组所有角色
                List<RoleInfoDTO> roleInfoDTOS = businessClient.getAffairAllRoles(affairDTO.getId());
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
        List<AffairDTO> allAffairs = businessClient.getChildrenAffairByType(courseId, AffairType.GROUP.getIndex(), true);
        List<GroupSimple> allGroups = allAffairs.stream().map(affairDTO -> {
            if (detailed){
                //TODO 3 获取小组所有角色
                List<RoleInfoDTO> roleInfoDTOS = businessClient.getAffairAllRoles(affairDTO.getId());
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
        List<RoleGroup> roleGroups = new ArrayList<>();
        roleGroups.add(RoleGroup.mockGroupLeader());
        roleGroups.add(RoleGroup.mockGroupMember());
        return roleGroups;
    }

    @Override
    public long createGroup(long userId, long roleId, long courseId, String name, String description) {
        //TODO 3
        AffairCreateForm affairCreateForm = new AffairCreateForm();
        affairCreateForm.setDescription(description);
        affairCreateForm.setName(name);
        affairCreateForm.setParentAffairId(courseId);
        affairCreateForm.setUserId(userId);
        affairCreateForm.setOperationRoleId(roleId);
        affairCreateForm.setOwnerRoleMold(UserType.LEADER.getIndex());
        affairCreateForm.setOwnerRoleTitle(UserType.LEADER.getName());
        affairCreateForm.setAffairMold(AffairType.GROUP.getIndex());
        return businessClient.createAffair(affairCreateForm);
    }
}
