package org.hxm.thread.多线程.java锁.Lock3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Aaron
 *
 * create at:  2022/6/2  10:22
 *
 * description:
 *
 *
 *公平锁和非公平锁区别：
 *      公平锁保证了排队的公平性，非公平锁霸气的忽视这个规则，
 * 所以就有可能导致排队的长时间在排队，也没有机会获取到锁，这就是传说中的 “锁饥饿”
 *
 *
 *
 * 为什么会设计公平锁和非公平锁
 * 1
 * 恢复挂起的线程到真正锁的获取还是有时间差的，
 * 从开发人员来看这个时间微乎其微，
 * 但是从CPU的角度来看，这个时间差存在的还是很明显的。
 * 所以非公平锁能更充分的利用CPU 的时间片，尽量减少 CPU 空闲状态时间。
 *  
 * 2
 * 使用多线程很重要的考量点是线程切换的开销，当采用非公平锁时，
 * 当1个线程请求锁获取同步状态，然后释放同步状态，
 * 因为不需要考虑是否还有前驱节点，所以刚释放锁的线程在此刻再次获取同步状态的概率就变得非常大，
 * 所以就减少了线程的开销。
 *
 *
 *公平锁和非公平如果选择
 *
 * 如果为了更高的吞吐量，很显然非公平锁是比较合适的，因为节省很多线程切换时间，吞吐量自然就上去了；
 * 否则那就用公平锁，大家公平使用。
 *
 *
 *
 *
 */
public class Lock公平锁和非公平锁 {

  //默认使用非公平锁 非公平锁会导致锁饥饿显现，可能导致其它线程不处理
  /**
   *
   *
   *      final boolean nonfairTryAcquire(int acquires) {
   *             final Thread current = Thread.currentThread();
   *             int c = getState();
   *             if (c == 0) {
   *                 if (compareAndSetState(0, acquires)) {
   *                     setExclusiveOwnerThread(current);
   *                     return true;
   *                 }
   *             }
   *             else if (current == getExclusiveOwnerThread()) {
   *                 int nextc = c + acquires;
   *                 if (nextc < 0) // overflow
   *                     throw new Error("Maximum lock count exceeded");
   *                 setState(nextc);
   *                 return true;
   *             }
   *             return false;
   *         }
   *
   *
   *
   *
   */
  private final static Lock lock = new ReentrantLock();

  /**
   * protected final boolean tryAcquire(int acquires) {
   *             final Thread current = Thread.currentThread();
   *             int c = getState();
   *             if (c == 0) {
   *                 if (!hasQueuedPredecessors() &&
   *                     compareAndSetState(0, acquires)) {
   *                     setExclusiveOwnerThread(current);
   *                     return true;
   *                 }
   *             }
   *             else if (current == getExclusiveOwnerThread()) {
   *                 int nextc = c + acquires;
   *                 if (nextc < 0)
   *                     throw new Error("Maximum lock count exceeded");
   *                 setState(nextc);
   *                 return true;
   *             }
   *             return false;
   *         }
   *
   *
   *          按序排队公平锁，就是判断同步队列是否还有先驱节点的存在(我前面还有人吗?)，多一个判断!hasQueuedPredecessors()
   *         如果没有先驱节点才能获取锁；先占先得非公平锁，是不管这个事的，只要能抢获到同步状态就可以
   */
  private final static Lock fairLock = new ReentrantLock(true);
  private static int number = 50;
  public static void main(String[] args) {
    t1();
  }

  /**
   * 非公平锁
   */
  public static void ticketLock() {
//    nonfairSale();
    fairSale();
  }


  /**
   * 非公平锁 就有可能导致排队的长时间在排队，也没有机会获取到锁，
   */
  public static void nonfairSale(){
    lock.lock();
    if (number > 0) {
      System.out.printf("%s 正在卖票,还剩余%s 张票 \n", Thread.currentThread().getName(), number--);
    }
    lock.unlock();
  }

  /**
   * 公平锁
   */
  public static void fairSale(){
    fairLock.lock();
    if (number > 0) {
      System.out.printf("%s 正在卖票,还剩余%s 张票 \n", Thread.currentThread().getName(), number--);
    }
    fairLock.unlock();
  }





  public static void t1() {

    new Thread(() -> {
      for (int i = 0; i < 55; i++) {
        ticketLock();
      }
    }, "a").start();
    new Thread(() -> {
      for (int i = 0; i < 55; i++) {
        ticketLock();
      }
    }, "b").start();
    new Thread(() -> {
      for (int i = 0; i < 55; i++) {
        ticketLock();
      }
    }, "c").start();
    new Thread(() -> {
      for (int i = 0; i < 55; i++) {
        ticketLock();
      }
    }, "d").start();

    new Thread(() -> {
      for (int i = 0; i < 55; i++) {
        ticketLock();
      }
    }, "e").start();

  }
}

