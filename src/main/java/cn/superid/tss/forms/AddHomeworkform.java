package cn.superid.tss.forms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel
public class AddHomeworkform extends AddActivityForm{

    @ApiModelProperty(value = "作业类型 0:普通作业 1:小组作业")
    private int homeworkType;

    private Timestamp deadline;

    public AddHomeworkform(int homeworkType, Timestamp deadline) {
        this.homeworkType = homeworkType;
        this.deadline = deadline;
    }

    public int getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(int homeworkType) {
        this.homeworkType = homeworkType;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }
}
