package com.example.excel.java8;


import com.example.excel.entity.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream的三个操作步骤：
 * 1.创建Stream
 * 2.中间操作
 * 3.终止操作（终端操作）
 */
public class TestStreamApi1 {
    List<Student> students = Arrays.asList(
            new Student("路飞", 22, 175),
            new Student("红发", 40, 180),
            new Student("白胡子", 50, 185),
            new Student("艾斯", 25, 183),
            new Student("雷利", 48, 176),
            new Student("雷利", 48, 176),
            new Student("雷利", 48, 176)
    );
    //中间操作
    /*
        映射
        map — 接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被
               应用到 每个元素 上，并将其映射成一个新的元素
        flatMap — 接收一个函数作为参数，将流中的 每个值 都换成另外一个流，然后把所有流连接成一个流
     */
    @Test
    public void test2(){
        List<String> list = Arrays.asList("aa", "bb", "cc");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("------------------------------");

        students.stream()
                .map(Student::getName)
                .forEach(System.out::println);

        System.out.println("------------------------------");

        Stream<Stream<Character>> stream = list.stream()    //map
                .map(TestStreamApi1::filterCharacter);     //{ {a,a},{b,b},{c,c} }
        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("------------------------------");

        Stream<Character> stream1 = list.stream()            //flatMap
                .flatMap(TestStreamApi1::filterCharacter);   //{ a,a,b,b,c,c }
        stream1.forEach(System.out::println);
    }
    //map和flatMap类似于
    @Test
    public void test3(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.addAll(list);
        System.out.println(list);   //flatMap
        List<Object> list1 = new ArrayList<>();
        list1.add("c");
        list1.add("d");
        list1.add(list);
        System.out.println(list1);  //map
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }

    /*
        筛选与切片
        filter — 接收 Lambda ,从流中排除某些元素
        limit — 截断流，使其元素不超过给定数量
        skip(n) — 跳过元素，返回一个扔掉了前 n 个元素的流，若该流中元素不足 n 个，
                   则返回一个空流，和limit互补
        distinct — 筛选，通过流所生成元素的hashCode() 和 equals()，除去重复元素
     */
    //内部迭代：操作由Stream Api完成
    @Test
    public void test1(){
        Stream<Student> list = students.stream()
                .filter((e) -> {
                    System.out.println("中间操作不会执行任何处理");
                    return e.getAge() > 35;
                })
                .skip(1)        //跳过 前 1 个
                .distinct()     //去掉重复的数据  重写hasCode和equals方法
                .limit(3);      //满足2条数据后，停止后续操作
        //终止操作: 一次性执行全部内容，即"惰性求值"
        list.forEach(System.out::println);
    }
    //外部迭代
    @Test
    public void test(){
        Iterator<Student> it = students.iterator();
        while (it.hasNext()){
            String name = it.next().getName();
            System.out.println(name);
        }
    }

}
