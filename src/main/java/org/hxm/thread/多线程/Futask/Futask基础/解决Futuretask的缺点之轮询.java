package org.hxm.thread.多线程.Futask.Futask基础;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;
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
public class 解决Futuretask的缺点之轮询 {

  /**
   * Futask的缺点 就是阻塞例如 get 放在 打印的前面 会发现 没有实现正在的异步 主线程会等待futask 的结果继续执行 解决方案 1、get(2,
   * TimeUnit.SECONDS) 过时不侯，get的时候增加过期时间 2、轮询解决阻塞
   */
  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
      Thread.sleep(5000);
      System.out.println(Thread.currentThread().getName() + "\t" + "------执行");
      return 1024;

    });

    new Thread(futureTask, "t1").start();
    /**
     *
     *  轮询的方式会浪费cpu资源，而且也不见得马上获取到结果
     *  如果异步计算的长江，通常会使用轮询的方式获取结果，尽量不要阻塞
     *
     *  轮询也是阻塞，却别在于轮询可以自己控制，比如
     *   Thread.sleep(1000); 一秒钟获取一次
     */
    boolean flag = true;
    while (flag) {

      if (futureTask.isDone()) {
        System.out.println("result--->" + futureTask.get());
        Integer integer = futureTask.get();
        flag = false;

      } else {
        Thread.sleep(1000);
        System.out.println("还未计算 继续等待");
      }
    }
    System.out.println(111111);

  }
}

