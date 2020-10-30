package com.example.excel.java8;

import com.example.excel.entity.Student;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * 方法引用：若Lambda体中的内容已经实现了，我们可以使用"方法引用"
 *      （可以理解为方法引用是Lambda表达式的一种表现形式）
 * 主要有三种语法格式：
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名
 *
 * 注意：
 * 1、Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和
 *  返回值类型保持一致。
 * 2、若Lambda参数列表中的第一参数是 实例方法的调用者，而第二个参数是实例方法的参数时，
 *  可以使用ClassName :: method
 *
 * 构造器引用：
 *  格式：
 *  ClassName::new
 *  注意：需要调用的构造器的参数列表要与抽象方法的参数列表保持一致
 *
 * 数组引用：
 *  Type::new
 */
public class TestMethodRef {

    //数组引用
    @Test
    public void test6(){
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] str = fun.apply(2);
        System.out.println(str.length);
        Function<Integer, String[]> fun2 = String[]::new;
    }

    //构造器引用
    @Test
    public void test5(){
        Supplier<Student> stu = () -> new Student();
        //构造器引用方式
        Supplier<Student> stu2 = Student::new;  //无参构造
        Student student = stu2.get();
        System.out.println(student);

//        Function<String,Student> fun = new Student();   //需要一个参数的构造函数

        BiFunction<String,Integer,Student> fun2 = Student::new; //两参构造

    }

    //类::实例方法
    @Test
    public void test4(){
        BiPredicate<String, String> bq = (x, y) -> x.equals(y);
        bq.test("Hello", "world");
        BiPredicate<String, String> bq2 = String::equals;
    }

    //类::静态方法
    @Test
    public void test3(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        com.compare(1,2);
        Comparator<Integer> com2 = Integer::compare;
    }

    //对象::实例方法
    @Test
    public void test1(){
        PrintStream ps = System.out;
        Consumer<String> con = (x) -> ps.println(x);
        con.accept("万古");

        Consumer<String> con2 = ps::println;
        con2.accept("万古");

        Consumer<String> con3 = (x) -> System.out.println(x);
        con3.accept("万古");
    }
    @Test
    public void test2(){
        Student student = new Student("hello", 20, 172);
        Supplier<String> sup = () -> student.getName();
        String name = sup.get();
        System.out.println(name);

        Supplier<Integer> sup2 = student :: getAge;
        Integer age = sup2.get();
        System.out.println(age);
    }

    //例                                     //接口
    public void stature(Student student, Consumer<Student> con){
        con.accept(student);
    }
    public Boolean bl(Integer age, Predicate<Integer> pd){
        return pd.test(age);
    }
    @Test
    public void reEntity(){
        BiFunction<String, Integer, Student> fun = Student::new;
        Student student = fun.apply("赔钱虎", 28);
                                    //实现
        stature(student, (t) -> t.setStature(180));
        Boolean b = bl(student.getAge(), (t) -> t.equals(28));
        if (b){
            System.out.println(student.toString());
        }
    }

    @Test
    public void test8(){                                //实现
        List<Integer> num = operation(10, () -> (int)(Math.random() * 100));
        System.out.println(num);
    }                                                   //接口
    public List<Integer> operation(Integer num, Supplier<Integer> myFun){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++){
            Integer n = myFun.get();
            list.add(n);
        }
        return list;
    }

}
