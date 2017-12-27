package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liushao
 * 角色组，如一组老师，一组学生等，主要为了拼接response
 */
@ApiModel
public class RoleGroup {

    private String roleType;

    private List<Role> roleList;

    public RoleGroup(String roleType, List<Role> roleList) {
        this.roleType = roleType;
        this.roleList = roleList;
    }

    public void addRole(Role role){
        if(null == roleList){
            roleList = new ArrayList<>();
        }
        roleList.add(role);
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public static RoleGroup mockTeacherGroup(){

        RoleGroup rg = new RoleGroup("教师",null);
        rg.addRole(Role.MockTeacher());
        rg.addRole(Role.MockTeacher());
        rg.addRole(Role.MockTeacher());

        return rg;
    }

    public static RoleGroup mockStudentGroup(){
        RoleGroup rg = new RoleGroup("学生",null);
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());

        return rg;
    }

    public static RoleGroup mockGroupMember(){
        RoleGroup rg = new RoleGroup("组员",null);
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        rg.addRole(Role.MockStudent());
        return rg;
    }

    public static RoleGroup mockGroupLeader(){
        RoleGroup rg = new RoleGroup("组长",null);
        rg.addRole(Role.MockStudent());
        return rg;
    }
}

