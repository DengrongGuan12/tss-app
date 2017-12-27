package cn.superid.tss.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DengrongGuan
 * @create 2017-12-22 下午12:20
 **/
public class TimeUtil {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(Date date){
        return simpleDateFormat.format(date);
    }


}
