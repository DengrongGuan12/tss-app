package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RoleService implements IRoleService{

    @Autowired
    BusinessClient businessClient;

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
        return 10001l;
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

    //TODO 3
    @Override
    public List<Role> getTeachersOfDepartment(long departmentId) {
        List<Role> roles = new ArrayList<>();
        List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(departmentId, UserType.DEAN.getIndex());
        roleInfoDTOS.stream().forEach(roleInfoDTO -> {

        });
        for (int i = 0;i<10;i++){
            roles.add(Role.MockTeacher());
        }
        return roles;
    }

    @Override
    public Role getRoleInCourse(long courseId, long userId) {
        //TODO 2 获取用户在事务下的角色

        return null;
    }

}
