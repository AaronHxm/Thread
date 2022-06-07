package org.hxm.thread.demo3;

import java.util.concurrent.RecursiveTask;
import org.hxm.thread.utils.MakeArray;

/**
 * @author : Aaron
 *
 * create at:  2020/9/9  18:50
 *
 * description: 数组求和
 */
public class SumArray extends RecursiveTask {

  private static final int MID = MakeArray.ARRAY_SIZE/10; //任务的阙值

  private int[] src;//统计的数组
  private int start;
  private int end;
  public SumArray(int[] src,int start,int end){

    this.src = src;
    this.start = start;
    this.end = end;
  }


  @Override
  protected Object compute() {
    if(end - start < MID){
      //小于阙值的时候 直接计算
      int count = 0 ;
      for(int i =start;i<end;i++){
        count = count+src[i];
      }
      return count;
    }else{
      // 如果大于阙值 需要将 这个继续分成两段
      // start。。。。。。mid 。。。。。。end
      int min = (start+end)/2;
   //   SumTask sumTask

    }

    return null;
  }
}

