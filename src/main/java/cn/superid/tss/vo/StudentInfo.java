package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-15 上午9:19
 **/
@ApiModel
public class StudentInfo {
    private String realName;
    private String stuNumber;
    private int gender;
    private String department;
    private String grade;
    private String degree;
    @ApiModelProperty(value = "小头像")
    private String avatar;
    @ApiModelProperty(value = "原头像")
    private String originAvatar;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    private PersonInfoPublic personInfoPublic;

    public PersonInfoPublic getPersonInfoPublic() {
        return personInfoPublic;
    }

    public void setPersonInfoPublic(PersonInfoPublic personInfoPublic) {
        this.personInfoPublic = personInfoPublic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOriginAvatar() {
        return originAvatar;
    }

    public void setOriginAvatar(String originAvatar) {
        this.originAvatar = originAvatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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
}
