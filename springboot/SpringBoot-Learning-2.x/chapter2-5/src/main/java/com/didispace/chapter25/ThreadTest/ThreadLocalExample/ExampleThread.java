package com.didispace.chapter25.ThreadTest.ThreadLocalExample;

public class ExampleThread {

    public  ThreadLocal<String> loca= new ThreadLocal<String>();


    public void setid(String id){
        loca.set(id);
    }

    public void getid(String id){
        loca.get();
    }

}
