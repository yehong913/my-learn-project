
package com.didispace.chapter25.ThreadTest.ReetrantLockExample;

import com.didispace.chapter25.ThreadTest.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全的方式，原子性的
 * AtomicInteger
 */
@Slf4j
@ThreadSafe
public class LockExample1 {
	
	//请求总数
	public  static  int clientTotal=5000;
	//允许并发执行的多线程
	public static  int threadTotal=20;
	
	public static int count=0;
	//public static  AtomicInteger count= new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException {
	 	//线程池
		ExecutorService executorService= Executors.newCachedThreadPool();
		//信号量
		final Semaphore semaphore= new Semaphore(threadTotal);
		//计数器锁
		final CountDownLatch countDownLatch= new CountDownLatch(clientTotal);
		 
		for(int i=0;i<clientTotal;i++) {
			executorService.execute(
					()->{
						//获取线程信号
						try {
							semaphore.acquire();
							add();
							semaphore.release();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						}
						// 每执行一次，计算器就减1
						countDownLatch.countDown();
					});
		}
		countDownLatch.await();
		executorService.shutdown();
		log.info("count=="+count);
		
	}
	
	private  static  synchronized void add() {
		count++;
	}
	

}
