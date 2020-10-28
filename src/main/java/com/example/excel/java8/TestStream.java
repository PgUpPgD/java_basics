package com.example.excel.java8;


import com.example.excel.entity.OutstandingClass;
import com.example.excel.entity.SpecialityEnum;
import com.example.excel.entity.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.maxBy;

public class TestStream {
    public static void main(String[] args) {
        List<Student> studentList = Stream.of(new Student("路飞", 22, 175),
                new Student("红发", 40, 180),
                new Student("白胡子", 50, 185))
                //收集Collectors.toList()
                .collect(Collectors.toList());
        System.out.println(studentList);

        List<SpecialityEnum> list2 = new ArrayList<>();
        list2.add(SpecialityEnum.SING);
        List<SpecialityEnum> list3 = new ArrayList<>();
        list3.add(SpecialityEnum.DANCE);
        List<SpecialityEnum> list4 = new ArrayList<>();
        list4.add(SpecialityEnum.RUNNING);
        List<Student> students = new ArrayList<>(3);
        students.add(new Student("路飞", 22, 175, list2));
        students.add(new Student("红发", 40, 180, list3));
        students.add(new Student("白胡子", 50, 185, list4));

        List<Student> list = students.stream()
                //过滤filter（）
                .filter(stu -> stu.getStature() < 180)
                .collect(Collectors.toList());
        System.out.println(list);

        List<String> names = students.stream()
                //内部转换
                .map(student -> student.getName())
                .collect(Collectors.toList());
        System.out.println(names);

        List<Student> student1 = new ArrayList<>(3);
        student1.add(new Student("艾斯", 25, 183));
        student1.add(new Student("雷利", 48, 176));

        List<Student> studentList1 = Stream.of(students,student1)
                //flatMap将多个Stream合并为一个Stream
                .flatMap(students1 -> students1.stream()).collect(Collectors.toList());
        System.out.println(studentList1);


        Optional<Student> max = students.stream()
                //最大
                .max(comparing(stu -> stu.getAge()));
        Optional<Student> min = students.stream()
                //最小
                .min(comparing(stu -> stu.getAge()));
        //判断是否有值
        if (max.isPresent()) {
            System.out.println(max.get());
        }
        if (min.isPresent()) {
            System.out.println(min.get());
        }

        //计数
        long count = students.stream().filter(s1 -> s1.getAge() < 45).count();
        System.out.println(count);

        //减少操作可以实现从一组值中生成一个值
        Integer reduce = Stream.of(1, 2, 3, 4).reduce(0, (acc, x) -> acc+ x);
        System.out.println(reduce);

        //转换成值
        OutstandingClass ostClass1 = new OutstandingClass("一班", students);
        //复制students，并移除一个学生
        List<Student> students2 = new ArrayList<>(students);
        students2.remove(1);
        OutstandingClass ostClass2 = new OutstandingClass("二班", students2);
        //将ostClass1、ostClass2转换为Stream
        Stream<OutstandingClass> classStream = Stream.of(ostClass1, ostClass2);
        OutstandingClass outstandingClass = biggestGroup(classStream);
        System.out.println("人数最多的班级是：" + outstandingClass.getName());

        System.out.println("一班平均年龄是：" + averageNumberOfStudent(students));

        //转换成块      分为会唱歌与不会唱歌的两个集合
        Map<Boolean, List<Student>> listMap = students.stream().collect(
                Collectors.partitioningBy(student -> student.getSpecialities().
                        contains(SpecialityEnum.SING))
        );
        System.out.println(listMap);

        //根据学生第一个特长进行分组
        Map<SpecialityEnum, List<Student>> listMap1 =
                students.stream().collect(
                        Collectors.groupingBy(student -> student.getSpecialities().get(0)));
        System.out.println(listMap1);

        //字符串拼接
        String name = students.stream()
                .map(Student::getName).collect(Collectors.joining(",","[","]"));
        System.out.println(name);

        //分组统计
        int[] arr=new int[]{5,1,3,4,1};
        List<Integer> list5= Arrays.stream(arr).boxed().collect(Collectors.toList());
        //groupingBy分组
        Map<Integer, Long> map = list5.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //控制台输出map
        map.forEach((k,v)->{
            System.out.println("k="+k+",v="+v);
        });

    }

    //获取人数最多的班级
    private static OutstandingClass biggestGroup(Stream<OutstandingClass> outstandingClasses) {
        return outstandingClasses.collect(
                maxBy(Comparator.comparing(ostClass -> ostClass.getStudents().size())))
                .orElseGet(OutstandingClass::new);
    }

    //计算平均年龄
    private static double averageNumberOfStudent(List<Student> students) {
        return students.stream().collect(averagingInt(Student::getAge));
    }


}
