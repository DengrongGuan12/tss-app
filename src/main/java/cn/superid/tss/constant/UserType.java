package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午9:51
 **/
public enum UserType {
    NULL("null",-1),
    STUDENT("student",0),
    TEACHER("teacher",1),
    DEAN("dean",2),
    TUTOR("tutor",3);
    private final String name;
    private final int index;
    UserType(String typeStr,int typeInt){
        this.name = typeStr;
        this.index = typeInt;
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

}
