package com.didispace.chapter25.ThreadTest.ReetrantLockExample;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionExample {
    public static void main(String[] args) {
        ReentrantLock  reentrantLock= new ReentrantLock();
        Condition  condition= reentrantLock.newCondition();

        new  Thread(
                ()->{
                  try{
                      reentrantLock.lock();
                      log.info("wait  sign ~~~");
                      condition.await();
                  }catch (InterruptedException e){
                            e.printStackTrace();
                  }
                  log.info("get signal");
                  reentrantLock.unlock();
                }
        ).start();



        new  Thread(
                ()->{
                    reentrantLock.lock();
                    log.info("get lock");
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    condition.signalAll();
                    log.info("get signal");
                    reentrantLock.unlock();
                }
        ).start();
    }
}
