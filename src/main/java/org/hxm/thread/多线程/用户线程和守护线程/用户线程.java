package org.hxm.thread.多线程.用户线程和守护线程;

/**
 * @author : Aaron
 *
 * create at:  2022/5/14  14:07
 *
 * description:
 */
public class 用户线程 {

  /**
   *
   *
   *
   * 可以得到结论就是 main方法执行完成后 其它线程 还在继续执行 main方法和 t这个线程都是 用户线程
   * 用户线程不会因为其它线程停止而停止
   *
   *isDaemon 默认false ，即用户线程
   *
   *
   * @param args
   */
  public static void main(String[] args) {
    Thread t = new Thread(() ->{
      boolean daemon = Thread.currentThread().isDaemon();
      String str = daemon?"守护线程":"用户线程";
      System.out.println(str);
      while(true){
        //System.out.println("11111");
      }
    },"a");
    t.start();
    System.out.println("MAIN 方法结束");
  }
}

