package cn.superid.tss.constant;

public enum ActivityType {

    Teaching("教学",0),
    Homework("作业",1),
    Exam("考试",2),
    other("其他",3),
    Project("项目活动",4);


    private final String name;

    private final int index;

    ActivityType(String name,int index){
        this.name = name;
        this.index = index;
    }

}
