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
 *          实例方法 ClearInterrupted = false,不清楚中断状态
 *
 *
 */
public class Interrupted方式 {


  /**
   * jvm源码
   *
   *void os::interrupt(Thread* thread) {
   *   assert(!thread->is_Java_thread() || Thread::current() == thread || Threads_lock->owned_by_self(),
   *          "possibility of dangling Thread pointer");
   *
   *   OSThread* osthread = thread->osthread();
   *   osthread->set_interrupted(true);
   *   // More than one thread can get here with the same value of osthread,
   *   // resulting in multiple notifications.  We do, however, want the store
   *   // to interrupted() to be visible to other threads before we post
   *   // the interrupt event.
   *   OrderAccess::release();
   *   SetEvent(osthread->interrupt_event());
   *   // For JSR166:  unpark after setting status
   *   if (thread->is_Java_thread())
   *     ((JavaThread*)thread)->parker()->unpark();
   *
   *   ParkEvent * ev = thread->_ParkEvent ;
   *   if (ev != NULL) ev->unpark() ;
   *
   * }
   *
   *
   * 大概可以看到 最下面调用了 unpark()
   * 此处猜想 此处的unpark 也就是Locksupport 调用的unpark 线程继续执行
   *
   * 也刚好印证了 wait  会出现中断异常的原因：明明要等待 然后 interrupt 让我继续执行，所以 抛出中断异常，是否继续执行具体看用户
   *
   *
   *
   * @param args
   */
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

