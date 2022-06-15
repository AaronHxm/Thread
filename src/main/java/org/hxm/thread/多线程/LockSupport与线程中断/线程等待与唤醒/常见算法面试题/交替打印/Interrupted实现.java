package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

/**
 * @author : Aaron
 *
 * create at:  2022/6/8  14:46
 *
 * description:
 */
public class Interrupted实现 {
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";
  static Thread t1;
  static Thread t2;
  public static void main(String[] args) {
    t1 = new Thread(() ->{

      for (char c : zimu.toCharArray()) {
        while(Thread.interrupted()){
        }
        System.out.println(c);

        t1.interrupt();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    t2 = new Thread(() ->{

      for (char c : shuzi.toCharArray()) {
        while(Thread.interrupted()){
        }
        System.out.println(c);

        t2.interrupt();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    t1.start();
    t2.start();

  }
}

