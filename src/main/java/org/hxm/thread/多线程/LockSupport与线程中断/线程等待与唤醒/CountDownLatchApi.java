package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  15:05
 *
 * description:
 *    CountDownLatch主要有两个方法：
 * countDown()和await()。
 * countDown()方法用于使计数器减一，其一般是执行任务的线程调用，
 * await()方法则使调用该方法的线程处于等待状态，
 *

 */
public class CountDownLatchApi {
  static CountDownLatch countDownLatch = new CountDownLatch(6);

  public static void main(String[] args) {
    t1();
  }

  public static void t1(){
    for(int i =0;i<10;i++){
      new Thread(()->{
        System.out.println(Thread.currentThread().getName()+"执行完成");
        countDownLatch.countDown();
      },String.valueOf(i)).start();
    }
    try {

      countDownLatch.await();//  countDownLatch.getCount(); ==0  才继续往下走
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


    System.out.println("他们都执行完成了");
  }
}

