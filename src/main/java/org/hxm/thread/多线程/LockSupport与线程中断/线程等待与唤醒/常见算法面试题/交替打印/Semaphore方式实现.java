package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  15:52
 *
 * description:
 */
public class Semaphore方式实现 {
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";
  static Semaphore semaphore = new Semaphore(1);
  static Semaphore semaphore1 = new Semaphore(0);

  public static void main(String[] args) {
   Thread t1 = new Thread(() -> {


        try {
          for (char c : shuzi.toCharArray()) {
            semaphore.acquire();
            System.out.println(c);


            semaphore1.release();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

    }) ;

    Thread t2 =  new Thread(() -> {

        try {
          for (char c : zimu.toCharArray()) {
            semaphore1.acquire();
            System.out.println(c);
            semaphore.release();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    });
    t1.start();
    t2.start();
  }
}

