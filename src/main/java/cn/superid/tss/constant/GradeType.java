package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-27 下午1:34
 **/
public enum GradeType {
    FRESH(0,"大一"),SOPHOMORE(1,"大二"),JUNIOR(2,"大三"),SENIOR(3,"大四"),MASTER1(4,"研一"),MASTER2(5,"研二"),MASTER3(6,"研三");

    private final int index;
    private final String name;

    GradeType(int index,String name){
        this.index = index;
        this.name = name;
    }
    public static String getName(int index) {
        for (GradeType c : GradeType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
    public static int getIndex(String name){
        for (GradeType c : GradeType.values()) {
            if (c.name.equals(name)) {
                return c.index;
            }
        }
        return -1;
    }
    public int getIndex(){
        return this.index;
    }

}
