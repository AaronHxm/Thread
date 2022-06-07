package org.hxm.thread.多线程.Futask.ComplateFuture改进future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author : Aaron
 *
 * create at:  2022/5/14  15:40
 *
 * description:
 */
public class ComplateFuture异步编程 {
  private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors
      .defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

  @Test
  public void t1() throws ExecutionException, InterruptedException{
    CompletableFuture.supplyAsync(() ->{
      try {

        boolean daemon = Thread.currentThread().isDaemon();
        String str = daemon?"守护线程":"用户线程";
        System.out.println(str);
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    }).thenApply( f ->{
      /**
       * 拿到上一步的结果 继续干活哦
       */
      return f+2;
    }).whenComplete((v,e) ->{
      if(null == e){
        System.out.println("计算结果"+v);

      }
    }).exceptionally(e ->{
        System.out.println(e.getCause()+"\t"+e.getMessage());
        return -1;
    });
    System.out.println("main -------- over");

    /**
     * 让主线程催眠三秒 不然无法看到结果
     *
     * 思考 1、为什么使用默认线程池的时候 需要主线程休眠  --------->默认线程池为守护线程，即用户线程停止后 守护线程停止
     *     2、使用自定义线程池是 需要shutDown --------》 自定义的为用户线程，不因其它用户线程停止而停止
     */
    TimeUnit.SECONDS.sleep(3);
  }



  @Test
  public void t2() throws ExecutionException, InterruptedException{
    CompletableFuture.supplyAsync(() ->{
      try {
        boolean daemon = Thread.currentThread().isDaemon();
        String str = daemon?"守护线程":"用户线程";
        System.out.println(str);
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    },threadPoolExecutor).thenApply( f ->{
      /**
       * 拿到上一步的结果 继续干活哦
       */
      return f+2;
    }).whenComplete((v,e) ->{
      if(null == e){
        System.out.println("计算结果"+v);

      }
    }).exceptionally(e ->{
      System.out.println(e.getCause()+"\t"+e.getMessage());
      return -1;
    });
    System.out.println("main -------- over");

    /**
     * 让主线程催眠三秒 不然无法看到结果
     *
     * 思考 1、为什么使用默认线程池的时候 需要主线程休眠  --------->默认线程池为守护线程，即用户线程停止后 守护线程停止
     *     2、使用自定义线程池是 需要shutDown --------》 自定义的为用户线程，不因其它用户线程停止而停止
     */
    TimeUnit.SECONDS.sleep(3);
//   threadPoolExecutor.shutdown();
  }

  /**
   * ComplateFuture get
   * 会发现get 依旧阻塞 并且会抛出异常
   */
  @Test
  public void t3() throws ExecutionException, InterruptedException {

    CompletableFuture completableFuture = CompletableFuture.supplyAsync(() ->{
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    },threadPoolExecutor).thenApply( f ->{
      /**
       * 拿到上一步的结果 继续干活哦
       */
      return f+2;
    }).whenComplete((v,e) ->{
      if(null == e){
        System.out.println("计算结果"+v);

      }
    }).exceptionally(e ->{
      System.out.println(e.getCause()+"\t"+e.getMessage());
      return -1;
    });

    System.out.println(completableFuture.get());

    System.out.println("main is over---");
  }
  /**
   * ComplateFuture join
   * join 依旧阻塞 但不会抛出异常
   */
  @Test
  public void t4()  {

    CompletableFuture completableFuture = CompletableFuture.supplyAsync(() ->{
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    },threadPoolExecutor).thenApply( f ->{
      /**
       * 拿到上一步的结果 继续干活哦
       */
      return f+2;
    }).whenComplete((v,e) ->{
      if(null == e){
        System.out.println("计算结果"+v);

      }
    }).exceptionally(e ->{
      System.out.println(e.getCause()+"\t"+e.getMessage());
      return -1;
    });

    System.out.println(completableFuture.join());

    System.out.println("main is over---");
  }

}

