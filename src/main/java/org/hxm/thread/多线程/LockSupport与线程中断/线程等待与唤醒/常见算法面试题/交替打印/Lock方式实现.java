package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Aaron
 *
 * create at:  2022/6/7  20:06
 *
 * description:
 */
public class Lock方式实现 {
  private static Lock lock = new ReentrantLock(true);
  static Condition t1condition = lock.newCondition();
  static Condition t2condition = lock.newCondition();
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";
  private static void lock() {
    //t1 打印a之后
    Thread t1 = new Thread(() -> {
      lock.lock();
      try {
        for (char c : zimu.toCharArray()) {
          System.out.println(c);
          t1condition.await();

          t2condition.signal();
        }
        //    t1condition.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "t1");
    Thread t2 = new Thread(() -> {
      lock.lock();
      try {

        for (char c : shuzi.toCharArray()) {
          System.out.println(c);
          t1condition.signal();
          t2condition.await();


        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "t2");
    t1.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t2.start();
  }

  /**
   * public final void await() throws InterruptedException { if (Thread.interrupted()) throw new
   * InterruptedException(); Node node = addConditionWaiter(); int savedState = fullyRelease(node);
   * int interruptMode = 0; while (!isOnSyncQueue(node)) { LockSupport.park(this); if
   * ((interruptMode = checkInterruptWhileWaiting(node)) != 0) break; } if (acquireQueued(node,
   * savedState) && interruptMode != THROW_IE) interruptMode = REINTERRUPT; if (node.nextWaiter !=
   * null) // clean up if cancelled unlinkCancelledWaiters(); if (interruptMode != 0)
   * reportInterruptAfterWait(interruptMode); }
   *
   *
   *
   * private Node addConditionWaiter() { Node t = lastWaiter; // If lastWaiter is cancelled, clean
   * out. if (t != null && t.waitStatus != Node.CONDITION) { unlinkCancelledWaiters(); t =
   * lastWaiter; } Node node = new Node(Thread.currentThread(), Node.CONDITION); if (t == null)
   * firstWaiter = node; else t.nextWaiter = node; lastWaiter = node; return node; }
   */

  /***
   *
   *
   *
   * 下面这个案例 演示 的是 错误lock.unlock
   *
   * for循环里面执行lock.unlock  第二次
   *
   *
   *         public final void signal() {
   *             if (!isHeldExclusively())
   *                 throw new IllegalMonitorStateException();
   *             Node first = firstWaiter;
   *             if (first != null)
   *                 doSignal(first);
   *         }
   *
   *
   *           *
   *      * @return {@code true} if synchronization is held exclusively;
   *      *         {@code false} otherwise
   *      * @throws UnsupportedOperationException if conditions are not supported
   *
   *

             protected boolean isHeldExclusively() {
              *throw new UnsupportedOperationException();
   *}
   *
   *
   * 从源码中可以看出 第一次for之后 unlock  导致 t2Condition
signal的时候 没有持有锁 所以抛出了 IllegalMonitorStateException

   */
  public static void exceptionMethod() {
    Thread t1 = new Thread(() -> {

      lock.lock();

      for (char a : shuzi.toCharArray()) {
        try {
          System.out.println(a);
          t2condition.signal();
          t1condition.await();


        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    }, "t1");
    t1.start();
    Thread t2 = new Thread(() -> {
      lock.lock();
      for (char a : zimu.toCharArray()) {
        try {
          System.out.println(a);
          t1condition.signal();
          t2condition.await();


        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          // mySleep();
          lock.unlock();
        }
      }
    }, "t2");
    t2.start();
  }
}

