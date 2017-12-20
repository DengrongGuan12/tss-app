package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-20 上午10:15
 **/
@ApiModel
public class UserInfo {
    @ApiModelProperty(value = "类型:teacher,student,dean")
    private String type;

}
