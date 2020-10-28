package com.example.excel.java8;

import com.example.excel.entity.Student;
import com.example.excel.service.MyFun;
import com.example.excel.service.MyFun2;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 一、Lambda表达式的基础语法：Java8中引进了一个新的操作符" -> "该操作符称为箭头操作符
 *      或Lambda操作符，箭头操作符将Lambda表达式分为两部分：
 * 左侧：Lambda表达式的参数列表
 * 右侧：Lambda表达式中所需要执行的功能，即Lambda体
 *
 * 语法格式一：无参数，无返回值
 *          () -> System.out.println("Hello World");
 * 语法格式二：有一个参数，无返回值
 *          (x) -> System.out.println(x);
 *          x -> System.out.println(x);  小括号也可省略
 * 语法格式三：有两个以上的参数，并且Lambda体中有多条语句
 *          Comparator<Integer> com = (x, y) -> {
 *             System.out.println("Hello World");
 *             return Integer.compare(x, y);
 *         };
 * 语法格式四：Lambda体中只有一条语句，大括号和return都可以不写
 *          Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * 语法格式五：Lambda表达式的参数列表的参数类型可以省略不写，因为JVM编译器会通过上下文推断出来数据类型
 *          称为"类型推断"
 *          Comparator<Integer> com = (Integer x, Integer y) -> Integer.compare(x, y);
 *
 * 二、Lambda表达式需要函数式接口的支持
 *          函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。
 *          可以使用注解@FunctionalInterface修饰，可以检查是否是函数式接口
 */
public class TestLambda2 {

    @Test
    public void test1(){
        int num = 1;   //jdk1.7是需要用final修饰， 1.8可不加，但默认有final
        Runnable r = new Runnable(){
            @Override
            public void run() {
                System.out.println("Hello World" + num);
            }
        };
        Runnable r1 = () -> System.out.println("Hello World" + num);
        r.run();
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> com = x -> System.out.println(x);
        com.accept("Hello World");
    }

    @Test
    public void test3(){
        Comparator<Integer> com = (x, y) -> {
            System.out.println("Hello World");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }

    //需求：对一个数进行运算
    @Test
    public void test5(){
        Integer num = operation(10, (x) -> x * x);
        System.out.println(num);
    }
    public Integer operation(Integer num, MyFun myFun){
        return myFun.getValue(num);
    }

    List<Student> students = Arrays.asList(
            new Student("路飞", 22, 175),
            new Student("红发", 40, 180),
            new Student("白胡子", 50, 185),
            new Student("艾斯", 25, 183),
            new Student("雷利", 48, 176)
    );

    /**
     * 按年龄排序，年龄相当按名字排
     */
    @Test
    public void test6(){
        Collections.sort(students, (e1, e2) ->{
            if (e1.getAge() == e2.getAge()){
                //String
                return e1.getName().compareTo(e2.getName());
            }else {
                //Integer   加 - 号为倒序
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Student s : students){
            System.out.println(s);
        }
    }

    @Test
    public void test7(){
        Long a = reInt(9l, 8, (x, y) -> x + y);
        System.out.println(a);
    }

    public Long reInt(Long a, Integer b, MyFun2<Long, Integer> my){
        return my.getValue(a, b);
    }



}
