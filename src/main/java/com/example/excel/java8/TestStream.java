package com.example.excel.java8;


import com.example.excel.entity.Student;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream的三个操作步骤：
 * 1.创建Stream
 * 2.中间操作: 多个中间操作连接起来形成一个流水线，除非触发终止操作，否则中间操作不会执行任何处理！
 *             而在终止操作时一次性全部处理，称为"惰性求值"
 * 3.终止操作（终端操作）
 */
public class TestStream {

    //创建Stream
    @Test
    public void test1(){
        //1.可以通过Collection系列集合提供的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2.通过Arrays中静态方法stream()获取数组流
        Student[] students = new Student[10];
        Stream<Student> stream2 = Arrays.stream(students);

        //3.通过stream中的静态方法Stream.of()
        Stream<Student> stream3 = Stream.of(students);

        //4.创建无限流
        //迭代
        Stream<Integer> iterate = Stream.iterate(0, (x) -> x + 2);
        iterate.limit(5).forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(() -> Math.random());
        stream4.forEach(
                (t) -> {
                    Double format = Double.parseDouble(new DecimalFormat("0.00").format(t));
                    System.out.println(format);
                }
        );

    }


}
