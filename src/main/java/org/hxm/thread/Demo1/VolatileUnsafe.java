package org.hxm.thread.Demo1;

import org.hxm.thread.utils.ThreadTools;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  14:17
 *
 * description: 演示Volatile非原子操作，因此 volatile 适合一个线程写，多个线程读的场景
 */
public class VolatileUnsafe {

  public static class VolatileVar implements Runnable {

    private volatile int age = 0;

    @Override
    public void run() {
      String threadName = Thread.currentThread().getName();
      age = age + 1;
      System.out.println(threadName + ":==============" + age);
      ThreadTools.ms(100);
      age = age + 1;
      System.out.println(threadName + ":==============" + age);
    }
  }

  public static void main(String[] args) {

    VolatileVar volatileVar = new VolatileVar();

    Thread t1 = new Thread(volatileVar);
    Thread t2 = new Thread(volatileVar);

    Thread t3 = new Thread(volatileVar);

    Thread t4 = new Thread(volatileVar);

    t1.start();
    t2.start();

    t3.start();

    t4.start();


  }


}

