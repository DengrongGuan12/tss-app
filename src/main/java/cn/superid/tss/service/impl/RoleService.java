package cn.superid.tss.service.impl;

import cn.superid.tss.service.IRoleService;
import cn.superid.tss.vo.RoleGroup;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RoleService implements IRoleService{


    @Override
    public List<RoleGroup> getRoleByCourseId(long courseId) {
        List<RoleGroup> groups = new LinkedList<>();
        /*
        TODO
        获得基础服务接口，获得一个事务下的所有角色，再进行业务处理
         */
        groups.add(RoleGroup.mockTeacherGroup());
        groups.add(RoleGroup.mockStudentGroup());

        return groups;
    }
}
