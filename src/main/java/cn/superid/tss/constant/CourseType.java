package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-21 上午8:59
 **/
public enum CourseType{
    Required(1,"required"), Optional(0,"optional");

    private final String name;
    private final int index;
    CourseType(int index,String name){
        this.index = index;
        this.name = name;
    }

    public static String getName(int index) {
        for (CourseType c : CourseType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
}
