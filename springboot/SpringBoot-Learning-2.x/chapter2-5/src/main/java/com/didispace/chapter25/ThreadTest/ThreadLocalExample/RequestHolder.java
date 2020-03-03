package com.didispace.chapter25.ThreadTest.ThreadLocalExample;


/**
 * 用于计算分配的工具类
 */
public class RequestHolder {

    private final static  ThreadLocal<Long> requestHolder= new ThreadLocal<>();

    public static  void add(long id){
        requestHolder.set(id);
    }

    public static   Long  getId(){
        return requestHolder.get();
    }

    public static  void  remove(){
         requestHolder.remove();
    }

}
