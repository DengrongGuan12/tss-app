package cn.superid.tss.service;

import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;

import java.util.List;

public interface IRoleService {

    List<RoleGroup> getRoleByCourseId(long courseId);

    int deleteRole(long roleId,long courseId);

    /*
      将一个用户加入到一门课程中
     */
    long addToCourse(long courseId,long operatorRoleId,long beAllocatedUserId,String roleTitle,int roleType);

    /*
        通过邀请码加入课程
     */
    long joinCourseByCode(long userId,long couseId,String code);

    /**
     * 获取一个院系下所有的老师
     * @param departmentId
     * @return
     */

    List<Role> getTeachersOfDepartment(long departmentId);

    /**
     * 获取用户在事务中的角色，没有则返回null
     * @param affairId
     * @param userId
     * @return
     */
    Role getRoleInAffair(long affairId, long userId);

    Role getRoleInCourse(long courseId, long userId);


}
