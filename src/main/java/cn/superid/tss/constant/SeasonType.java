package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-27 下午1:22
 **/
public enum SeasonType {
    SPRING(0,"Spring"),SUMMER(1,"Summer"),FALL(2,"Fall"),WINTER(3,"Winter");
    private final int index;
    private final String name;

    SeasonType(int index,String name){
        this.index = index;
        this.name = name;
    }
    public static String getName(int index) {
        for (SeasonType c : SeasonType.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
    public static int getIndex(String name){
        for (SeasonType c : SeasonType.values()) {
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
