package org.hxm.thread.Demo1;

/**
 * @author : Aaron
 *
 * create at:  2020/9/4  17:22
 *
 * description: 安全的停止线程
 */
public class T3 {


  //  interrupt() 中断线程，并不是强行关闭。 中断标记位 改成 true
//  isInterrupted() //判断是否被中断
//  static interrupted() //判断是否被中断，并清除当前中断状态  中断标记位 改成 false
  public static class EndThread extends Thread {
    public EndThread(String name) {
      super(name);
    }
    @Override
    public void run() {
      String threadName = Thread.currentThread().getName();

//        while (true){ //如果此处 不对标记位进行处理 则不会中断
//          System.out.println(String.format("线程【%S】，正在运行",threadName));
//
//        }
      while (!interrupted()) { //如果此处 不对标记位进行处理 则不会中断
        System.out.println(String.format("线程【%S】，正在运行", threadName));
      }
      System.out.println(String.format("线程【%S】的中断状态%S", threadName, isInterrupted()));
    }

  }


  /**
   * interrupted() 等方法是在Thread中存在，Runnable中不存在
   * 下面演示如何在runnable中使用
   */


  public static class EndAble implements Runnable{

    @Override
    public void run() {
      String threadName = Thread.currentThread().getName();

      while (!Thread.currentThread().isInterrupted()) { //如果此处 不对标记位进行处理 则不会中断
        System.out.println(String.format("线程【%S】，正在运行", threadName));
      }
      System.out.println(String.format("线程【%S】的中断状态%S", threadName, Thread.currentThread().isInterrupted()));
    }
  }



  public static void main(String[] args) {
    Thread endThread = new EndThread("endThread");
    endThread.start();

    try {
      Thread.sleep(2000);
      endThread.interrupt();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}

