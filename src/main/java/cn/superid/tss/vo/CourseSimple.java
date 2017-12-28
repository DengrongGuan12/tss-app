package cn.superid.tss.vo;

import cn.superid.tss.constant.GradeType;
import cn.superid.tss.constant.SeasonType;
import cn.superid.tss.model.CourseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:36
 **/
@ApiModel
public class CourseSimple {
    private long id;
    @ApiModelProperty(value = "学期比如：2017 Fall")
    private String term;
    private String grade;
    private String name;
    @ApiModelProperty(value = "我扮演的角色:teacher(1),student(0),tutor(3),null(-1)")
    private int roleType;
    @ApiModelProperty(value = "小组列表")
    private List<GroupSimple> groupSimpleList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public List<GroupSimple> getGroupSimpleList() {
        return groupSimpleList;
    }

    public void setGroupSimpleList(List<GroupSimple> groupSimpleList) {
        this.groupSimpleList = groupSimpleList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CourseSimple(){}

    public CourseSimple(CourseEntity courseEntity){
        this.id = courseEntity.getId();
        this.term = courseEntity.getYear()+" "+ SeasonType.getName(courseEntity.getSeason());
        this.grade = GradeType.getName(courseEntity.getGrade());
    }
}
