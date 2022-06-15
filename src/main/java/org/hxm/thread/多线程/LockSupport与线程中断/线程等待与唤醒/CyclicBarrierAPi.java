package org.hxm.thread.多线程.LockSupport与线程中断.线程等待与唤醒;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author : Aaron
 *
 * create at:  2022/6/15  15:39
 *
 * description:
 *  这个与CountDownLatch 刚好相反
 *  例如：需要七个人开会，只有等七个人到了才能开会
 *
 */
public class CyclicBarrierAPi {

  public static void main(String[] args) {
    CyclicBarrier cy = new CyclicBarrier(7, () ->
      System.out.println("人到齐了 开始开会")
    );

    for (int i = 1; i<=7;i++){
      new Thread(() -> {
        System.out.println(Thread.currentThread().getName()+"人，到了");
        try {
          cy.await(); //先到的人 给我等着
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      },String.valueOf(i)).start();
    }
  }
}

