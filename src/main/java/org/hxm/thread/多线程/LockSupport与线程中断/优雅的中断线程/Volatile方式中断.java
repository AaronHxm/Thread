package org.hxm.thread.多线程.LockSupport与线程中断.优雅的中断线程;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : Aaron
 *
 * create at:  2022/6/4  16:25
 *
 * description:
 */
public class Volatile方式中断 {
  static volatile boolean flag = false;

  /**
   * 通过volatile的可见性 。t2线程三秒钟将flag 改成ture
   * t1线程当flag = true的时候中停止继续执行的任务
   * @param args
   */
  public static void main(String[] args) {

    new Thread(() -> {
      while (true) {
         if (flag) {
          System.out.println("我要中断线程了");
          break;
        }
        System.out.println("继续执行");
      }

    },"t1").start();

    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
        flag = true;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    },"t2").start();
  }
}

