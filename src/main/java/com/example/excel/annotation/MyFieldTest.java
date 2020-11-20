package com.example.excel.annotation;

import org.junit.Test;

import java.lang.reflect.Field;

public class MyFieldTest {
    //使用我们的自定义注解
    @CustomAnnotations(description = "用户名", length = 12)
    private String username;

    @Test
    public void testMyFiled(){
        //获取类模板
        Class c = MyFieldTest.class;
        //获取所有字段
        for (Field f : c.getDeclaredFields()){
            System.out.println(f);
            //判断这个字段是否有MyField注解 Annotation 注释  present 现在
            if (f.isAnnotationPresent(CustomAnnotations.class)){
                CustomAnnotations annotation = f.getAnnotation(CustomAnnotations.class);
                System.out.println("字段:[" + f.getName() + "], 描述:[" + annotation.description() + "], 长度:[" + annotation.length() +"]");
            }
        }

    }
}
