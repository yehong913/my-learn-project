package com.didispace.chapter25.ThreadTest.finlaExample;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * final 在修饰integer String 等时，值是不允许修改的
 * final 在修饰对象的时候，对象也是允许修改该的，可是能修改 对象内部的值
 * 也就是说：final 修饰时，不允许内存指向地址的修改，但是内存地址的引用对象内部无法判断
 * 所以在修改对象或者引用对象时，时线程不安全的
 */
@Slf4j
public class FinalClassExample1 {

    private  final  static  Integer a=0;
    private  final  static  String  b ="2";
    private  final  static Map<Integer,Integer> map= Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(2,4);
        map.put(4,2);
    }

    public static void main(String[] args) {
        map.put(1,10);
        log.info("map.get(1):"+map.get(1));


    }

}

