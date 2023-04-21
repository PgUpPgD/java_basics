package com.example.excel.exception;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/3/29 10:02
 */
public class TestThrow {

    public static void main(String[] args) {

        try {
            if (true){
                throw new RuntimeException("1111111111111111");
            }
            if (true){
                throw new Exception("22222222222222");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new RuntimeException("333333333333333");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }



}
