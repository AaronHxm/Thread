package org.hxm.thread.多线程.java锁.synchronized源码讲解2;

/**
 * @author : Aaron
 *
 * create at:  2022/6/1  10:08
 *
 * description:
 */
public class Sync代码块 {

  /**
   *    11: monitorenter
   *       12: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
   *       15: iconst_1
   *       16: invokevirtual #4                  // Method java/io/PrintStream.println:(I)V
   *       19: aload_2
   *       20: monitorexit
   *       21: goto          29
   *       24: astore_3
   *       25: aload_2
   *       26: monitorexit
   *
   *       思考 1、 一个monitorenter 对应 两个 monitorexit？
   *       下monitorenter指令和monitorexit指令，可以把执行 monitorenter 理解为加锁，执行 monitorexit 理解为释放锁
   *       。可以看到一个monitorenter对应两个monitorexit指令，这是因为monitorenter 指令被插入到同步代码块的开始位置，
   *       而 monitorexit 需要插入到方法正常结束处和异常处两个地方，这样就可以保证抛异常的情况下也能释放锁

   *
   * @param args
   */
  public static void main(String[] args) {
    Object obj = new Object();
    synchronized (obj){
      System.out.println(1);
    }
  }

  /**
   *    11: monitorenter
   *       12: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
   *       15: iconst_1
   *       16: invokevirtual #4                  // Method java/io/PrintStream.println:(I)V
   *       19: new           #5                  // class java/lang/Exception
   *       22: dup
   *       23: ldc           #6                  // String 500
   *       25: invokespecial #7                  // Method java/lang/Exception."<init>":(Ljava/lang/String;)V
   *       28: athrow
   *       29: astore_3
   *       30: aload_2
   *       31: monitorexit
   *       32: aload_3
   *       33: athrow
   *
   *
   *       在抛出异常的时候 monitorenter对应一个monitorexit 因为athrow是异常直接包裹了  异常会释放锁
   * @throws Exception
   */
  public void t1() throws Exception {
    Object obj = new Object();
    synchronized (obj){
      System.out.println(1);
      throw new Exception("500");
    }
  }
}

