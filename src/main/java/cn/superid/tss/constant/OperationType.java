package cn.superid.tss.constant;

/**
 * @author DengrongGuan
 * @create 2018-01-17 上午9:41
 **/
public enum OperationType {
    PUBLISH(0,"发布"),
    MODIFY(1,"修改");
    private final int index;
    private final String name;
    OperationType(int index, String name) {
        this.index = index;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

}
