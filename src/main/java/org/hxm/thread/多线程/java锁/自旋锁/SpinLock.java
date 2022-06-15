package org.hxm.thread.多线程.java锁.自旋锁;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  10:46
 *
 * description:
 */
public class SpinLock {


  AtomicReference<Thread> atomicReference = new AtomicReference<>();

  public void myLock(){

    System.out.println(Thread.currentThread().getName()+"\t"+"进来了");
    //期望是null  替换成我自己
    // atomicReference.compareAndSet(null, Thread.currentThread())
    //如果替换成功了 就表示我持有了
    //如果失败 false 那我继续旋转
     while(!atomicReference.compareAndSet(null, Thread.currentThread())){
       //System.out.println(Thread.currentThread().getName()+"\t"+"我在旋转");
     };
    System.out.println(Thread.currentThread().getName()+"\t"+"我出去了");
  }

  public void myUnlock(){
    atomicReference.compareAndSet(Thread.currentThread(),null );
    System.out.println(Thread.currentThread().getName()+"\t"+"我释放了锁");
  }

  /**
   * 1、t1 t2 线程同时调用myLock
   * 2、t1 先将 atomicReference 里面的值设置称为 自己
   *      t2 compareAndSet 一直失败 所以 t2 自选
   *  3、t1线程 五秒后执行了 myUnlock() 也就是将atomicReference 里面的值设置为null
   *      最后 t2 再次 将atomicReference 里面的值设置为null
   *
   *
   *
   * @param args
   */
  public static void main(String[] args) {
    SpinLock spinLock = new SpinLock();
    new Thread(()->{
      spinLock.myLock();
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      spinLock.myUnlock();
    },"t1").start();
    new Thread(()->{
      spinLock.myLock();
      spinLock.myUnlock();
    },"t2").start();
  }
}

