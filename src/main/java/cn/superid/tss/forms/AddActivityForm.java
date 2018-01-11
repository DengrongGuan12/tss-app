package cn.superid.tss.forms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author liushao
 */
@ApiModel
public class AddActivityForm {


  @NotBlank(message = "标题不能为空")
  private String title;

  @NotBlank(message = "发布内容不能为空")
  private String content;
  @ApiModelProperty(value = "附件列表")
  private List<AttachmentForm> attachments;

  @ApiModelProperty(value = "\n" +
          "    Teaching(\"教学\",0),\n" +
          "    Homework(\"作业\",1),\n" +
          "    Exam(\"考试\",2),\n" +
          "    other(\"其他\",3),\n" +
          "    Project(\"项目活动\",4);\n" +
          "\n")
  private int type;


  @ApiModelProperty(value = "作业类型 0:普通作业 1:小组作业")
  private int homeworkType;
  @ApiModelProperty(value = "截止日期")
  private Timestamp deadline;


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public List<AttachmentForm> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<AttachmentForm> attachments) {
    this.attachments = attachments;
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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}




