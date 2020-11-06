package com.example.excel.java8;

import lombok.Data;

import java.util.concurrent.RecursiveTask;

//并行流
@Data
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private static final Long THRESHOLD = 10000L;
    private Long start;
    private Long end;

    public ForkJoinCalculate() {}
    public ForkJoinCalculate(Long start, Long end) {
        this.start = start;    //0
        this.end = end;        //100000
    }

    @Override
    protected Long compute() {
        long length = end - start; //100000
        if (length <= THRESHOLD){
            long sum = 0;
            for (long i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }else {
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle); //0到5万
            left.fork();  //拆分子任务，同时压入线程队列
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);//5万到10万
            right.fork();
            return left.join() + right.join();
        }
    }
}
