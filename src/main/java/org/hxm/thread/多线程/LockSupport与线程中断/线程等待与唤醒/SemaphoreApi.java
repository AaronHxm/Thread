package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  15:49
 *
 * description:
 *
 * 抢车位 六个车抢三个车位
 *
 */
public class SemaphoreApi {

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(3);
    for(int i =1;i<=6;i++){
      new Thread(()->{
        try {
          semaphore.acquire();
          System.out.println(Thread.currentThread().getName()+"\t"+"我抢到了车位");
          TimeUnit.SECONDS.sleep(3);
          System.out.println(Thread.currentThread().getName()+"\t"+"三秒后 我离开了");
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }finally {
          semaphore.release();
        }
        System.out.println();
      },String.valueOf(i)).start();
    }
  }
}

