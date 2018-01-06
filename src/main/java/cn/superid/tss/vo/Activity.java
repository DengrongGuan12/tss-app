package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * @author liushao
 * 课程活动的基类
 */
@ApiModel
public class Activity {

    private long id;

    private String title;

    private String content;

    private long creatorRoleId;

    private long createUserId;

    private String createRoleTitle;

    private String creatorUserName;

    private Timestamp createTime;


    private String creatorAvatar;

    private Timestamp deadline;

    private int homeworkType;

    @ApiModelProperty(value = " Teaching(\"教学\",0),\n" +
            "    Homework(\"作业\",1),\n" +
            "    Exam(\"考试\",2),\n" +
            "    other(\"其他\",3),\n" +
            "    Project(\"项目活动\",4);")
    private int type;

    public Activity(){

    }

    public Activity(long id, String title, String content,
                    long creatorRoleId, long createUserId,
                    String createRoleTitle, String creatorUserName,
                    Timestamp createTime, String avator, int type) {
        this.id = id;
        this.title = title;
        this.content = content;

        this.creatorRoleId = creatorRoleId;
        this.createUserId = createUserId;
        this.createRoleTitle = createRoleTitle;
        this.creatorUserName = creatorUserName;
        this.createTime = createTime;
        this.creatorAvatar = avator;
        this.type = type;
    }

    public Activity(long id, String title, String content, long creatorRoleId,
                    long createUserId, String createRoleTitle, String creatorUserName, Timestamp createTime,
                    String creatorAvatar, Timestamp deadline, int homeworkType, int type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatorRoleId = creatorRoleId;
        this.createUserId = createUserId;
        this.createRoleTitle = createRoleTitle;
        this.creatorUserName = creatorUserName;
        this.createTime = createTime;
        this.creatorAvatar = creatorAvatar;
        this.deadline = deadline;
        this.homeworkType = homeworkType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public int getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(int homeworkType) {
        this.homeworkType = homeworkType;
    }

    public static Activity mockActivity(String title, String content, int type){
        return new Activity(1234,title,content,1234567l,1234,
                "教师","刘钦",
                new Timestamp(System.currentTimeMillis()),"http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png",
                type);
    }
}
