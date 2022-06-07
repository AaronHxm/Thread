package org.hxm.thread.demo2;

import org.hxm.thread.utils.ThreadTools;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  22:04
 *
 * description: 学习join
 */
public class Usejoin {

  public static class JumpQueue implements Runnable {


    private Thread thread; //插队的线程

    public JumpQueue(Thread thread) {
      this.thread = thread;
    }

    @Override
    public void run() {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " is terminted.");
    }
  }


  public static void main(String[] args) {
    Thread previous = Thread.currentThread();//当前是主线程
    for (int i = 0; i < 10; i++) {

      //i =0 previous是主线程， i=1是，previous是i=0这个线程
      Thread t = new Thread(new JumpQueue(previous), "ThreadName" + String.valueOf(i));
      t.start();
      System.out.println(String.format("线程【%S】插队插到了 [%S]的线程前面", previous.getName(), t.getName()));
      previous = t;
    }

    ThreadTools.second(2);
    System.out.println(String.format("当前线程终止 【%s】", Thread.currentThread().getName()));

  }

}

