package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  11:43
 *
 * description://todo 未完成
 */
public class 自旋锁实现 {


  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";

  static AtomicReference atomicReference = new AtomicReference();
  public static void myLock(){
    while( !atomicReference.compareAndSet(null,Thread.currentThread())){

    }
  }
  public static void unLock(){
    atomicReference.compareAndSet(Thread.currentThread(),null);


  }
  public static void main(String[] args) {
    new Thread(() -> {

      for (char c : shuzi.toCharArray()) {
        while( !atomicReference.compareAndSet(null,Thread.currentThread())){

        }
        System.out.println(c);
        atomicReference.compareAndSet(Thread.currentThread(),null);

      }
    }).start();

    new Thread(() -> {
      for (char c : zimu.toCharArray()) {
        while( !atomicReference.compareAndSet(null,Thread.currentThread())){

        }
        System.out.println(c);
        atomicReference.compareAndSet(Thread.currentThread(),null);

      }
    }).start();
  }
}

