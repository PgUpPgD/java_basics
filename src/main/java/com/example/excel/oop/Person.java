package com.example.excel.oop;

import lombok.Data;

@Data
public class Person extends People {

    private int a1;

    public Person(){}
    public Person(int a1, int a, int b){
        super(a, b);
        this.a1 = a1;
    }

    public void add(int a1){
        System.out.println("person");
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.add();
        person.add(1);
        person.add(1,2);

        //父类的引用指向子类的对象
        People people = new Person();
        people.add(1);
    }


}
