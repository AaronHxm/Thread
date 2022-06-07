package org.hxm.thread.多线程.LockSupport与线程中断.优雅的中断线程;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : Aaron
 *
 * create at:  2022/6/4  16:38
 *
 * description:
 */
public class Atomic方式中断 {
  static AtomicBoolean b = new AtomicBoolean(false);
  public static void main(String[] args) {

    new Thread(() -> {
      while (true) {
        if (b.get()) {
          System.out.println("我要中断线程了");
          break;
        }
        System.out.println("继续执行");
      }

    },"t1").start();

    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
       b.set(true);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    },"t2").start();
  }
}

