package com.example.excel.exception;

public class TryCatch {
/*
* 1.无异常，finally有return   出口finally  2
* 2.有异常，finally有return   出口finally  3
* 3.有双异常，finally有return   出口finally  3
*
* */
    public static Integer add(){
        String v = "123";
        new StringBuilder(); //线程不安全
        new StringBuffer();
        int i = 0;
        try{
            i++;
            int a = 1/0;
            return i;
        }catch (Exception e){
            i++;
            int a = 1/0;
            return i;
        }finally {
            i++;
            return i;
        }
    }

    public static void main(String[] args) {
        Integer add = TryCatch.add();
        System.out.println(add);
    }

}
