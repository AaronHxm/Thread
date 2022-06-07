package org.hxm.thread.多线程.java锁.八种锁解读对象锁类锁1;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author : Aaron
 *
 * create at:  2022/5/25  10:23
 *
 * description:
 *
 *
 *  * 线程   操作  资源类  8锁案例说明
 *
 * 邮件休眠三秒 短信不休眠
 *
 *  * 1    标准访问有ab两个线程，请问先打印邮件还是短信
 *  * 2    sendEmail方法暂停3秒钟，请问先打印邮件还是短信
 *  * 3    新增一个普通的hello方法，请问先打印邮件还是hello
 *  * 4    有两部手机，请问先打印邮件还是短信
 *  * 5    两个静态同步方法，同1部手机，请问先打印邮件还是短信
 *  * 6    两个静态同步方法， 2部手机，请问先打印邮件还是短信
 *  * 7    1个静态同步方法，1个普通同步方法,同1部手机，请问先打印邮件还是短信
 *  * 8    1个静态同步方法，1个普通同步方法,2部手机，请问先打印邮件还是短信
 *  *
 *
 */
public class 八种案例 {

  public static void main(String[] args) {
    /**
     * *  1-2
     *  *  *  一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
     *  *  *  其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一的一个线程去访问这些synchronized方法
     *  *  *  锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
     *
     *
     *
     *   *  3-4
     *  *  *  加个普通方法后发现和同步锁无关,hello
     *  *  *  换成两个对象后，不是同一把锁了，情况立刻变化。
     *  *
     *  *   *
     *  *  *  5-6 都换成静态同步方法后，情况又变化
     *  *  *  三种 synchronized 锁的内容有一些差别:
     *  *  * 对于普通同步方法，锁的是当前实例对象，通常指this,具体的一部部手机,所有的普通同步方法用的都是同一把锁——实例对象本身，
     *  *  * 对于静态同步方法，锁的是当前类的Class对象，如Phone.class唯一的一个模板
     *  *  * 对于同步方法块，锁的是 synchronized 括号内的对象
     *  *
     *  *   *  7-8
     *  *  *    当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁。
     *  *  *  *
     *  *  *  *  所有的普通同步方法用的都是同一把锁——实例对象本身，就是new出来的具体实例对象本身,本类this
     *  *  *  *  也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
     *  *  *  *
     *  *  *  *  所有的静态同步方法用的也是同一把锁——类对象本身，就是我们说过的唯一模板Class
     *  *  *  *  具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的
     *  *  *  *  但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
     *
     *
     *
     *
     *  静态同步方法属于类锁，不管new多少个对象 类锁只有一个 static sync的方法 不管new 多少个对象 都是同一把锁---类锁
     *  同步方法锁的是对象 同一个方法 不同对象（Phone phone = new Phone(); Phone phone1 = new Phone();）    phone.sendSms(); phone1.sendSms(); 互不干涉
     *
     *
     * 类锁范围大
     * 对象锁
     *
     */
//    t1();
//    t2();

//    t3();
//    t4();

//    t5();
//    t6();

    t7();
    t8();
  }

  public static void t1() {
    Phone phone = new Phone();
    new Thread(() -> {
      phone.sendEmail();
    }).start();

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone.sendSms();
    }).start();

  }

  public static void t2() {
    Phone phone = new Phone();
    new Thread(() -> {
      phone.sendEmail();
    }).start();

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone.sendSms();
    }).start();
  }

  public static void t3() {
    Phone phone = new Phone();
    new Thread(() -> {
      phone.sendEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    phone.Hello();
  }

  public static void t4() {
    Phone phone = new Phone();
    Phone phone1 = new Phone();
    new Thread(() -> {
      phone.sendEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone1.sendSms();
    }).start();
  }
  //两个静态同步方法，同1部手机，请问先打印邮件还是短信
  public static void t5() {
    Phone phone = new Phone();
    new Thread(() -> {
      phone.sendStaticEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone.sendStaticSms();
    }).start();
  }
  @Test
  public static void t6() {
    Phone phone = new Phone();
    Phone phone2 = new Phone();

    new Thread(() -> {
      phone.sendStaticEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone2.sendStaticSms();
    }).start();
  }
  @Test
  public static void t7() {
    Phone phone = new Phone();
    new Thread(() -> {
      phone.sendStaticEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone.sendSms();
    }).start();
  }
  @Test
  public static void t8() {
    Phone phone = new Phone();
    Phone phone2 = new Phone();

    new Thread(() -> {
      phone.sendStaticEmail();
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      phone2.sendSms();
    }).start();
  }
}

