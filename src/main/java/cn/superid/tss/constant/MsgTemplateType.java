package cn.superid.tss.constant;

public enum MsgTemplateType {
    TSS_COURSE_PUBLISH(0,"TSS_COURSE_PUBLISH"),
    TSS_ANNOUNCEMENT_PUBLISH(1,"TSS_ANNOUNCEMENT_PUBLISH"),
    TSS_ADD_MEMBER(2,"TSS_ADD_MEMBER"),
    TSS_REJECT_JOIN(3,"TSS_REJECT_JOIN"),
    TSS_DELETE_ROLE(4,"TSS_DELETE_ROLE"),
    TSS_DELETE_AFFAIR(5,"TSS_DELETE_AFFAIR"),
    TSS_REPLY_COMMENT(6, "TSS_REPLY_COMMENT"),
    TSS_COMMENT_ANNOUNCEMENT(7,"TSS_COMMENT_ANNOUNCEMENT"),
    TSS_APPLY_JOIN(8, "TSS_APPLY_JOIN");
    private final String value;
    private final int index;

    MsgTemplateType(int index, String value){
        this.index = index;
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public int getIndex(){
        return this.index;
    }
}
