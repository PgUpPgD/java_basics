package com.example.excel.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher 类的方法
 * 索引方法
 * 索引方法提供了有用的索引值，精确表明输入字符串中在哪能找到匹配：
 *
 * 序号	方法及说明
 * 1	public int start()
 * 返回以前匹配的初始索引。
 * 2	public int start(int group)
 *  返回在以前的匹配操作期间，由给定组所捕获的子序列的初始索引
 * 3	public int end()
 * 返回最后匹配字符之后的偏移量。
 * 4	public int end(int group)
 * 返回在以前的匹配操作期间，由给定组所捕获子序列的最后字符之后的偏移量。
 */
public class StartEnd {
    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT = "cat cat cat cattie cat";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);   //获取matcher对象
        int count = 0;

        while (m.find()){
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
        }
        System.out.println(INPUT.length());
    }



}
