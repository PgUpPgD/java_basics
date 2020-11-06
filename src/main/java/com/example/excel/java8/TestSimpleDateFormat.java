package com.example.excel.java8;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestSimpleDateFormat {
    /*
        java8
        java.time.chrono;
        java.time.format;
        java.time.zone;
     */

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        //有多线程安全问题  （没测出来）
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
//                return format.parse("20201106");    //未加锁
                return convert("20201106");  //加锁
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<Date>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            result.add(pool.submit(task));
        }
        for (Future<Date> future : result){
            System.out.println(future.get());
        }
        pool.shutdown();
    }

    //加线程锁  解决线程安全问题
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue(){
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    public static Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }

    //1.8
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20201106", dtf);
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            result.add(pool.submit(task));
        }
        for (Future<LocalDate> future : result){
            System.out.println(future.get());
        }
        pool.shutdown();
    }


}
