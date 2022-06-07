package org.hxm.thread.多线程.Futask.Futask基础;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author : Aaron
 *
 * create at:  2022/5/3  14:50
 *
 * description: futask 异步使用
 */
public class 解决Futuretask的缺点之过时不侯 {

  /**
   * Futask的缺点 就是阻塞例如 get 放在 打印的前面 会发现 没有实现正在的异步
   * 主线程会等待futask 的结果继续执行
   * 解决方案
   * 1、get(2, TimeUnit.SECONDS) 过时不侯，get的时候增加过期时间
   * 2、
   * @param args
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    FutureTask<Integer> futureTask = new FutureTask<Integer>(() ->{
      Thread.sleep(5000);
      System.out.println(Thread.currentThread().getName()+"\t"+"------执行");
        return 1024;

    });

    new Thread(futureTask,"t1").start();
    /**
     * 我就等你两秒 两秒之后 我不需要了
     *
     */
    System.out.println(futureTask.get(2, TimeUnit.SECONDS));
    System.out.println("我是主线程，你继续干活");

  }
}

