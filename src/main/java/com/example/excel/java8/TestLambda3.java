package com.example.excel.java8;

import com.example.excel.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * Java8 内置的四大核心函数式接口
 * Consumer<T> : 消费型接口
 *      void accept(T t);
 * Supplier<T> : 供给型接口
 *      T get();
 * Function<T, R> : 函数型接口
 *      R apply(T t);
 * Predicate<T> : 断言型接口
 *      boolean test(T t);
 */
public class TestLambda3 {

    public Consumer<User> str1 = (user) -> user.setName("name");
    public Supplier<User> str2 = () -> {
        User user = new User();
        user.setUserId("1");
        return user;
    };
    public Function<String, User> str3 = (t) -> new User(t,t,t);
    public Predicate<User> str4 = (user) -> user.getName().equals("1");
    public BiFunction<String, String, User> str5 = (a, b) -> new User(a,b,b);

    //需求：充话费
    public void prepaid(Double dou, Consumer<Double> con){
        con.accept(dou);
    }
    @Test   //Consumer<T> : 消费型接口
    public void test1(){
        prepaid(99.80, (s) -> System.out.println("话费充了" + s));
    }

    //需求：返回10个随机数
    public List<Integer> reNum(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++){
            Integer nu = sup.get();
            list.add(nu);
        }
        return list;
    }
    @Test      //Supplier<T> : 供给型接口
    public void test2(){
        List<Integer> num = reNum(10, () -> (int)(Math.random() * 100));
        System.out.println(num);
    }

    //需求用于处理字符串
    public String reStr(String str, Function<String, String> fun){
        return fun.apply(str);
    }
    @Test       //Function<T, R> : 函数型接口
    public void test3(){
        String s = reStr("\t\t\t\t 冰寒千古   ", (t) -> t.trim());
        System.out.println(s);
    }

    //需求：将满足条件的字符串放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> list1 = new ArrayList<>();
        for (String str : list){
            if (pre.test(str)){
                list1.add(str);
            }
        }
        return list1;
    }
    @Test       //Predicate<T> : 断言型接口
    public void test4(){
        List<String> list = filterStr(this.list, (t) -> t.contains("u"));
        System.out.println(list);
    }

    List<String> list = Arrays.asList(
            "Consumer<T> : 消费型接口",
            "Supplier<T> : 供给型接口",
            "Function<T, R> : 函数型接口",
            "Predicate<T> : 断言型接口"
    );

}
