package com.didispace.chapter25.ThreadTest.Signleton;

/**
 * 单例模式
 *
 * 饥汉模式是类装载的时候直接创建
 */
public class SignletonExample2 {

    //私有构造器  是为了避免在new 对象时 直接创建对象（单利模式只允许出现一个实例）
    private SignletonExample2(){
    }
    //单例对象
    private static SignletonExample2 signletonExample1=new SignletonExample2();


    //工厂方法
    public static SignletonExample2 getInstance(){
        return signletonExample1;
    }

}
