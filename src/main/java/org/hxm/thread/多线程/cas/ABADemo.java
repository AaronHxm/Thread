package org.hxm.thread.多线程.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  11:24
 *
 * description:
 * ABA问题
 * CAS算法实现一个重要前提需要取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差类会导致数据的变化。
 *  
 * 比如说一个线程one从内存位置V中取出A，这时候另一个线程two也从内存中取出A，并且线程two进行了一些操作将值变成了B，
 * 然后线程two又将V位置的数据变成A，这时候线程one进行CAS操作发现内存中仍然是A，然后线程one操作成功。
 *  
 *尽管线程one的CAS操作成功，但是不代表这个过程就是没有问题的。
 *
 * eg:
 *  1、比如银行流水吧 100-101 101-100 100-2022 实际上发生了三次流水
 *   但是 ABA可能导致 100-2022 这次被记录了
 *  2、挪用公款 去赌球
 *     赢了 把钱还回去
 *
 */
public class ABADemo {

static AtomicInteger atomicInteger = new AtomicInteger(100);

  static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

  public static void main(String[] args) {
    t2();
  }

  public static void t1() {
    new Thread(() -> {
      atomicInteger.compareAndSet(100,101 );
      atomicInteger.compareAndSet(101,100);
    },"t1").start();

    new Thread(() -> {
      boolean b = atomicInteger.compareAndSet(100, 2022);
      System.out.println(Thread.currentThread().getName()+"\t"+"修改成功否："+b+"\t"+atomicInteger.get());

    },"t1").start();
  }

  /**
   * 通过版本号来解决
   */
  public static void t2() {
    new Thread(() -> {
      int stamp = atomicStampedReference.getStamp();//拿到版本号
      System.out.println(Thread.currentThread().getName()+"\t"+"---默认版本号: "+stamp);
      //暂停1秒 模拟 当前线程 t2线程 同时拿到版本号
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //将100 改成101  版本号 1 改成2
      atomicStampedReference.compareAndSet(100,101,stamp,stamp+1);
      System.out.println(Thread.currentThread().getName()+"\t"+"---1次版本号: "+atomicStampedReference.getStamp());
      atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
      System.out.println(Thread.currentThread().getName()+"\t"+"---2次版本号: "+atomicStampedReference.getStamp());

    },"t1").start();

    new Thread(() -> {
      int stamp = atomicStampedReference.getStamp();//拿到版本号
      System.out.println(Thread.currentThread().getName()+"\t"+"---默认版本号: "+stamp);
      //暂停3秒 模拟 t1线程 内部实现aba
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      boolean b = atomicStampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
      System.out.println(Thread.currentThread().getName()+"\t"+"---操作成功否："+b+"\t"+atomicStampedReference.getStamp()+"\t"+atomicStampedReference.getReference());

    },"t2").start();
  }
}

