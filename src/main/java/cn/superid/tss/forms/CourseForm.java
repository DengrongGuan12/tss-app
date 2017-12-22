package cn.superid.tss.forms;

import cn.superid.tss.model.CourseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2017-12-22 上午10:15
 **/
@ApiModel
public class CourseForm extends CourseEntity{
    @ApiModelProperty(required = true)
    private String name;
    @ApiModelProperty(required = true)
    private List<Long> teacherList;
    @ApiModelProperty(required = true)
    private String description;
    public CourseForm(){}

    public CourseForm(long id, String number, String year, String season, String grade, Date startDate, Date endDate, int credit, String inviteCode, int type, String name, List<Long> teacherList, String description) {
        super(id, number, year, season, grade, startDate, endDate, credit, inviteCode, type);
        this.name = name;
        this.teacherList = teacherList;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Long> teacherList) {
        this.teacherList = teacherList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static CourseForm mock(long id){
        return new CourseForm(id,"124","2015","Fall","大一",new Date(),new Date(),4,null,0,"软件工程",null,null);
    }


}
