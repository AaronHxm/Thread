package org.hxm.thread.多线程.java锁.八种锁解读对象锁类锁1;

import java.util.concurrent.TimeUnit;

/**
 * @author : Aaron
 *
 * create at:  2022/5/25  10:17
 *
 * description:
 */
public class Phone {

  public synchronized void sendEmail(){

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("同步代码块，发送邮件");
  }

  public synchronized void sendSms(){

    System.out.println("同步代码块，sendSms");
  }

  public static synchronized void sendStaticEmail(){
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("静态，发送邮件");
  }

  public static synchronized void sendStaticSms(){
    System.out.println("静态，发送sms");
  }


  public void Hello(){
    System.out.println("普通方法说hello");
  }
}

