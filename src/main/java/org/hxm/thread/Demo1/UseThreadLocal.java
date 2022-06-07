package org.hxm.thread.Demo1;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  14:30
 *
 * description: 使用ThreadLocal
 */
public class UseThreadLocal {

  //给threadlocal 初始化 并且赋值
  static ThreadLocal<Integer> age = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return 1;
    }
  };


  public static void threads(){
    Thread[] threads =  new Thread[3];
    for(int i=0;i<threads.length;i++){
    // 这个地方 给mythread 传入 的 是 0 1 2
      //所以 下面 叠加出来的效果 应该是 1 2 3
      threads[i] = new Thread(new MyThread(i));

    }

    for(int i=0;i<threads.length;i++){

      threads[i].start();

    }
  }

  public static class MyThread extends Thread{

    private int id;
    public MyThread(int id){
      this.id = id;
    }
    @Override
    public void run(){
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+"start====="+id);
        Integer i = age.get(); //获取变量
        id =id+i;
      age.set(id);//重新set
      System.out.println(threadName+"start====="+ age.get());

    }
  }

  public static void main(String[] args) {

    UseThreadLocal.threads();
  }
}

