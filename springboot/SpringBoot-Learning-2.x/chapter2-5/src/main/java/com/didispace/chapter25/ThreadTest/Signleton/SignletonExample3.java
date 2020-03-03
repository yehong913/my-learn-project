package com.didispace.chapter25.ThreadTest.Signleton;

/**
 * 单例模式的线程安全方法
 * 增加双重验证机制，并且在创建实例中增加sychronized
 * 增加synchronized锁对象时，也是线程不安全的
 * 因为指令的重排机制可能会引发线程不安全的
 * 所以这个时候需要使用关键字 volatile 关键字
 *
 * volatile 只能保证对单次读/写的原子性。i++ 这种操作不能保证原子性
 * 懒汉模式是类实例的第一次创建
 */
public class SignletonExample3 {

    //私有构造器  是为了避免在new 对象时 直接创建对象（单利模式只允许出现一个实例）
    private SignletonExample3(){
    }
    //单例对象
    private volatile static SignletonExample3 signletonExample1=null;


    //工厂方法
    public  static SignletonExample3 getInstance(){

       if(signletonExample1 ==  null){//双重验证机制
           synchronized (SignletonExample1.class){
              if(null== signletonExample1){
                  signletonExample1= new SignletonExample3();
              }
           }
           signletonExample1= new SignletonExample3();
       }
        return signletonExample1;
    }

}
