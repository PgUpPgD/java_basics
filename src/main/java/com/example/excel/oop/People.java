package com.example.excel.oop;

import lombok.Data;

@Data
public class People {

    private int a = 1;
    private int b;
    static int c = 2;

    public People(){}
    public People(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void add(){
        People people = new People();
        int a = people.getA();
        int d = a + c;
        System.out.println(d);
    }

    public void add(int a, int b){
        People people = new People(a,b);
        int a1 = people.getA();
        int d = a1 + c + people.getB();
        System.out.println(d);
    }

    public void add(int a){
        People people = new People(a,c);
        int a1 = people.getA();
        int d = a1 + c;
        System.out.println(d);
    }

    public static void main(String[] args) {
        People people = new People();
        People people1 = new People();
        if (people == people1){
            System.out.println("true");
        }
    }


}
