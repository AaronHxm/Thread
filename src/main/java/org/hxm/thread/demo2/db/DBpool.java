package org.hxm.thread.demo2.db;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author : Aaron
 *
 * create at:  2020/9/7  16:31
 *
 * description: 数据库连接池
 */
public class DBpool {

  private static LinkedList<Connection> pool = new LinkedList<>();

  public DBpool(int size) {
    for (int i = 0; i < size; i++) {

      pool.addLast(SqlConnetcImpl.fetchConnect());
    }

  }

  public Connection fetchConn(int mills) throws InterruptedException {

    synchronized (pool) {
      if (mills < 0) {
        while (pool.isEmpty()) {
          pool.wait();

        }
        return pool.removeFirst();
      } else {
        long overtime = System.currentTimeMillis() + mills; //到期时间
        long remian = mills; //剩余时间
        while (pool.isEmpty() && remian > 0) {

          pool.wait(remian);
          remian = overtime - System.currentTimeMillis(); //剩余时间= 到期时间-当前时间
        }
        //  超时了 尝试去检查pool中还存不存在 存在就返回 不存在就 返回null
        if (!pool.isEmpty()) {

          return pool.getFirst();

        }
        return null;
      }
    }
  }


  public void releaseConn(Connection conn){
    if(conn !=null){

      synchronized (pool){
        pool.addLast(conn  );
        pool.notifyAll(); //通知正在等待获取连接池的线程 获取连接
      }
    }
  }

}

