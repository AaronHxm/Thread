package org.hxm.thread.多线程.java锁.死锁5;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author : Aaron
 *
 * create at:  2022/6/3  11:54
 *
 * description:
 */
public class Sync死锁 {

  public static void main(String[] args) {
    t1();
  }

  final static Object lockA = new Object();
  final static Object lockB = new Object();

  @Test
  public static void t1(){


    new Thread(()->{
      synchronized(lockA){
        System.out.println(Thread.currentThread().getName()+"-----"+"我持有a，希望获得到b锁");
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized(lockB){
          System.out.println("我获取到了b锁");
        }
      }


    },"a").start();

    new Thread(()->{
      synchronized(lockB){
        System.out.println(Thread.currentThread().getName()+"-----"+"我持有b，希望获得到a锁");
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized(lockA){
          System.out.println("我获取到了a锁");
        }
      }


    },"b").start();
  }

}

