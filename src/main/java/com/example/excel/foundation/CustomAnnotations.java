package com.example.excel.foundation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Target(ElementType.FIELD)   //用于字段上 .
@Retention(RetentionPolicy.RUNTIME)  //运行时
public @interface CustomAnnotations {
    //默认是public，没有设default值，则在使用注解时要在括号里赋值
    String description();
    int length();
}
