package org.hxm.thread.多线程.Futask.ComplateFuture改进future;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * @author : Aaron
 *
 * create at:  2022/5/24  10:25
 *
 * description: 电商比价
 *
 *
 *
 * * 案例说明：电商比价需求 *
 * 1 同一款产品，同时搜索出同款产品在各大电商的售价; *
 * 2 同一款产品，同时搜索出本产品在某一个电商平台下，各个入驻门店的售价是多少 * *
 * 出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String> *
 * 《mysql》 in jd price is 88.05 *
 * 《mysql》 in pdd priceis 86.11 *
 * 《mysql》 in taobao price is 90.43 * *
 * 3 要求深刻理解 *
 *  3.1 函数式编程 *
 *  3.2 链式编程 *
 *  3.3 Stream流式计算
 */
public class ComplateFuture电商比价 {

  private static List<NetMall> list = Arrays.asList(
      new NetMall("pdd"),
      new NetMall("taobao"),
      new NetMall("jd"),
      new NetMall("suning"),

      new NetMall("jd"),
      new NetMall("suning")
  );

  /**
   * 传统for 循环去比价
   */
  @Test
  public void t1() {
    String productName = "mysql";
    Instant start = Instant.now();
    list.stream()
        .map(netMall -> String.format(productName + " in %s price is %.2f", netMall.getMallName(),netMall.getMallPrice(productName)))
        .collect(Collectors.toList())
        .forEach(System.out::println);

    Instant end = Instant.now();
    System.out.println("花费时间------>" + Duration.between(start, end).toMillis());
  }

  /**
   * 通过异步编排去比价
   */
  @Test
  public void t2() {
    String productName = "mysql";
    Instant start = Instant.now();
    list.stream()
        .map(netMall -> CompletableFuture.supplyAsync(() -> String
            .format(productName + " in %s price is %.2f", netMall.getMallName(),
                netMall.getMallPrice(productName))))
        .collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList())
        .forEach(System.out::println);

    Instant end = Instant.now();
    System.out.println("花费时间------>" + Duration.between(start, end).toMillis());
  }

  @Test
  public void t3() {
    String productName = "mysql";
    Instant start = Instant.now();
    list.stream()
        .map(netMall -> CompletableFuture.supplyAsync(() -> String
            .format(productName + " in %s price is %.2f", netMall.getMallName(),
                netMall.getMallPrice(productName))))
        .collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList())
        .forEach(System.out::println);

    Instant end = Instant.now();
    System.out.println("花费时间------>" + Duration.between(start, end).toMillis());
  }
}

