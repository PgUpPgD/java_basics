package com.example.excel.reflect;

public class BubbleSort {

    public static void main(String[] args){
        int[] num = new int[]{2,5,3,4,1};
        for (int i = 0; i < num.length; i++){
            for (int j = 0; j < num.length - 1 - i; j++){
                if (num[j] > num[j + 1]){
                    int a = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = a;
                }
            }
        }
        System.out.println(num[0] + "-" + num[4]);
    }


}
