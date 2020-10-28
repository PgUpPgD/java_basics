package com.example.excel.innerClass;

public class Shadow {

    public int x = 0;
    class FirstLevel{
        public int x = 1;
        void methodInFirstLevel(int x) {
            System.out.println("x = " + x); //23
            System.out.println("this.x = " + this.x);   //1
            System.out.println("Shadow.this.x = " + Shadow.this.x);    //0
        }
    }

    public static void main(String... args) {
        Shadow st = new Shadow();
        Shadow.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }

}
