package org.hxm.thread.多线程.Futask.Futask基础;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : Aaron
 *
 * create at:  2022/5/3  14:50
 *
 * description: futask 异步使用
 */
public class FutureTask的缺点 {

  /**
   * futuretask 的缺点 就是阻塞例如 get 放在 打印的前面 会发现 没有实现正在的异步
   * 主线程会等待futuretask 的结果继续执行
   * @param args
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<Integer> futureTask = new FutureTask<Integer>(() ->{
      Thread.sleep(5000);
      System.out.println(Thread.currentThread().getName()+"\t"+"------执行");
        return 1024;

    });

    new Thread(futureTask,"t1").start();
    System.out.println(futureTask.get());
    System.out.println("我是主线程，你继续干活");

  }
}

