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
public class 初步使用Futuretask {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<Integer> futureTask = new FutureTask<Integer>(() ->{
      System.out.println(Thread.currentThread().getName()+"\t"+"------执行");
        return 1024;

    });

    new Thread(futureTask,"t1").start();
    System.out.println("我是主线程，你继续干活");
    System.out.println(futureTask.get());
  }
}

