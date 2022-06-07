package org.hxm.thread.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  13:30
 *
 * description: 线程休眠工具类
 */
public class ThreadTools {


  public static final void second(int seconds){

    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public static final void ms(int ms){

    try {
      TimeUnit.MILLISECONDS.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

