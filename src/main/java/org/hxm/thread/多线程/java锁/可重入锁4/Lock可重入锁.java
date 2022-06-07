package org.hxm.thread.多线程.java锁.可重入锁4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Aaron
 *
 * create at:  2022/6/2  16:33
 *
 * description:
 */
public class Lock可重入锁 {


  final static Lock lock = new ReentrantLock();

  public static void m1() {
    lock.lock();
    System.out.println("m1");
    m2();
    lock.unlock();
  }
  public static void m2() {
    lock.lock();
    System.out.println("m2");
    m3();
    lock.unlock();
  }
  public static void m3() {
    lock.lock();
    System.out.println("m3");
    lock.unlock();
  }

  public static void main(String[] args) {
    m1();
  }
}

