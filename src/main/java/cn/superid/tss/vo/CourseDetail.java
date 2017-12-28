package cn.superid.tss.vo;

import cn.superid.tss.constant.GradeType;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.util.TimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

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
    @ApiModelProperty(value = "课程类型:required(1),optional(0)")
    private int courseType;
    @ApiModelProperty(value = "我扮演的角色:teacher(1),student(0),tutor(3),null(-1)")
    private int roleType;
    @ApiModelProperty(value = "邀请码，仅教师视角展示")
    private String inviteCode;

    private List<Role> teachers;

    public CourseDetail(){}

    public CourseDetail(long id,String name,String number,String description,String term,String grade,String startDate,String endDate,int credit, int courseType,int roleType, List<Role> roles, String inviteCode){
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
        this.teachers = roles;
        this.inviteCode = inviteCode;
    }

    public CourseDetail(CourseEntity courseEntity){
        this.id = courseEntity.getId();
        this.number = courseEntity.getNumber();
        this.term = courseEntity.getYear() + " " + courseEntity.getSeason();
        this.grade = GradeType.getName(courseEntity.getGrade());
        this.startDate = TimeUtil.dateToString(courseEntity.getStartDate());
        this.endDate = TimeUtil.dateToString(courseEntity.getEndDate());
        this.credit = courseEntity.getCredit();
        this.courseType = courseEntity.getType();
        this.inviteCode = courseEntity.getInviteCode();
    }

    public List<Role> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Role> teachers) {
        this.teachers = teachers;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
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

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public static CourseDetail mockCourseDetail(){
        List<Role> roles = new ArrayList<>();
        for (int i = 0;i<3;i++){
            roles.add(Role.MockTeacher());
        }
        return new CourseDetail(1,"计算系统基础","834258","计算机入门","2017 Fall","大一","2017年9月1日","2018年6月1日",2,0,0,roles,"wet345");
    }
}
