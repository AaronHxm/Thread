package org.hxm.thread.utils;

import java.util.Random;

/**
 * @author : Aaron
 *
 * create at:  2020/9/9  18:47
 *
 * description: 创建数组工具类
 */
public class MakeArray {
  public static final int ARRAY_SIZE = 40000;


  /**
   * 制造随机数组
   * @return
   */
  public static int[] makeRandomArray(){

    Random r = new Random();
    int[] result = new int[ARRAY_SIZE];
    for(int i =0 ;i<ARRAY_SIZE;i++){
      result[i] = r.nextInt(ARRAY_SIZE*3);
    }
    return result;
  }
  /**
   * 制造非随机数组
   * @return
   */
  public static int[] makeArray(){

     int[] result = new int[ARRAY_SIZE];
    for(int i =0 ;i<ARRAY_SIZE;i++){
      result[i] = i;
    }
    return result;
  }
}

