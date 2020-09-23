package com.example.excel.reflect;

public class Reflect {

    public static void main(String[] args) {
        System.out.println(getV(2));
    }
    public static int getV(int i){
        int result = 0;
        switch (i){
            case 1:
                result = result + i;
            case 2:
                result = result + i*2;
                System.out.println("2" + result);
            case 3:
                result = result + i*3;
                System.out.println("3" + result);
        }
        return result;

    }

}
