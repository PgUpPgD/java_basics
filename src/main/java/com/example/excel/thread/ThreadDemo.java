package com.example.excel.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "e")
public class ThreadDemo{

    class Thread1 extends Thread{
        public void run() {
            reRun();
        }
    }

    class Thread2 implements Runnable{
        public void run() {
            reRun();
        }
    }

    private void reRun(){
        int ticket =30;
        //默认实现非公平锁，true 则为公平锁
        ReentrantLock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        while(true){
            if(ticket > 0){
                System.out.println(Thread.currentThread().getName() + "--" + ticket--);
            }else{
                break;
            }
        }
        lock.lock();
        log.info(Thread.currentThread().getName() + "执行完");
    }

    public static void main(String[] args) throws Exception {
        ThreadDemo threadDemo = new ThreadDemo();
        Thread1 thread01 = threadDemo.new Thread1();
        thread01.start();
//        thread01.join();
//        TimeUnit.SECONDS.sleep(1);  //睡眠主线程
//        Thread1 thread02 = threadDemo.new Thread1();
//        thread02.start();
//        thread02.join();
        Thread thread1 = new Thread(threadDemo.new Thread2());
        thread1.start();
//        thread1.join();
//        Thread thread2 = new Thread(threadDemo.new Thread2());
//        thread2.start();
//        thread2.join(); //等待当前线程执行完毕
    }


}
