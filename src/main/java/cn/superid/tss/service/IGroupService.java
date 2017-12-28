package cn.superid.tss.service;

import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.RoleGroup;

import java.util.List;
import java.util.Map;

public interface IGroupService {
    Map<String, List<GroupDetail>> getGroupsByCourseId(long courseId, long userId);

    List<RoleGroup> getRolesOfGroup(long groupId);

    long createGroup(long userId, long roleId, long courseId, String name, String description);
}
