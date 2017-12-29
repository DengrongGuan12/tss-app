package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:51
 **/
public enum UserType {
    NULL("null",-1, "无"),
    STUDENT("student",1, "学生"),
    TEACHER("teacher",2,"老师"),
    DEAN("dean",3,"教务员"),
    TUTOR("tutor",4, "助教"),
    LEADER("leader", 5, "组长"),
    MEMBER("member", 6, "组员");
    private final String chName;
    private final String name;
    private final int index;
    UserType(String typeStr,int typeInt, String chName){
        this.name = typeStr;
        this.index = typeInt;
        this.chName = chName;
    }
    public static String getName(int index) {
        for (UserType c : UserType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
    public int getIndex(){
        return this.index;
    }
    public String getChName(){
        return this.chName;
    }

    public String getName(){
        return this.name;
    }

}
