package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class Comment {
    private long id;
    private Role role;
    private String content;
    private Date time;
    private Role toRole;

    public Comment(long id, Role role, String content, Date time, Role toRole) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.time = time;
        this.toRole = toRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Role getToRole() {
        return toRole;
    }

    public void setToRole(Role toRole) {
        this.toRole = toRole;
    }

    public static Comment mock(long id){
        Role role = Role.MockStudent();
        Role toRole = Role.MockTeacher();
        return new Comment(id, role, "sdsdfsdfsdfsdfsdfsdfkjljlkjlkjiuijkjlknklajdfjl",new Date(), toRole);
    }
}
