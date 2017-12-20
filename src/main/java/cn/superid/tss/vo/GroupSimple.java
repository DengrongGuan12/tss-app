package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:41
 **/
@ApiModel
public class GroupSimple {
    private String name;
    @ApiModelProperty(value = "我是否在这个小组里")
    private boolean isMine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
