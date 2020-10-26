package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class NumTest{
        volatile int number =0;

        public void addNumber(){
           this.number=60;
        }
        }
        @Slf4j
public class test {

    final ThreadPoolExecutor pool=new ThreadPoolExecutor(2,3,
            60,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5),
            Executors.defaultThreadFactory());


    List<User> userList=new ArrayList<User>();
    public void testOM(){


        for (int i=0;i<10000;i++){
            User user =new User("user"+String.valueOf(i),"13590269355"+String.valueOf(i));
            userList.add(user);
        }
    }


//    public static void main(String args[]){
//        NumTest numTest=new NumTest();
//        new Thread(()->{
//
//            System.out.println(Thread.currentThread().getName()+"----Thread  number="+numTest.number);
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//            numTest.addNumber();
//        },"AAA").start();
//        while (numTest.number==0){
//
//        }
//        System.out.println(Thread.currentThread().getName()+"----Thread  number="+numTest.number);
//    }
    public static void main(String args[]){
        test omt=new test();
        for(int i=0;i<2;i++){
            omt.testOM();
        }
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
