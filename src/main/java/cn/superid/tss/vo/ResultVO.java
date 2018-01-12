package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;

/**
 * @author DengrongGuan
 * @create 2018-01-11 下午8:00
 **/
@ApiModel
public class ResultVO {
    private int code;
    private String errorMsg;
    private String key;

    public ResultVO(){}

    public ResultVO(int code, String errorMsg, String key) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.key = key;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
