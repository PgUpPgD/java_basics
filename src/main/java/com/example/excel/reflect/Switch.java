package com.example.excel.reflect;

public class Switch {

    private String dayString;

    public String day(int day){
        switch(day){
            case 1 : dayString = "星期一";
                break;
            case 2 : dayString = "星期二";
                break;
            case 3 : dayString = "星期三";
                break;
            case 4 : dayString = "星期四";
                break;
            case 5 : dayString = "星期五";
                break;
            case 6 : dayString = "星期六";
                break;
            default : dayString = "星期日";
                break;
        }
        return dayString;
    }

    public static void main(String[] args) {
        Switch sss = new Switch();
        String day = sss.day(2);
        System.out.println(day);
    }

}
