package com.example.excel.java8;


import com.example.excel.entity.SpecialityEnum;
import com.example.excel.entity.Student;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一、Stream的三个操作步骤：
 * 1.创建Stream
 * 2.中间操作
 * 3.终止操作（终端操作）
 */
public class TestStreamApi2 {
    List<Student> students = Arrays.asList(
            new Student("路飞", 22, 175, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.SING)),
            new Student("红发", 40, 180, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.DANCE)),
            new Student("白胡子", 50, 185, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.DANCE)),
            new Student("艾斯", 25, 183, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.SWIMMING)),
            new Student("雷利", 48, 176, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.SWIMMING)),
            new Student("雷利", 48, 175, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.DANCE)),
            new Student("雷利", 48, 176, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.RUNNING))
    );
    //终止操作（终端操作）

    /*
        收集
        collect - 将流转化为其他形式，接收一个Collector接口的实现，用于给Stream中元素汇总的方法
     */
    @Test       //拼接字符串
    public void test10(){
        String collect = students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(",", "*", "/"));
        System.out.println(collect);
    }

    @Test       //相关运算
    public void test9(){
        DoubleSummaryStatistics collect = students.stream()
                .collect(Collectors.summarizingDouble(Student::getAge));
        System.out.println(collect.getMax());
        System.out.println(collect.getAverage());
        System.out.println(collect.getSum());
    }

    @Test       //分区
    public void test8(){
        Map<Boolean, List<Student>> map = students.stream()
                .collect(Collectors.partitioningBy((e) -> e.getStature() > 180));
        System.out.println(map);
    }

    @Test       //多级分组
    public void test7(){
        Map<String, Map<String, List<Student>>> map = students.stream()
                .collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy((e) -> {
                    if ((((Student) e).getAge() > 45)) {
                        return "壮年";
                    } else {
                        return "青年";
                    }
                })));
        System.out.println(map);
    }

    @Test       //分组
    public void test6(){
        Map<String, List<Student>> map = students.stream()
                .collect(Collectors.groupingBy(Student::getName));
        System.out.println(map);
    }

    @Test
    public void test5(){
        //总数
        Long collect = students.stream()
                .collect(Collectors.counting());
        System.out.println(collect);

        //平均值
        Double collect1 = students.stream()
                .collect(Collectors.averagingInt(Student::getAge));
        System.out.println(collect1);

        //总和
        Double collect2 = students.stream()
                .collect(Collectors.summingDouble(Student::getAge));
        System.out.println(collect2);

        //最大值
        Optional<Student> student = students.stream()
                .collect(Collectors.maxBy((t1, t2) -> Integer.compare(t1.getStature(), t2.getStature())));
        System.out.println(student.get());

        //最小值
        Optional<Integer> collect3 = students.stream()
                .map(Student::getAge)
                .collect(Collectors.minBy(Integer::compareTo));
        System.out.println(collect3.get());
    }

    @Test
    public void test4(){
        List<String> collect = students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(collect);

        Set<Integer> set = students.stream()
                .map(Student::getAge)
                .collect(Collectors.toSet());
        System.out.println(set);

        HashSet<Integer> hashSet = students.stream()
                .map(Student::getAge)
                .collect(Collectors.toCollection(HashSet::new));//指定集合
        System.out.println(hashSet);
    }

    /*
        归约
        reduce(T identity, BinaryOperator) / reduce(BinaryOperator) 可以将流中元素反复结合起来返回一个值
     */
    @Test
    public void Test3(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        Optional<Integer> reduce1 = students.stream() //Optional把可能为空的object封装到其中
                .map(Student::getAge)
                .reduce(Integer::sum);
        System.out.println(reduce1.get());
    }

    /*
        查找与匹配
        allMatch - 检查是否匹配所有元素
        anyMatch - 检查是否至少匹配一个元素
        noneMatch - 检查是否没有匹配所有元素
        count - 返回流中元素的总个数
        max - 返回流中最大值
        min - 返回流中最小值
     */
    @Test
    public void test2(){
        long count = students.stream()
                .count();
        System.out.println(count);

        Optional<Student> student = students.stream()
                .max((e1, e2) -> Integer.compare(e1.getStature(), e2.getStature()));
        System.out.println(student);

        Optional<String> optional = students.stream()
                .map(Student::getName)
                .min(String::compareTo);
        System.out.println(optional);
    }

    @Test
    public void test1(){
        Boolean b = students.stream()
                .allMatch((e) -> e.getSpecialities().contains(SpecialityEnum.RUNNING));
        System.out.println(b);

        Boolean b1 = students.stream()
                .anyMatch((e) -> e.getSpecialities().contains(SpecialityEnum.RUNNING));
        System.out.println(b1);

        Optional<Student> student = students.stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .findFirst();
        System.out.println(student.get());

        Optional<Student> student1 = students.parallelStream()      //并行
                .filter((e) -> e.getSpecialities().contains(SpecialityEnum.RUNNING))
                .findAny();
        System.out.println(student1.get());

    }

}
