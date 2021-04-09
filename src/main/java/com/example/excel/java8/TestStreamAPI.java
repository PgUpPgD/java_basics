package com.example.excel.java8;


import com.example.excel.entity.SpecialityEnum;
import com.example.excel.entity.Student;
import com.example.excel.entity.Trader;
import com.example.excel.entity.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamAPI {

    /*
         1、给定一个数字列表，[1， 2， 3， 4]，返回 [1， 4， 9， 16]
     */
    @Test
    public void test1(){
        Integer[] nums = new Integer[]{1,2,3,4};
        Stream.of(nums)
                .map((t) -> t * t)
                .forEach(System.out::println);
    }
    /*
        2、数一数流中有多少个元素
     */
    List<Student> students = Arrays.asList(
            new Student("路飞", 22, 175, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.SING)),
            new Student("红发", 40, 180, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.DANCE)),
            new Student("白胡子", 50, 185, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.DANCE)),
            new Student("雷利", 48, 176, Arrays.asList(SpecialityEnum.SING, SpecialityEnum.RUNNING))
    );
    @Test
    public void test2(){
        Optional<Integer> reduce = students.stream()
                .map((t) -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());
    }

    List<Transaction> transactions = Arrays.asList(
            new Transaction(new Trader("brain","Cambridge"),2011,300),
            new Transaction(new Trader("Raoul","Cambridge"),2012,1000),
            new Transaction(new Trader("Raoul","Cambridge"),2011,400),
            new Transaction(new Trader("Mario","Milan"),2012,710),
            new Transaction(new Trader("Mario","Milan"),2012,700),
            new Transaction(new Trader("Alan","Cambridge"),2012,950)
    );
    /*
        1、找出2011年发生的所有交易，并按照交易额排序（从低到高）
        2、交易员都在哪些不同城市工作过？
        3、查找所有来自剑桥的交易员，并按姓名排序
        4、返回所有交易员的名字字符串，按照字母顺序排序
        5、有没有交易员是在米兰工作的
        6、打印生活在剑桥的交易员的所有交易额
        7、所有交易中，最高的交易额是多少
        8、找到交易额最小的交易
     */

    @Test
    public void test3(){
        transactions.stream()
                .filter((t) -> t.getYear() == 2011)
                .sorted((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()))
                .forEach(System.out::println);

        Map<String, Map<String, List<Transaction>>> collect = transactions.stream()
                .collect(Collectors.groupingBy((t) -> t.getTrader().getName(), Collectors.groupingBy((e) -> e.getTrader().getCity())));
        Set<String> keySet = collect.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            Map<String, List<Transaction>> stringListMap = collect.get(next);
            Set<String> set = stringListMap.keySet();
            Iterator<String> iterator1 = set.iterator();
            while (iterator1.hasNext()){
                String next1 = iterator1.next();
                System.out.println(next + ":" + next1);
            }
        }

        transactions.stream()
                .map(Transaction::getTrader)
                .filter((t) -> t.getCity().equals("Cambridge"))
                .sorted((e, e1) -> e.getName().compareTo(e1.getName()))
                .map(Trader::getName)
                .distinct()
                .forEach(System.out::println);

        List<Character> list = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .flatMap((t) -> filterCharacter(t)) //TestStreamAPI::filterCharacter
                .sorted()
                .collect(Collectors.toList());
        System.out.println(list);

        String reduce = transactions.stream()
                .map((t) -> t.getTrader().getName())
                .sorted()
                .reduce("", String::concat);
        System.out.println(reduce);

        boolean b = transactions.stream()
                .anyMatch((e) -> e.getTrader().getCity().contains("Milan"));
        System.out.println(b);

        Optional<Integer> reduce1 = transactions.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(reduce1.get());

        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compare);
        System.out.println(max.get());

        Optional<Transaction> first = transactions.stream()
                .sorted((t, t1) -> Integer.compare(t.getValue(), t1.getValue()))
                .findFirst();
        System.out.println(first.get());
        Optional<Transaction> first1 = transactions.stream()
                .min((t, t1) -> Integer.compare(t.getValue(), t1.getValue()));
        System.out.println(first1.get());

    }

    public static Stream<Character> filterCharacter(String str){ //
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }

    @Test
    public void test4(){
        Map<Integer, String> map = new HashMap<>();
        map.put(15, "Mahesh");
        map.put(10, "Suresh");
        map.put(30, "Nilesh");

        System.out.println("---Sort by Map Value---");
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(e -> System.out.println("Key: "+ e.getKey() +", Value: "+ e.getValue()));

        System.out.println("---Sort by Map Key---");System.out.println("---Sort by Map Key---");
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> System.out.println("Key: "+ e.getKey() +", Value: "+ e.getValue()));
    }

}
