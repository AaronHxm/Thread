package org.hxm.thread.多线程.LockSupport与线程中断.优雅的中断线程;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author : Aaron
 *
 * create at:  2022/6/4  20:20
 *
 * description:  理解三个api
 */
public class InterruptedApi理解 {

  public static void main(String[] args) {
    t4() ;
  }

  /**
   * 当对一个线程，调用 interrupt() ， 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。 被设置中断标志的线程将继续正常运行，不受影响。所以， interrupt()
   * 并不能真正的中断线程，需要被调用的线程自己进行配合才行。
   */
  public static void t1() {

    Thread t1 = new Thread(() -> {

      for (int i = 1; i < 300; i++) {
        System.out.println(String
            .format("第 %s次，当前线程interrupted 状态为 %s", i, Thread.currentThread().isInterrupted()));
      }

      System.out.println("t1.interrupt()调用之后02： " + Thread.currentThread().isInterrupted());
    }, "t1");
    t1.start();
    System.out.println("t1.interrupt()调用之前,t1线程的中断标识默认值： " + t1.isInterrupted());
    try {
      TimeUnit.MILLISECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //实例方法interrupt()仅仅是设置线程的中断状态位设置为true，不会停止线程
    t1.interrupt();
    //活动状态,t1线程还在执行中
    System.out.println("t1.interrupt()调用之后01： " + t1.isInterrupted());

    try {
      TimeUnit.MILLISECONDS.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //非活动状态,t1线程不在执行中，已经结束执行了。
    System.out.println("t1.interrupt()调用之后03： " + t1.isInterrupted());

  }

  public static void t2() {

    Thread t1 = new Thread(() -> {

      for (int i = 1; i < 300; i++) {
        System.out.println(String
            .format("第 %s次，当前线程interrupted 状态为 %s", i, Thread.currentThread().isInterrupted()));

      }

      System.out.println("线程结束");


    }, "t1");
    t1.start();
    try {
      TimeUnit.MILLISECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t1.interrupt();
  }

  /**
   *
   *
   *
   * 线程 InterruptedException 会清除 线程的中断状态，
   * 所以 t1线程中正在 sleep、wait，其它线程对此线程进行中断操作的时候，会失效，
   * 需要ti线程在抛出异常时候 重新 修改标志位
   *
   * 可以从InterruptedException源码看到描述
   */
  public static void t3() {
    Thread t1 = new Thread(() -> {
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("-----isInterrupted() = true，程序结束。");
          break;
        }
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          /**
          * <pre >
               *if (Thread.interrupted())  // Clears interrupted status!
               *throw new InterruptedException();
               * </pre >
               *
               *@author Frank Yellin
               *@see java.lang.Object#wait()
                            * @see java.lang.Object#wait( long)
               *@see java.lang.Object#wait( long,int)
               *@see java.lang.Thread#sleep( long)
               *@see java.lang.Thread#interrupt()
              * @see java.lang.Thread#interrupted()
              * @since JDK1 .0
              */
          Thread.currentThread().interrupt();//???????  //线程的中断标志位为false,无法停下，需要再次掉interrupt()设置true
          e.printStackTrace();
        }
        System.out.println("------hello Interrupt");
      }
    }, "t1");
    t1.start();

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    new Thread(() -> {
      t1.interrupt();//修改t1线程的中断标志位为true
    }, "t2").start();
  }

  public static void t4() {
    //获取当前线程的中断状态，并清除当前中断状态

    System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
    System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
    System.out.println("111111");
    Thread.currentThread().interrupt();///----false---> true
    System.out.println("222222");
//获取当前线程的中断状态，并清除当前中断状态  因为 Thread.currentThread().interrupt() 这个将中断状态设置了true
    //所以 下面 第一行 获取到是true 但紧接着清除中断状态 改成了false
    //也就是第二行获取到false 的原因
    System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
    System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
  }
}

