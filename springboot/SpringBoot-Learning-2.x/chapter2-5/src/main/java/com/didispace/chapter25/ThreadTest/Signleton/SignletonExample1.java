package com.didispace.chapter25.ThreadTest.Signleton;

/**
 * 单例模式
 *
 * 懒汉模式是类实例的第一次创建
 */
public class SignletonExample1 {

    //私有构造器  是为了避免在new 对象时 直接创建对象（单利模式只允许出现一个实例）
    private  SignletonExample1(){
    }
    //单例对象
    private static SignletonExample1 signletonExample1=null;


    //工厂方法
    public static  SignletonExample1 getInstance(){
       if(signletonExample1 ==  null){
           signletonExample1= new SignletonExample1();
       }
        return signletonExample1;
    }

}
