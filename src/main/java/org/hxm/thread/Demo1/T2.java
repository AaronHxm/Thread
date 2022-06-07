package org.hxm.thread.Demo1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : Aaron
 *
 * create at:  2020/9/4  16:25
 *
 * description: 创建线程的方法
 */
@SuppressWarnings("all")
public class T2 {

  /**
   * 线程的启动方式有四种 1、继承Thread类 2、实现runnable 接口，无返回值，无异常 3、实现callable接口，有返回值，有异常
   * 4、线程池（此种方式，网上很多不算创建方式，但是个人觉得可以创建线程，所以我归进去类）
   */
  /**
   * 线程的停止方法
   *1、 使用退出标志，使线程正常退出，也就是当 run() 方法完成后线程中止。
   *2、使用 stop()，resume(),suspend() 方法强行终止线程，但是不推荐使用这个方法，该方法已被弃用。此方法不释放资源
   *3、interrupt() 中断线程，并不是强行关闭。 中断标记位 改成 true
   * isInterrupted() //判断是否被中断
   * static interrupted() //判断是否被中断，并清除当前中断状态  中断标记位 改成 false
   *
   */

  // java 线程是协作式，并不是抢占式
  public static void main(String[] args) {

    UseRunnable useRunnable = new UseRunnable();
    new Thread(useRunnable).start();

    new Thread(useRunnable).isInterrupted();
    new Thread(useRunnable).interrupt();
    /**
     *因为 Tread 源码中，不直接支持callable的方式，因此直接传入callable 是报错
     *new Thread(useCall).start();
     *以下为源码
     * public Thread(Runnable target) {
     *   init(null, target, "Thread-" + nextThreadNum(), 0);
     *}
     */
    UseCall useCall = new UseCall();
    FutureTask<String> futureTask = new FutureTask<>(useCall);
    new Thread(futureTask).start();
    String s = null;//就是callable 的返回
    try {
      s = futureTask.get(); //get方法是阻塞的
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    System.out.println(s);
  }

  /**
   * 1、实现Runnable
   */

  public static class UseRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println("i am runbable impl");
    }
  }

  /**
   * 2\ callable接口，
   */

  public static class UseCall implements Callable<String> {

    @Override
    public String call() throws Exception {
      return "Callable";
    }
  }

  /**
   * 3、继承Thread
   */

  public static class MyThread extends Thread{

    @Override
    public void run(){

      System.out.println("继承Thread");
    }

  }

}

