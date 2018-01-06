package cn.superid.tss.service;

import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.RoleGroup;

import java.util.List;
import java.util.Map;

public interface IGroupService {
    Map<String, List> getGroupsOfCourse(long courseId, long userId);

    List<? extends GroupSimple> getMyGroups(long courseId, long userId, boolean detailed);

    List<? extends GroupSimple> getAllGroups(long courseId, boolean detailed);

    List<RoleGroup> getRolesOfGroup(long groupId);

    long createGroup(long userId, long roleId, long courseId, String name, String description);

    void deleteGroup(long groupId);

}
