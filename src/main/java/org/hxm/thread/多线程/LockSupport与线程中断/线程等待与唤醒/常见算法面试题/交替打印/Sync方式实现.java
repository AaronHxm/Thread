package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Aaron
 *
 * create at:  2022/6/6  14:34
 *
 * description:
 */
@SuppressWarnings("all")
public class Sync方式实现 {


  private static Object lockObj = new Object();


  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (lockObj){

        for (char c : shuzi.toCharArray()) {

          System.out.println(c);
          try {
            lockObj.notify();
            lockObj.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }finally {
            lockObj.notify();
          }


        }
      }

    }).start();

    new Thread(() -> {
      synchronized (lockObj) {

        for (char c : zimu.toCharArray()) {

          System.out.println(c);
          try {
            lockObj.notify();
            lockObj.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }
    }).start();
  }

  public static void mySleep() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * lock
   *
   * await() 释放当前线程的锁，加入到等待队列 尾插法
   *
   * signal()
   */





}

