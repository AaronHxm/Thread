在java中所有对象的最顶层父类Object，在jvm中有一个ObjectMonitor，
这也就是解释了为什么所有的对象都能加锁


内置锁(ObjectMonitor)
Monitor可以理解为一个同步工具或一种同步机制，通常被描述为一个对象。每一个Java对象就有一把看不见的锁，称为内部锁或者Monitor锁。

通常所说的对象的内置锁，是对象头Mark Word中的重量级锁指针指向的monitor对象，该对象是在HotSpot底层C++语言编写的(openjdk里面看)，简单看一下代码：


在JVM里，monitor就是实现lock的方式。
monitorenter就是获得某个对象的lock(owner是当前线程)
monitorexit就是释放某个对象的lock

ObjectMonitor::ObjectMonitor() {  
  _header       = NULL;  
  _count       = 0;     //用来记录获取锁的次数
  _waiters      = 0,  
  _recursions   = 0;       //线程的重入次数
  _object       = NULL;  
  _owner        = NULL;    //标识拥有该monitor的线程
  _WaitSet      = NULL;    //等待线程组成的双向循环链表，_WaitSet是第一个节点
  _WaitSetLock  = 0 ;  
  _Responsible  = NULL ;  
  _succ         = NULL ;  
  _cxq          = NULL ;    //多线程竞争锁进入时的单向链表
  FreeNext      = NULL ;  
  _EntryList    = NULL ;    //_owner从该双向循环链表中唤醒线程结点，_EntryList是第一个节点
  _SpinFreq     = 0 ;  
  _SpinClock    = 0 ;  
  OwnerIsThread = 0 ;  
} 
