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

    @Override
    public int deleteRole(long roleId, long courseId) {
        /*
          TODO
           调用superID的从事务中删除角色的接口
         */

        return 0;
    }

    @Override
    public long addToCourse(long userId, long courseId, String roleTitle) {

        /*
            TODO
            调用superID里的在事务中创建角色，为角色分配用户的接口
         */
        return 0;
    }

    @Override
    public long joinCourseByCode(long userId, long couseId, String code) {
        /*
        TODO
        验证邀请码和课程是否匹配
        调用superID里的在事务中创建角色，为角色分配用户的接口
         */
        return 0;
    }

}
