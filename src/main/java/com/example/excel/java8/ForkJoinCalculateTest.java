package com.example.excel.java8;

import lombok.Data;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

//并行流
@Data
public class ForkJoinCalculateTest{

    @Test  //ForkJoin 框架
    public void test1(){
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0l, 10000000000l); //百亿  4119毫秒
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时为：" + Duration.between(start, end).toMillis());
    }

    @Test
    public void test2(){
        Instant start = Instant.now();
        long sum = 0l;
        for (int i = 0; i <= 10000000000l; i++){  //百亿   卡死没反应
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时为：" + Duration.between(start, end).toMillis());
    }

    //java8并行流
    @Test
    public void test3(){
        Instant start = Instant.now();
        LongStream.rangeClosed(0, 10000000000l)   //10亿   2342毫秒
                .parallel()  //并行流  底层ForkJoin框架   .sequential()顺序流
                .reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("耗时为：" + Duration.between(start, end).toMillis());
    }

}
