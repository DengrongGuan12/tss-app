package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;

import java.sql.Timestamp;

/**
 * @author liushao
 */
@ApiModel
public class Attachment {

    private long id;

    private String attachmentUrl;

    private String fileName;

    private long activityId;

    private long roleId;

    private String roleTile;

    private long userId;

    private String userName;

    private Timestamp submitTime;

    public Attachment(long id, String attachmentUrl, String fileName, long activity,
                      long roleId, String roleTile, long userId, String userName, Timestamp submitTime) {
        this.id = id;
        this.attachmentUrl = attachmentUrl;
        this.fileName = fileName;
        this.activityId = activity;
        this.roleId = roleId;
        this.roleTile = roleTile;
        this.userId = userId;
        this.userName = userName;
        this.submitTime = submitTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleTile() {
        return roleTile;
    }

    public void setRoleTile(String roleTile) {
        this.roleTile = roleTile;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }
}
