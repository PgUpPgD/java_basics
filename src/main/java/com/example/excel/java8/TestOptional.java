package com.example.excel.java8;

import com.example.excel.entity.SpecialityEnum;
import com.example.excel.entity.Student;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.empty;

@Data
public class TestOptional {
    /*
        为了减少空指针异常,和快速定位
        Optional 容器类的常用方法：
        Optional.of（T t）创建一个optional实例
        Optional.empty() 创建一个空的optional实例
        Optional.ofNullable(T t) 若t不为null，创建Optional实例，否则创建空实例
        isPresent() 判断是否包含值
        orElse(T t) 如果调用对象返回值，返回该值，否则返回 t
        orElseGet(Supplier s) 如果调用对象包含值，返回该值，否则返回 s 获取的值
        map(Function mapper) 与map类似，要求返回值必须是Optional
     */

    @Test
    public void test4(){
        Optional<Student> student = Optional.of(
                new Student("路飞", 22, 175, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.SING))
        );

        Optional<String> s = student.map(Student::getName);
        String a = s.orElse(null);
        System.out.println(a);

        Optional<String> s1 = student.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(s1.get());
    }

    @Test
    public void test3(){
        Optional<Student> student = Optional.empty();

        Student student1 = student.orElse(new Student("路飞", 22, 175, Arrays.asList(SpecialityEnum.RUNNING, SpecialityEnum.SING)));
        System.out.println(student1);

        Student student2 = student.orElseGet(Student::new);
        System.out.println(student2);
    }

    @Test
    public void test2(){
        Optional<Student> student = empty();
        System.out.println(student.get());
    }

    @Test
    public void test1(){
        Optional<Student> student = Optional.of(new Student());
        Student student1 = student.get();
        System.out.println(student1);
    }

    //例：
//    private Student student;
    private Optional<Student> student = Optional.empty();
    public TestOptional() {}

//空指针异常
//    public static void reName(TestOptional op){
//        String name = op.getStudent().getName();
//        System.out.println(name);
//    }
//    @TestExcel
//    public void test5(){
//        reName(new TestOptional());
//    }

    public static void reName2(Optional<TestOptional> op){
        String name = op.orElse(new TestOptional())
                .getStudent()
                .orElse(new Student("呵呵", 18))
                .getName();
        System.out.println(name);
    }
    @Test
    public void test5(){
        Optional<TestOptional> opt = Optional.ofNullable(null);
        reName2(opt);
    }



}
