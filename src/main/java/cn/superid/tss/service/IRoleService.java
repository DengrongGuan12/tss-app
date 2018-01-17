package cn.superid.tss.service;

import cn.superid.common.rest.dto.RoleInfoDTO;
import cn.superid.tss.constant.AffairType;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;

import java.util.List;

public interface IRoleService {

    List<RoleGroup> getRoleByCourseId(long courseId);

    /**
     *
     * @param operateId
     * @param roleId
     * @param affairId
     * @param affairType
     * @param active 主动 or 被动 决定了是否发消息通知
     * @return
     */
    int deleteRole(long operateId,long roleId,long affairId, AffairType affairType, boolean active);

    /*
      将一个用户加入到一门课程/小组中
     */
    List<Long> addMember(long courseId, long operatorRoleId, Long[] beAllocatedRoleId, String roleTitle, int roleType, AffairType affairType);

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

    List<Role> getRolesByIds(Long[] roleId);

    List<Long> getRoleIdsInAffairWithType(long affairId, UserType type);

    List<Long> getRoleIdsInAffair(long affairId);

    void rejectJoin(long affairId, long roleId, AffairType affairType, long rejectedId, String reason);

    void applyJoin(long affairId, long roleId, AffairType affairType, String reason);

}
