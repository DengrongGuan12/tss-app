package cn.superid.tss.vo;

import java.sql.Timestamp;

public class Homework extends Activity{

    private Timestamp deadline;

    private int homeworkType;

    public Homework(){}

    public Homework(long id, String title, String content, long creatorRoleId, long createUserId, String createRoleTitle, String creatorUserName, Timestamp createTime, String avator, int type, Timestamp deadline, int homeworkType) {
        super(id, title, content, creatorRoleId, createUserId, createRoleTitle, creatorUserName, createTime, avator, type);
        this.deadline = deadline;
        this.homeworkType = homeworkType;
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
}
