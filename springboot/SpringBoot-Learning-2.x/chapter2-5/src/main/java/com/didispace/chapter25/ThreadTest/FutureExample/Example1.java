package com.didispace.chapter25.ThreadTest.FutureExample;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Example1 {
    static  class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            log.info(" do  something   in callable");
            Thread.sleep(5000);
            return "done";
        }
    }
    public static void main(String[] args) throws Exception {

        ExecutorService  executorService= Executors.newCachedThreadPool();
        Future<String> future=executorService.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        log.info(future.get());


    }
}
