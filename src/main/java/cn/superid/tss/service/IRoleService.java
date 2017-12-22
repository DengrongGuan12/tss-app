package cn.superid.tss.service;

import cn.superid.tss.vo.RoleGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IRoleService {

    List<RoleGroup> getRoleByCourseId(long courseId);

    int deleteRole(long roleId,long courseId);

    /*
      将一个用户加入到一门课程中
     */
    long addToCourse(long userId,long courseId,String roleTitle);

    /*
        通过邀请码加入课程
     */
    long joinCourseByCode(long userId,long couseId,String code);


}
