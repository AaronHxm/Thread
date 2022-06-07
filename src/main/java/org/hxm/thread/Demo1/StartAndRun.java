package org.hxm.thread.Demo1;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  12:48
 *
 * description: 理解Start和run
 */
public class StartAndRun {


  public static class MyThread extends Thread{

    @Override
    public void run(){
      int i =100;
      while (i>0){
        System.out.println(String.format("线程【%S】，正在运行,当前的i=【%S】", Thread. currentThread().getName(),i--));
      }
    }
  }

  public static void main(String[] args) {
    MyThread myThread =  new MyThread();
    myThread.setName("xxxxxxxx");
    myThread.run();
  }
}

