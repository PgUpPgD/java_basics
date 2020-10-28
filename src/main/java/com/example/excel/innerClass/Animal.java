package com.example.excel.innerClass;

public class Animal {

    private final String ANIMAL = "动物";

    public void accessTest() {
        System.out.println("匿名内部类访问其外部类方法");
    }

    class AnimalTest {
        private String name;
        public AnimalTest(String name) {
            this.name = name;
        }

        public void printAnimalName() {
            System.out.println(bird.name);
        }
    }

    // 鸟类，匿名子类，继承自Animal类，可以覆写父类方法
    AnimalTest bird = new AnimalTest("布谷鸟") {
        @Override
        public void printAnimalName() {
            accessTest();               // 访问外部类成员
            System.out.println(ANIMAL);  // 访问外部类final修饰的变量
            super.printAnimalName();
        }
    };

    public void print() {
        bird.printAnimalName();
    }

    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.print();
    }

}
