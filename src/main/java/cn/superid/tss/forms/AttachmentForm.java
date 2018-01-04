package cn.superid.tss.forms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AttachmentForm {

    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "文件地址")
    private String url;




    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
