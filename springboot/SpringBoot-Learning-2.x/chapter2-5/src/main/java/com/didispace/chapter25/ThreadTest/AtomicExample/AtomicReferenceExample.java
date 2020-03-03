
package com.didispace.chapter25.ThreadTest.AtomicExample;

import com.didispace.chapter25.ThreadTest.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;


/**
 * 原子性的Long操作  AtomicLong
 *  AtomicReference 都是是使用CAS算法，预期值和输入值是一致的时候才进行执行；不一致将不执行
 */
@Slf4j
@ThreadSafe
public class AtomicReferenceExample {

	public  static AtomicReference<Integer> count= new AtomicReference<>(0);

    public static void main(String[] args) {
       count.compareAndSet(0,2);
       count.compareAndSet(1,7);
       count.compareAndSet(2,10);
       log.info("count=="+count.get());
    }

}
