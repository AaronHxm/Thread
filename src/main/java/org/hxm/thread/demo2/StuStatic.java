package org.hxm.thread.demo2;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  21:57
 *
 * description: 意外收获学习static
 */
public class StuStatic {

  private static int i= 0;


  public void call() { // 定义成员方法
    System.out.println("调用call()方法");
    for (i = 0; i < 3; i++) {
      System.out.println("此处执行i++===="+i++);
    }
  }

  public static void main(String[] args) {

    StuStatic  stuStatic = new StuStatic();
    StuStatic  stuStatic1 = new StuStatic();
    System.out.println(stuStatic.i);
    stuStatic.call();

    System.out.println(stuStatic1.i);
  }

}

