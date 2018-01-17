package cn.superid.tss.constant;

public enum  HomeworkType {

    NORMAL(0,"普通作业"),
    GROUP(1,"小组作业");

    private final int index;
    private final String name;

    HomeworkType(int index,String name){
        this.index = index;
        this.name = name;
    }
    public static String getName(int index) {
        for (HomeworkType c : HomeworkType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }

    public int getIndex(){
        return this.index;
    }

}
