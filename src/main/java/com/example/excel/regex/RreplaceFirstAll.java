package com.example.excel.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 替换方法
 * 替换方法是替换输入字符串里文本的方法：
 *
 * 序号	方法及说明
 * 1	public Matcher appendReplacement(StringBuffer sb, String replacement)
 * 实现非终端添加和替换步骤。
 * 2	public StringBuffer appendTail(StringBuffer sb)
 * 实现终端添加和替换步骤。
 * 3	public String replaceAll(String replacement)
 *  替换模式与给定替换字符串相匹配的输入序列的每个子序列。
 * 4	public String replaceFirst(String replacement)
 *  替换模式与给定替换字符串匹配的输入序列的第一个子序列。
 * 5	public static String quoteReplacement(String s)
 * 返回指定字符串的字面替换字符串。这个方法返回一个字符串，
 * 就像传递给Matcher类的appendReplacement 方法一个字面字符串一样工作。
 */
public class RreplaceFirstAll {

    private static String REGEX = "dog";
    private static String INPUT = "The dog says meow. All dogs say meow.";
    private static String REPLACE = "cat";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        // get a matcher object
        Matcher m = p.matcher(INPUT);
        //INPUT = m.replaceAll(REPLACE);
        INPUT = m.replaceFirst(REPLACE);
        System.out.println(INPUT);
    }

}
