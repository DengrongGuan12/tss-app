package cn.superid.tss.vo;


import io.swagger.annotations.ApiModel;

/**
 * @author liushao
 */
@ApiModel
public class Role {

    private long id;

    private long userId;

    private String title;

    private String realName;

    private String number = "";//学号，如果有就显示，没有就不显示

    private String avatar = "";//小头像

    private int gender;

    public Role(long id, long userId, String title, String realName, String number, String avatar, int gender) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.realName = realName;
        this.number = number;
        this.avatar = avatar;
        this.gender = gender;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String username) {
        this.realName = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public static Role MockTeacher(){
        Role role  = new Role(1234567,123,"教师","刘钦","",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0);
        return role;

    }

    public static Role MockStudent(){
        Role role  = new Role(1234567,123,"学生","管哥","MF1632001",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0);
        return role;
    }


}
