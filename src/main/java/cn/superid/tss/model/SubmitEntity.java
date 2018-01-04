package cn.superid.tss.model;

import org.exemodel.orm.ExecutableModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "submit")
public class SubmitEntity extends CModel{


    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "attachment_url")
    private String attachmentUrl;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "activity_id")
    private long activityId;
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "role_title")
    private String roleTitle;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "submit_time")
    private Timestamp submitTime;
    @Column(name = "is_delayed")
    private int isDelayed;

    public SubmitEntity(long id, String attachmentUrl, String fileName, long activityId,
                        long roleId, String roleTitle, long userId, String userName,
                        Timestamp submitTime, int isDelayed) {
        this.id = id;
        this.attachmentUrl = attachmentUrl;
        this.fileName = fileName;
        this.activityId = activityId;
        this.roleId = roleId;
        this.roleTitle = roleTitle;
        this.userId = userId;
        this.userName = userName;
        this.submitTime = submitTime;
        this.isDelayed = isDelayed;
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

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
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

    public int getIsDelayed() {
        return isDelayed;
    }

    public void setIsDelayed(int isDelayed) {
        this.isDelayed = isDelayed;
    }
}
