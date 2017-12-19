package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:00
 **/
@ApiModel
public class PersonInfoPublic {
    @ApiModelProperty("true 公开 false 不公开")
    private boolean realname;
    private boolean birthday;
    private boolean email;
    private boolean mobile;

    public PersonInfoPublic(){}

    public PersonInfoPublic(boolean realname, boolean birthday, boolean email, boolean mobile){
        this.realname = realname;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
    }

    public static PersonInfoPublic setFalse(){
        return new PersonInfoPublic(false, false, false, false);
    }

    public static PersonInfoPublic setTrue(){
        return new PersonInfoPublic(true, true, true, true);
    }

    public boolean isRealname() {
        return realname;
    }

    public void setRealname(boolean realname) {
        this.realname = realname;
    }

    public boolean isBirthday() {
        return birthday;
    }

    public void setBirthday(boolean birthday) {
        this.birthday = birthday;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }
}
