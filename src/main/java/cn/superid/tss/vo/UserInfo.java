package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-20 上午10:15
 **/
@ApiModel
public class UserInfo {
    @ApiModelProperty(value = "类型:teacher,student,dean")
    private String type;
    private String number;
    private String department;
    @ApiModelProperty(value = "年级如:2017级")
    private String grade;
    @ApiModelProperty(value = "学历如:本科")
    private String degree;
    private int gender;
    private String realName;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    private PersonInfoPublic personInfoPublic;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PersonInfoPublic getPersonInfoPublic() {
        return personInfoPublic;
    }

    public void setPersonInfoPublic(PersonInfoPublic personInfoPublic) {
        this.personInfoPublic = personInfoPublic;
    }

    public static UserInfo mockUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setDegree("本科");
        userInfo.setDepartment("软件学院");
        userInfo.setGender(0);
        userInfo.setGrade("2017级");
        userInfo.setNumber("MF1632020");
        userInfo.setRealName("管登荣");
        userInfo.setAvatar("http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png");
        userInfo.setMobile("15950570277");
        userInfo.setPersonInfoPublic(PersonInfoPublic.setFalse());
        userInfo.setType("student");
        return userInfo;
    }
}
