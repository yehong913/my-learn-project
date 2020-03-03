package com.didispace.chapter25.ThreadTest.SynchContainerExample;

import java.util.Vector;

public class VectorExample2 {

    private  static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            vector.add(1);
        }

    Thread  thread1= new Thread(){
        @Override
        public void run() {
            for (int i=0;i<10;i++){
                vector.remove(i);
            }
        }
    };

        Thread  thread2= new Thread(){
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    vector.get(i);
                }
            }
        };

        thread1.start();;
        thread2.start();

    }




}
