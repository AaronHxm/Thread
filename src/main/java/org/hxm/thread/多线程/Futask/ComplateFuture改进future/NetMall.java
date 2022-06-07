package org.hxm.thread.多线程.Futask.ComplateFuture改进future;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Aaron
 *
 * create at:  2022/5/24  10:26
 *
 * description:
 */

@Data
@AllArgsConstructor
public class NetMall {

  private String mallName;

  /**
   * 模拟获取最低价
   *
   * 实际情况应该是 爬虫爬当前商城 拿到最低价
   *
   * 此处 直接 线程sleep 模拟爬虫过程
   *
   * @return
   */
  public double getMallPrice(String productName) {

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return ThreadLocalRandom.current().nextDouble()+productName.charAt(0);
  }

}

