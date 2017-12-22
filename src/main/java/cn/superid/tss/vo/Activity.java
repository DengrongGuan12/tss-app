package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;

import java.sql.Timestamp;

/**
 * @author liushao
 * 课程活动的基类
 */
@ApiModel
public class Activity {

    private long id;

    private String title;

    private long creatorRoleId;

    private long createUserId;

    private String createRoleTitle;

    private String creatorUserName;

    private Timestamp createTime;

    private String avator;

    private String type;

    public Activity(long id, String title,
                    long creatorRoleId, long createUserId,
                    String createRoleTitle, String creatorUserName,
                    Timestamp createTime, String avator, String type) {
        this.id = id;
        this.title = title;
        this.creatorRoleId = creatorRoleId;
        this.createUserId = createUserId;
        this.createRoleTitle = createRoleTitle;
        this.creatorUserName = creatorUserName;
        this.createTime = createTime;
        this.avator = avator;
        this.type = type;
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

    public long getCreatorRoleId() {
        return creatorRoleId;
    }

    public void setCreatorRoleId(long creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateRoleTitle() {
        return createRoleTitle;
    }

    public void setCreateRoleTitle(String createRoleTitle) {
        this.createRoleTitle = createRoleTitle;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
