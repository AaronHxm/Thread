package org.hxm.thread.Demo1;

import org.hxm.thread.utils.ThreadTools;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  13:17
 *
 * description: 对象锁和类锁
 */
public class SynClzAndInst {

  //类锁
  private static class SynClass extends Thread {

    @Override
    public void run() {
      System.out.println("TestClss is running");
      synClass();
    }
  }

  //对象锁
  private static class InstanceSyn implements Runnable {

    private SynClzAndInst synClzAndInst;

    public InstanceSyn(SynClzAndInst synClzAndInst) {
      this.synClzAndInst = synClzAndInst;
    }

    @Override
    public void run() {
      System.out.println("test InstanceSyn is running " + synClzAndInst);
      synClzAndInst.instance();
    }
  }

  //对象锁
  private static class Instance2Syn implements Runnable {

    private SynClzAndInst synClzAndInst;

    public Instance2Syn(SynClzAndInst synClzAndInst) {
      this.synClzAndInst = synClzAndInst;
    }

    @Override
    public void run() {
      System.out.println("test InstanceSyn2 is running " + synClzAndInst);
      synClzAndInst.instance2();
    }
  }

  private synchronized void instance() {

    ThreadTools.second(3);

    System.out.println("syncinstance is 开始..." + this.toString());
    ThreadTools.second(3);
    System.out.println("syncinstance is 结束..." + this.toString());

  }

  private synchronized void instance2() {

    ThreadTools.second(3);

    System.out.println("syncinstance2 is 开始..." + this.toString());
    ThreadTools.second(3);
    System.out.println("syncinstance2 is 结束..." + this.toString());

  }

  private static synchronized void synClass() {

    ThreadTools.second(3);

    System.out.println("syncinstance2 is 开始...");
    ThreadTools.second(3);
    System.out.println("syncinstance2 is 结束...");
  }


  public static void main(String[] args) {

    SynClzAndInst synClzAndInst = new SynClzAndInst();
    Thread t1 = new Thread(new InstanceSyn(synClzAndInst));

    SynClzAndInst synClzAndInst2 = new SynClzAndInst();
    Thread t2 = new Thread(new Instance2Syn(synClzAndInst2));

    t1.start();
    t2.start();
    /**
     *
     *
     *
     * 对象
     */
  }
}

