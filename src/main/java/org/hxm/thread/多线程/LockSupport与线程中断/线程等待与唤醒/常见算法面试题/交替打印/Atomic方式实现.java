package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒.常见算法面试题.交替打印;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : Aaron
 *
 * create at:  2022/6/7  20:34
 *
 * description:
 */
public class Atomic方式实现 {
  static AtomicBoolean   flag = new AtomicBoolean();
  private static String shuzi = "1234567";
  private static String zimu = "ABCDEFG";

  public static void main(String[] args) {

    new Thread(() -> {

      for (char c : shuzi.toCharArray()) {
        while (flag.get()) {

        }
        System.out.println(c);
        flag.set(false);
      }
    }).start();

    new Thread(() -> {
      for (char c : zimu.toCharArray()) {
        while (!flag.get()) {
        }
        System.out.println(c);
        flag.set(true);
      }
    }).start();
  }
}

