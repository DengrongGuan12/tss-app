package cn.superid.tss.vo;

import java.sql.Timestamp;

public class Submit extends Attachment{

    private int isDelayed;

    public Submit(int isDelayed) {
        this.isDelayed = isDelayed;
    }

    public Submit(long id, String attachmentUrl, String fileName, long activity, long roleId, String roleTile, long userId, String userName, Timestamp submitTime, int isDelayed) {
        super(id, attachmentUrl, fileName, activity, roleId, roleTile, userId, userName, submitTime);
        this.isDelayed = isDelayed;
    }

    public int getIsDelayed() {
        return isDelayed;
    }

    public void setIsDelayed(int isDelayed) {
        this.isDelayed = isDelayed;
    }
}
