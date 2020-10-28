package com.example.excel.java8;

import com.example.excel.entity.Student;
import com.example.excel.service.StudentService;
import com.example.excel.service.impl.StudentServiceFilterAgeImpl;
import org.junit.Test;

import java.util.*;

public class TestLambda {

    //原来的匿名内部类
    @Test
    public void test1(){
        Comparator<Integer> com = new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> set = new TreeSet<>(com);
    }

    //Lambda 表达式
    @Test
    public void test2(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        System.out.println(com.compare(2,2));
    }

    List<Student> students = Arrays.asList(
            new Student("路飞", 22, 175),
            new Student("红发", 40, 180),
            new Student("白胡子", 50, 185),
            new Student("艾斯", 25, 183),
            new Student("雷利", 48, 176)
    );

    @Test
    public void test3(){
        List<Student> stu = stu(students);
        List<Student> stus = stus(students);
        for (Student s : stu){
            System.out.println(s);
        }
    }
    //获取当前age小于30的人
    public List<Student> stu(List<Student> list){
        List<Student> list1 = new ArrayList<>();
        for (Student s : list){
            if (s.getAge() < 30){
                list1.add(s);
            }
        }
        return list1;
    }
    //获取当前身高小于180的人
    public List<Student> stus(List<Student> list){
        List<Student> list1 = new ArrayList<>();
        for (Student s : list){
            if (s.getStature() < 180){
                list1.add(s);
            }
        }
        return list1;
    }

    @Test
    public void test4(){
        List<Student> list = filterStudent(students, new StudentServiceFilterAgeImpl());
        for (Student s : list){
            System.out.println(s);
        }
    }
    //优化方式一：接口
    public List<Student> filterStudent(List<Student> list, StudentService<Student> stu){
        List<Student> list1 = new ArrayList<>();
        for (Student s : list){
            //需要额外写StudentService的实现类：student.getAge() < 30;
            if (stu.filterStudent(s)){
                list1.add(s);
            }
        }
        return list1;
    }

    //优化方式二：匿名内部类
    @Test
    public void test5(){
        List<Student> list = filterStudent(students, new StudentService<Student>(){
            @Override       //直接实现StudentService：student.getStature() < 180;
            public Boolean filterStudent(Student student) {
                return student.getStature() < 180;
            }
        });
        for (Student s : list){
            System.out.println(s);
        }
    }

    //优化方式三：Lambda表达式
    @Test
    public void test6(){
        List<Student> list = filterStudent(students, (e) -> e.getStature() < 180);
        list.forEach(System.out::println);
    }

    //优化方式四：stream
    @Test
    public void test7(){
        students.stream()
                .filter((t) -> t.getAge() < 30)
                .limit(2)
                .forEach(System.out::println);

        students.stream()
                .filter((t) -> t.getAge() < 30)
                .map(Student::getName)
                .forEach(System.out::println);
    }


}
