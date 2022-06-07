package org.hxm.thread.多线程.Futask.ComplateFuture改进future;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author : Aaron
 *
 * create at:  2022/5/14  14:48
 *
 * description:
 */
public class CompalteFuture使用 {
  private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors
      .defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

  /**
   * 无返回值 使用默认线程池
   */
  @Test
  public void t1() throws ExecutionException, InterruptedException {
    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
      String name = Thread.currentThread().getName();
      System.out.println(name);
    });
    System.out.println(completableFuture.get());

  }
  /**
   * 无返回值 使用自定义线程池
   */
  @Test
  public void t2() throws ExecutionException, InterruptedException {
    CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
      String name = Thread.currentThread().getName();
      System.out.println(name);
    },threadPoolExecutor);
    System.out.println(completableFuture1.get());
    System.out.println("-----------");
    threadPoolExecutor.shutdown();
  }
  /**
   * 、有返回值 使用默认线程池
   */
  @Test
  public void t3() throws ExecutionException, InterruptedException {
    CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
      String name = Thread.currentThread().getName();
      System.out.println(name);
      return 1;
    });
    System.out.println(completableFuture2.get());
    System.out.println("-----------");
  }
  /**
   * 有返回值 使用自定义程池
   */
  @Test
  public void t4() throws ExecutionException, InterruptedException {
    CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(() -> {
      String name = Thread.currentThread().getName();
      System.out.println(name);
      return 1;
    },threadPoolExecutor);

    System.out.println(completableFuture3.get());
    System.out.println("-----------");
    threadPoolExecutor.shutdown();
  }
  @Test
  public void t5() throws ExecutionException, InterruptedException {

    List<Integer> collect = IntStream.generate(() -> {
      return (int)( Math.random() * 10000);
    }).limit(10000).boxed().collect(Collectors.toList());
    collect.forEach(System.out::println);
  }
/**
 * 我们可以把任务分为计算密集型和IO密集型。
 *
 * CPU密集型
 *
 * 一般来说：计算型代码、Bitmap转换、Gson转换等
 *
 * 计算密集型任务的特点是要进行大量的计算，消耗CPU资源，比如计算圆周率、对视频进行高清解码等等，全靠CPU的运算能力。
 *
 * 这种计算密集型任务虽然也可以用多任务完成，但是，任务越多，花在任务切换的时间就越多，CPU执行任务的效率就越低，
 *
 * 所以，要最高效地利用CPU，计算密集型任务同时进行的数量应当等于CPU的核心数。
 *
 * 计算密集型任务由于主要消耗CPU资源，因此，代码运行效率至关重要。
 *
 * Python这样的脚本语言运行效率很低，完全不适合计算密集型任务。对于计算密集型任务，最好用C语言编写。
 *
 * IO密集型
 *
 * 一般来说：文件读写、DB读写、网络请求等
 *
 * 第二种任务的类型是IO密集型，涉及到网络、磁盘IO的任务都是IO密集型任务，这类任务的特点是CPU消耗很少，
 *
 * 任务的大部分时间都在等待IO操作完成（因为IO的速度远远低于CPU和内存的速度）。
 *
 * 对于IO密集型任务，任务越多，CPU效率越高，但也有一个限度。常见的大部分任务都是IO密集型任务，比如Web应用。
 *
 * IO密集型任务执行期间，99%的时间都花在IO上，花在CPU上的时间很少，因此，用运行速度极快的C语言替换用Python这样运行速度极低的脚本语言，完全无法提升运行效率。
 *
 * 对于IO密集型任务，最合适的语言就是开发效率最高（代码量最少）的语言，脚本语言是首选，C语言最差。
 *
 * 总之，计算密集型程序适合C语言多线程，I/O密集型适合脚本语言开发的多线程。
 * ————————————————
 * 版权声明：本文为CSDN博主「星夜孤帆」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_38826019/article/details/121295970
 */
}

