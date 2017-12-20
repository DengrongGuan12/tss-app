package cn.superid.tss.util;

import java.lang.reflect.Field;

/**
 * @author DengrongGuan
 * @create 2017-12-19 上午10:50
 **/
public class MockUtil {
    public Object mock(Class c){
        Object o = null;
        try {
            o = c.newInstance();
            Field[] fields = c.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
