package cn.superid.tss.vo;


import cn.superid.common.rest.dto.business.RoleInfoDTO;
import io.swagger.annotations.ApiModel;

import javax.management.relation.RoleInfo;

/**
 * @author liushao
 */
@ApiModel
public class Role {

    private long id;

    private long userId;

    private String title;

    private String realName;

    private String number = "";//学号，如果有就显示，没有就不显示

    private String avatar = "";//小头像

    private int gender;

    private int roleType;

    public Role(){}

    public Role(long id, long userId, String title, String realName, String number, String avatar, int gender,int roleType) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.realName = realName;
        this.number = number;
        this.avatar = avatar;
        this.gender = gender;
        this.roleType = roleType;
    }

    public Role(RoleInfoDTO roleInfoDTO){
        this.id = roleInfoDTO.getRoleId();
        this.userId = roleInfoDTO.getUserId();
        this.title = roleInfoDTO.getRoleTitle();
        this.realName = roleInfoDTO.getUsername();
        this.avatar = roleInfoDTO.getAvatar();
        this.gender = roleInfoDTO.getGender();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String username) {
        this.realName = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType){
        this.roleType = roleType;
    }

    public static Role MockTeacher(){
        Role role  = new Role(1234567,123,"教师","刘钦","",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0,2);
        return role;

    }

    public static Role MockStudent(){
        Role role  = new Role(1234567,123,"学生","管哥","MF1632001",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0,1);
        return role;
    }


}
