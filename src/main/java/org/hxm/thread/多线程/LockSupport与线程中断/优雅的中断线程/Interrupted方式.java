package org.hxm.thread.多线程.LockSupport与线程中断.优雅的中断线程;

import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2022/6/4  16:39
 *
 * description:
 *
 *
 *
 * 1、 public void interrupt()实例方法，
 *      实例方法interrupt()仅仅是设置线程的中断状态为true，不会停止线程
 * 2、public static boolean interrupted()静态方法，Thread.interrupted();  
 *          判断线程是否被中断，并清除当前中断状态这个方法做了两件事：
 *            1 返回当前线程的中断状态
 *            2 将当前线程的中断状态设为false 这个方法有点不好理解，因为连续调用两次的结果可能不一样。
 *  3、public boolean isInterrupted()实例方法，判断当前线程是否被中断（通过检查中断标志位）
 *
 *
 *
 *  static boolean interrupted()， boolean interrupted() 这两个方法都会调用native的
 *    private native boolean isInterrupted(boolean ClearInterrupted);
 *
 *    区别是 静态方法 ClearInterrupted = true,清除中断状态
 *          静态方法 ClearInterrupted = false,不清楚中断状态
 *
 *
 */
public class Interrupted方式 {
  public static void main(String[] args) {

    Thread t1= new Thread(() -> {
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("我要中断线程了");
          break;
        }
        System.out.println("继续执行");
      }

    },"t1");
    t1.start();

    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt(); //将t1线程的中断标志位 改为true
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    },"t2").start();
  }
}

