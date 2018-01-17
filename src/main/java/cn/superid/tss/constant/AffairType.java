package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-25 下午4:56
 **/
public enum AffairType {
    COURSE(1,"course","课程"),
    DEPARTMENT(2,"department", "学院"),
    GROUP(3,"group", "小组");


    private final int index;
    private final String name;
    private final String chName;
    AffairType(int index, String name, String chName){
        this.index = index;
        this.name = name;
        this.chName = chName;
    }

    public static String getName(int index) {
        for (AffairType c : AffairType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }

    public static AffairType getType(int index){
        for (AffairType c: AffairType.values()){
            if (c.index == index){
                return c;
            }
        }
        return AffairType.COURSE;
    }

    public String getChName(){
        return this.chName;
    }
    public int getIndex(){
        return this.index;
    }



}
