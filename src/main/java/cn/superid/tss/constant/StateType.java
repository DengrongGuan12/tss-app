package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2018-01-10 上午9:23
 **/
public enum StateType {
    NORMAL(0,"NORMAL"),
    DELETED(1,"DELETED");

    private final String name;
    private final int index;
    StateType(int index, String name){
        this.name = name;
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
}
