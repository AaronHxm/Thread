package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒;

import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2022/6/6  09:56
 *
 * description: syncapi理解
 *
 * 1、wait notify 必须在 synchronized中使用（t1 t2方法）
 * 2、必须先 wait 才能 notify，否则会一直 waitting
 */
public class SyncApi {

public static Object syncLock = new Object();

  public static void main(String[] args) {
    t3();
  }

  public static void t1(){
    new Thread(() ->{
      synchronized (syncLock){
        System.out.println("t1 线程 come in");
        try {
          syncLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("t1线程被唤醒");
      }

    },"t1").start();

    new Thread(() ->{
      synchronized (syncLock){
        System.out.println("t2 线程你去叫醒 t1");
        syncLock.notify();
      }
    },"t2").start();
  }

  public static void t2(){
    new Thread(() ->{
//      synchronized (syncLock){
        System.out.println("t1 线程 come in");
        try {
          syncLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("t1线程被唤醒");
//      }

    },"t1").start();

    new Thread(() ->{
//      synchronized (syncLock){
        System.out.println("t2 线程你去叫醒 t1");
        syncLock.notify();
//      }
    },"t2").start();
  }

  /**
   *   java.lang.Thread.State: WAITING (on object monitor)
   *         at java.lang.Object.wait(Native Method)
   *         - waiting on <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
   *         at java.lang.Object.wait(Object.java:502)
   *         at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
   *         - locked <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
   *         at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
   */
  public static void t3(){
    new Thread(() ->{
      try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
      synchronized (syncLock){
        System.out.println("t1 线程 come in");
        try {
           syncLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("t1线程被唤醒");
      }

    },"t1").start();

    new Thread(() ->{
      synchronized (syncLock){
        System.out.println("t2 线程你去叫醒 t1");
        syncLock.notify();
      }
    },"t2").start();
  }
}

