package cn.superid.tss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author DengrongGuan
 * @create 2017-12-22 下午1:43
 **/
public class ObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 仅仅用于复制子类信息到父类，之后有需求再加
     * @param fromObject
     * @param toClass
     * @return
     */
    public static Object deepCopy(Object fromObject,Class toClass){
        Object o = null;
        try {
            o = toClass.newInstance();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                field.set(o,field.get(fromObject));
            }
        } catch (Exception e) {
            logger.error("deep copy failed!", e);
        }

        return o;
    }

}
