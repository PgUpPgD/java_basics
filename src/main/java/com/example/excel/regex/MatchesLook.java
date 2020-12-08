package com.example.excel.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 查找方法
 * 查找方法用来检查输入字符串并返回一个布尔值，表示是否找到该模式：
 *
 * 序号	方法及说明
 * 1	public boolean lookingAt()
 *  尝试将从区域开头开始的输入序列与该模式匹配。
 * 2	public boolean find()
 * 尝试查找与该模式匹配的输入序列的下一个子序列。
 * 3	public boolean find(int start）
 * 重置此匹配器，然后尝试查找匹配该模式、从指定索引开始的输入序列的下一个子序列。
 * 4	public boolean matches()
 * 尝试将整个区域与模式匹配。
 */
public class MatchesLook {

    private static final String REGEX = "foo";
    private static final String INPUT = "fooooooooooooooooo";
    private static final String INPUT2 = "ooooofoooooooooooo";
    private static Pattern pattern;
    private static Matcher matcher;
    private static Matcher matcher2;

    public static void main(String[] args) {
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);
        matcher2 = pattern.matcher(INPUT2);

        System.out.println("Current REGEX is: "+REGEX);
        System.out.println("Current INPUT is: "+INPUT);
        System.out.println("Current INPUT2 is: "+INPUT2);

        System.out.println("lookingAt:" + matcher.lookingAt());
        System.out.println("matches:" + matcher.matches());
        System.out.println("lookingAt:" + matcher2.lookingAt());
    }

}
