package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.locks.LockSupport;

/**
 * @author : Aaron
 *
 * create at:  2022/6/7  20:10
 *
 * description:
 */
public class LockSupport方式实现 {
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";

  /**\
   *
   */
  static Thread t1;
  static Thread t2;
  public static void byLockSupport() {
    t1 = new Thread(() -> {
      for (char c : zimu.toCharArray()) {
        MyUnpark.say(c,t2);
      }
    });
    t2 = new Thread(() -> {
      for (char c : shuzi.toCharArray()) {
        MyUnpark.say(c,t1);
      }
    });
    t1.start();
    t2.start();
  }


  public static class  MyUnpark {
    public static void say(Object str,Thread t){
      System.out.println(str);
      LockSupport.unpark(t);
      LockSupport.park();
    }

  }
}

