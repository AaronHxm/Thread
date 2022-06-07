package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author : Aaron
 *
 * create at:  2022/6/6  14:07
 *
 * description:
 *  1、此处信号量 类似于通行证，最大是1 最小是0
 *   LockSupport.park();  设置信号量为0
 *   LockSupport.unpark(t1); 设置信号量为1，不允许累加
 *  2、与sync和lock 区别
 *     a、无需在同步代码块中使用
 *     b、没有先后顺序
 *     c、多次  LockSupport.unpark(t1) 例如t3 线程t2，多次提供许可证，
 *     最终许可证 1，此时 在t1 第一次 park()时，已经用掉了许可证，所以
 *     第二次park() 会出现没有许可证 一直阻塞
 *     类似于 停车场，我给你这个车牌通行证 不管多少次 给的都是这个车牌 LockSupport.unpark(t1)
 *     LockSupport.park(); 但你第二个车需要出去的时候就不行
 *     park unpark 需要一组一组的实现
 *
 *
 *
 *
 */
public class LockSupportApi {


  public static void main(String[] args) {
t3();
  }



  public static void t1() {
    Thread t1 = new Thread(() -> {
      System.out.println("t1 线程 come in");
      LockSupport.park();
      System.out.println("t1线程被唤醒");
    }, "t1");
    t1.start();
    new Thread(() -> {

      System.out.println("t2 线程你去叫醒 t1");

      LockSupport.unpark(t1);
    }, "t2").start();
  }

  public static void t2() {
    Thread t1 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("t1 线程 come in");
      LockSupport.park();
      System.out.println("t1线程被唤醒");
    }, "t1");
    t1.start();

    new Thread(() -> {

      System.out.println("t2 线程你去叫醒 t1");

      LockSupport.unpark(t1);
    }, "t2").start();
  }

  public static void t3() {
    Thread t1 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("t1 线程 come in");
      LockSupport.park();
      LockSupport.park();
      System.out.println("t1线程被唤醒");
    }, "t1");
    t1.start();

    new Thread(() -> {

      System.out.println("t2 线程你去叫醒 t1");

      LockSupport.unpark(t1);
      LockSupport.unpark(t1);

    }, "t2").start();
  }
}

