package org.hxm.thread.Demo1;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  12:16
 *
 * description: 异常
 */
public class HasInterruptedException {

  public static class UseThread extends Thread {

    @Override
    public void run() {
      String threadName = Thread.currentThread().getName();
      while (!isInterrupted()) {

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          System.out.println(String.format("发生了异常，当前的标志位位:[%S]", isInterrupted()));
          interrupt();
          e.printStackTrace();
        }
        System.out.println(String.format("当前线程的名称是，【%S】", threadName));
      }
      System.out.println(
          String.format("线程【%S】的中断状态%S", threadName, Thread.currentThread().isInterrupted()));

    }
  }

  public static void main(String[] args) throws InterruptedException {
    UseThread useThread = new UseThread();
    useThread.start();
    Thread.sleep(500);
    useThread.interrupt();
  }

}

