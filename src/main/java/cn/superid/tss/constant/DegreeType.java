package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2017-12-20 上午10:57
 **/
public enum DegreeType {
    BACHELOR("本科",0),MASTER("硕士",1),DOCTOR("博士",2);
    private final String name;
    private final int index;
    DegreeType(String name,int index){
        this.name = name;
        this.index = index;
    }
    public static String getName(int index) {
        for (DegreeType c : DegreeType.values()) {
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
