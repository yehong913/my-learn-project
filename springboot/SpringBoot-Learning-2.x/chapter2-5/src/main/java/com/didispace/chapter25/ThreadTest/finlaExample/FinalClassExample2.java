package com.didispace.chapter25.ThreadTest.finlaExample;


import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * Collections.unmodifiable  这个包下面的集合包 在初始化话之后都是不允许修改的
 * 修改报错
*Exception in thread "main" java.lang.UnsupportedOperationException
 */
@Slf4j
public class FinalClassExample2 {

    private  final  static  Integer a=0;
    private  final  static  String  b ="2";
    private   static Map<Integer,Integer> map= Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(2,4);
        map.put(4,2);
        map= Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1,10);
        log.info("map.get(1):"+map.get(1));


    }

}

