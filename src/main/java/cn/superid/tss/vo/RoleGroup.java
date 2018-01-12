package cn.superid.tss.vo;

import cn.superid.tss.constant.UserType;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liushao
 * 角色组，如一组老师，一组学生等，主要为了拼接response
 */
@ApiModel
public class RoleGroup {

    private int roleType;

    private List<Role> roleList;

    public RoleGroup(int roleType, List<Role> roleList) {
        this.roleType = roleType;
        this.roleList = roleList;
    }

    public void addRole(Role role){
        if(null == roleList){
            roleList = new ArrayList<>();
        }
        roleList.add(role);
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public static RoleGroup mockTeacherGroup(){

        RoleGroup rg = new RoleGroup(UserType.TEACHER.getIndex(),null);
        rg.addRole(Role.MockTeacher());
        rg.addRole(Role.MockTeacher());
        rg.addRole(Role.MockTeacher());

        return rg;
    }

    public static RoleGroup mockStudentGroup(){
        RoleGroup rg = new RoleGroup(UserType.STUDENT.getIndex(),null);
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());

        return rg;
    }

    public static RoleGroup mockGroupMember(){
        RoleGroup rg = new RoleGroup(UserType.MEMBER.getIndex(),null);
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        return rg;
    }

    public static RoleGroup mockGroupLeader(){
        RoleGroup rg = new RoleGroup(UserType.LEADER.getIndex(),null);
        rg.addRole(Role.MockStudent());
        return rg;
    }
}

