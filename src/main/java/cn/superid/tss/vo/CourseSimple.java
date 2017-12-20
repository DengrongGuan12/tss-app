package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:36
 **/
@ApiModel
public class CourseSimple {
    @ApiModelProperty(value = "学期比如：2017Fall")
    private String term;
    private String grade;
    private String name;
    @ApiModelProperty(value = "我扮演的角色:teacher,student,tutor")
    private String roleType;
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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<GroupSimple> getGroupSimpleList() {
        return groupSimpleList;
    }

    public void setGroupSimpleList(List<GroupSimple> groupSimpleList) {
        this.groupSimpleList = groupSimpleList;
    }
}
