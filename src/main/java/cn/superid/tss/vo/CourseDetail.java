package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午10:38
 **/
@ApiModel
public class CourseDetail {
    private long id;
    private String name;
    private String number;
    private String description;
    private String term;
    private String grade;
    private String startDate;
    private String endDate;
    private int credit;
    @ApiModelProperty(value = "课程类型:required,optional")
    private String courseType;
    @ApiModelProperty(value = "我扮演的角色:teacher,student,tutor,null")
    private String roleType;

    public CourseDetail(){}

    public CourseDetail(long id,String name,String number,String description,String term,String grade,String startDate,String endDate,int credit, String courseType,String roleType){
        this.id = id;
        this.name = name;
        this.number = number;
        this.description = description;
        this.term = term;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.credit = credit;
        this.courseType = courseType;
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public static CourseDetail mockCourseDetail(){
        return new CourseDetail(1,"计算系统基础","834258","计算机入门","2017 Fall","大一","2017年9月1日","2018年6月1日",2,"Optional","student");
    }
}
