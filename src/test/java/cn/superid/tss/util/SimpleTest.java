package cn.superid.tss.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2018-01-11 下午8:50
 **/
public class SimpleTest {
    public static void main(String[] args) {
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        System.out.println(longs.subList(1,1).size());

    }
}
