package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SubmitCount {

    @ApiModelProperty("应提交份数")
    private int total;
    @ApiModelProperty("已经提交份数")
    private int submitted;
    @ApiModelProperty("尚未提交份数")
    private int toSubmit;


    public SubmitCount() {}

    public SubmitCount(int total, int submitted, int toSubmit) {
        this.total = total;
        this.submitted = submitted;
        this.toSubmit = toSubmit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }

    public int getToSubmit() {
        return toSubmit;
    }

    public void setToSubmit(int toSubmit) {
        this.toSubmit = toSubmit;
    }
}
