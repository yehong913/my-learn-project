
package com.didispace.chapter25.ThreadTest.ReetrantLockExample;

import com.didispace.chapter25.ThreadTest.annoations.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的方式，原子性的
 * AtomicInteger
 */
@Slf4j

public class LockExample3 {

	private final Map<String, Data> map= new TreeMap<String,Data>();

	private  final ReentrantReadWriteLock  reentrantReadWriteLock= new ReentrantReadWriteLock();

	private  final Lock writerLock= reentrantReadWriteLock.writeLock();

	private  final Lock readLock=reentrantReadWriteLock.readLock();

	public   Data get(String key){
		readLock.lock();
		try{
			return map.get(key);
		}finally {
			readLock.unlock();
		}

	}

	public Set<String> getAllKey(){
		readLock.lock();
		try{
			return map.keySet();
		}finally {
			readLock.unlock();
		}


	}

	public  Data put(String key,Data value){
		writerLock.lock();
		try{
			return map.put(key,value);
		}finally {
			readLock.unlock();
		}

	}



	class Data{

	}
}
