package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-25 下午4:56
 **/
public enum AffairType {
    COURSE(5,"course"),
    DEPARTMENT(6,"department"),
    GROUP(7,"group");


    private final int index;
    private final String name;
    AffairType(int index, String name){
        this.index = index;
        this.name = name;
    }

    public static String getName(int index) {
        for (AffairType c : AffairType.values()) {
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
