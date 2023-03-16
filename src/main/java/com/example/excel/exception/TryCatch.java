package com.example.excel.exception;

public class TryCatch {
/*
* 1.无异常，finally有return   出口finally  2
* 2.有异常，finally有return   出口finally  3
* 3.有双异常，finally有return   出口finally  3
*
* */
    public static Integer add(){
        new StringBuilder(); //线程不安全
        new StringBuffer();
        int i = 0;
        try{
            i++;
            int a = 1/0;
            System.out.println("1............ " + i);
            return i;
        }catch (Exception e){
            i++;
//            int a = 1/0;
            System.out.println("2............" + i);
            return i;
        }finally {
            i++;
            System.out.println("3............" + i);
//            return i;
        }
    }

    public static void main(String[] args) {
        Integer add = TryCatch.add();
        System.out.println(add);

//        uploadFile(3);
    }

    public static void uploadFile(int num) {
        try {
            if (num > 5){
                int a = num / 0;
                System.out.println("a:" + a);
            }else {
                throw new RuntimeException("2...........");
            }
        } finally {
            System.out.println("正常执行...");
        }
    }
}
