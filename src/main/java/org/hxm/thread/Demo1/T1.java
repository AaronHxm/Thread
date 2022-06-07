package org.hxm.thread.Demo1;

import com.sun.jmx.mbeanserver.MXBeanMappingFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import org.hxm.thread.Demo1.T2.UseRunnable;

/**
 * @author : Aaron
 * create at:  2019-10-10  14:07
 * @description: thread 第一章
 */
public class T1 {



  public static void main(String[] args) {
    /**
     * 获取当前执行的所有线程
     */
    ThreadMXBean threadMXBeanm = ManagementFactory.getThreadMXBean();
    ThreadInfo[] threadInfos = threadMXBeanm.dumpAllThreads(false, false);
    for(ThreadInfo threadInfo : threadInfos){
      System.out.println(String.format("线程名字 【%S】 ,线程id【%S】",threadInfo.getThreadName(),threadInfo.getThreadId()));
    }
    /*
    线程名字 【MONITOR CTRL-BREAK】 ,线程id【5】
    线程名字 【SIGNAL DISPATCHER】 ,线程id【4】
    线程名字 【FINALIZER】 ,线程id【3】
    线程名字 【REFERENCE HANDLER】 ,线程id【2】
    线程名字 【MAIN】 ,线程id【1】

 Attach Listener
Attach Listener线程是负责接收到外部的命令，而对该命令进行执行的并且吧结果返回给发送者。通常我们会用一些命令去要求jvm给我们一些反 馈信息，如：java -version、jmap、jstack等等。如果该线程在jvm启动的时候没有初始化，那么，则会在用户第一次执行jvm命令时，得到启动。

Signal Dispatcher
前面我们提到第一个Attach Listener线程的职责是接收外部jvm命令，当命令接收成功后，会交给signal dispather线程去进行分发到各个不同的模块处理命令，并且返回处理结果。signal dispather线程也是在第一次接收外部jvm命令时，进行初始化工作。

Finalizer
这个线程也是在main线程之后创建的，其优先级为10，主要用于在垃圾收集前，调用对象的finalize()方法；关于Finalizer线程的几点：

1. 只有当开始一轮垃圾收集时，才会开始调用finalize()方法；因此并不是所有对象的finalize()方法都会被执行；
　　2. 该线程也是daemon线程，因此如果虚拟机中没有其他非daemon线程，不管该线程有没有执行完finalize()方法，JVM也会退出；
　　3. JVM在垃圾收集时会将失去引用的对象包装成Finalizer对象（Reference的实现），并放入ReferenceQueue，由Finalizer线程来处理；最后将该Finalizer对象的引用置为null，由垃圾收集器来回收；
　　4. JVM为什么要单独用一个线程来执行finalize()方法呢？如果JVM的垃圾收集线程自己来做，很有可能由于在finalize()方法中误操作导致GC线程停止或不可控，这对GC线程来说是一种灾难；

Reference Handler
VM在创建main线程后就创建Reference Handler线程，其优先级最高，为10，它主要用于处理引用对象本身（软引用、弱引用、虚引用）的垃圾回收问题。
     */
  }
}

