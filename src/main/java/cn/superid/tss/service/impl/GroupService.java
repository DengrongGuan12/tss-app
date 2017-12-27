package cn.superid.tss.service.impl;

import cn.superid.tss.service.IGroupService;
import cn.superid.tss.vo.GroupDetail;
import cn.superid.tss.vo.RoleGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午11:08
 **/
@Service
public class GroupService implements IGroupService{
    @Override
    public Map<String, List<GroupDetail>> getGroupsByCourseId(long courseId) {
        return null;
    }

    @Override
    public List<RoleGroup> getRolesOfGroup(long groupId) {
        List<RoleGroup> roleGroups = new ArrayList<>();
        roleGroups.add(RoleGroup.mockGroupLeader());
        roleGroups.add(RoleGroup.mockGroupMember());
        return roleGroups;
    }
}
