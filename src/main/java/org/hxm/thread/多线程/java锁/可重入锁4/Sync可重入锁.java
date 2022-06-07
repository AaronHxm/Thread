package org.hxm.thread.多线程.java锁.可重入锁4;

/**
 * @author : Aaron
 *
 * create at:  2022/6/2  16:28
 *
 * description:
 *
 * 可重入锁又名递归锁
 *  
 * 是指在同一个线程在外层方法获取锁的时候，
 * 再进入该线程的内层方法会自动获取锁(前提，锁对象得是同一个对象)，
 * 不会因为之前已经获取过还没释放而阻塞。
 *  
 * 如果是1个有 synchronized 修饰的递归调用方法，程序第2次进入被自己阻塞了岂不是天大的笑话，出现了作茧自缚。为了避免自己坑自己
 * 所以Java中ReentrantLock和synchronized都是可重入锁，可重入锁的一个优点是可一定程度避免死锁。
 *
 *
 *
 */
public class Sync可重入锁 {
final static Object objectLock = new Object();
  public static void main(String[] args) {
    synchronized (objectLock){
      System.out.println("外层");
      synchronized (objectLock){
        System.out.println("中层");
        synchronized (objectLock){
          System.out.println("内层层");
        }
      }
    }
  }
}

