package com.example.excel.innerClass;

public class ShadowT {
    public int x = 0;
    interface FirstLevel {
        void methodInFirstLevel(int x);
    }

    FirstLevel firstLevel =  new FirstLevel() {
        public int x = 1;
        @Override
        public void methodInFirstLevel(int x) {
            System.out.println("x = " + x); //23
            System.out.println("this.x = " + this.x);   //1
            System.out.println("ShadowT.this.x = " + ShadowT.this.x);    //0
        }
    };

    public static void main(String... args) {
        ShadowT st = new ShadowT();
        ShadowT.FirstLevel fl = st.firstLevel;
        fl.methodInFirstLevel(23);
    }
}
