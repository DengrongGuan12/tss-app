package cn.superid.tss.vo;


import io.swagger.annotations.ApiModel;

/**
 * @author liushao
 */
@ApiModel
public class Role {

    private long id;

    private String title;

    private String username;

    private String number = "";//学号，如果有就显示，没有就不显示

    private String avatar = "";//小头像

    private int gender;



    public Role(long id, String title, String username, String number, String avatar, int gender) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.number = number;
        this.avatar = avatar;
        this.gender = gender;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        Role role  = new Role(1234567,"教师","刘钦","",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0);
        return role;

    }

    public static Role MockStudent(){
        Role role  = new Role(1234567,"学生","管哥","MF1632001",
                "http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",0);
        return role;
    }


}
