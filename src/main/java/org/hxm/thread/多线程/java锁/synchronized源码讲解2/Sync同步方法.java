package org.hxm.thread.多线程.java锁.synchronized源码讲解2;

/**
 * @author : Aaron
 *
 * create at:  2022/6/1  10:28
 *
 * description: javap  -v xxx.class
 *
 *
 * 可以通过会变代码 看到方法上面有一个 flags
 *
 * 唯一区别是静态同步方法 多了一个 ACC_STATIC
 * 在jvm中 ACC_STATIC标示
 * JVM就是通过这个标签来识别方法属性，当JVM执行引擎执行某一个方法时，
 * 其会从方法区中获取该方法的access_flags，检查其是否有ACC_SYNCRHONIZED标识符，
 * 若是有该标识符，则说明当前方法是同步方法，需要先获取当前对象的monitor，再来执行方法,
 *
 *
 *
 * 实例方法获取的对象就是this，如果是静态方法获取的对象就是class。
 */
public class Sync同步方法 {

  /**
   *   flags: ACC_PUBLIC, ACC_SYNCHRONIZED
   *     Code:
   *       stack=2, locals=1, args_size=1
   *          0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
   *          3: ldc           #3                  // String 同步方法
   *          5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
   *          8: return
   */
  public synchronized void t1(){
    System.out.println("同步方法");
  }

  /**
   *  descriptor: ()V
   *     flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
   *     Code:
   *       stack=2, locals=0, args_size=0
   *          0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
   *          3: ldc           #5                  // String 静态同步方法
   *          5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
   *          8: return
   */
  public static synchronized void t2(){
    System.out.println("静态同步方法");
  }



}

