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
}




