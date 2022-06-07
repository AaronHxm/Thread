package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

/**
 * @author : Aaron
 *
 * create at:  2022/6/7  20:21
 *
 * description: 自旋方式
 */
public class Volatile方式实现 {

  static volatile boolean flag = false;
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";

  public static void main(String[] args) {

    new Thread(() -> {

      for (char c : shuzi.toCharArray()) {
        while (flag) {

        }
        System.out.println(c);
        flag = false;
      }
    }).start();

    new Thread(() -> {
      for (char c : zimu.toCharArray()) {
        while (!flag) {
        }
        System.out.println(c);
        flag = true;
      }
    }).start();
  }
}

