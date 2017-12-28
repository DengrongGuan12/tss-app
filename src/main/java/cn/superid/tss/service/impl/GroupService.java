package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.AffairDTO;
import cn.superid.common.rest.forms.AffairCreateForm;
import cn.superid.tss.constant.AffairType;
import cn.superid.tss.service.IGroupService;
import cn.superid.tss.vo.GroupDetail;
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
    public Map<String, List<GroupDetail>> getGroupsByCourseId(long courseId, long userId) {
        Map<String,List<GroupDetail>> result = new LinkedHashMap<>();


        return null;
    }

    public List<GroupDetail> getMyGroups(long courseId, long userId){
        //TODO 3 获取我的小组:需要描述字段
        List<AffairDTO> myAffairs = businessClient.getMyChildrenAffair(userId, courseId, AffairType.GROUP.getIndex());
        List<Long> myAffairIds = new ArrayList<>();//用于过滤全部小组
        List<GroupDetail> myGroups = myAffairs.stream().map(affairDTO -> {
            List<Role> roles = new ArrayList<>();
            return new GroupDetail(affairDTO.getId(),affairDTO.getName(),true, null, roles);
        }).collect(Collectors.toList());
        return myGroups;
    }

    public List<GroupDetail> getAllGroups(long courseId, long userId){
        //TODO 3 获取该课程下所有小组
        List<AffairDTO> allAffairs = businessClient.getChildrenAffairByType(courseId, AffairType.GROUP.getIndex());
        List<GroupDetail> allGroups = allAffairs.stream().map(affairDTO -> {
            List<Role> roles = new ArrayList<>();
            return new GroupDetail(affairDTO.getId(),affairDTO.getName(),true, null, roles);
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
        return businessClient.createAffair(affairCreateForm);
    }
}
