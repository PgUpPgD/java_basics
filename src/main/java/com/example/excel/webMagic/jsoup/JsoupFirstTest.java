package com.example.excel.webMagic.jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 *
 */
public class JsoupFirstTest {

    @Test
    public void testUrl() throws Exception{
        //解析url地址，一个参数地址，一个超时时间
        Document document = Jsoup.parse(new URL("http://yun.itheima.com"), 1000);
        //使用标签选择器，获取title标签中的内容
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testString() throws Exception{
        //使用工具读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("F:/title.html")).toString();
        //解析字符串
        Document document = Jsoup.parse(content);
        //使用标签选择器，获取title标签中的内容
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("F:/title.html"), "utf8");
        //使用标签选择器，获取title标签中的内容
        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test       //使用dom方式
    public void testDom() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("F:/title.html"), "utf8");
        //获取元素
        //1.根据id查询元素.getElementById();
        Element city_bj = document.getElementById("city_bj");
        System.out.println(city_bj.text());
        //2.根据标签获取元素.getElementsByTag()
        Element span = document.getElementsByTag("span").first();
        System.out.println(span.text());
        //3.根据class获取元素.getElementsByClass()
        Element class_a = document.getElementsByClass("class_a class_b").first();
        System.out.println(class_a.text());
        //4.根据属性获取元素.getElementsByAttribute()
        Element abc = document.getElementsByAttribute("abc").first();
        System.out.println(abc.text());
        //5.根据属性和属性值获取元素
        Element href = document.getElementsByAttributeValue("href", "http://sh.cn").first();
        System.out.println(href.text());
    }

    @Test
    public void testData() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("F:/title.html"), "utf8");

        //根据id获取元素
        Element element = document.getElementById("test");
        //1.元素中获取id
        String id = element.id();
        System.out.println(id);

        //2.元素中获取className
        String className = element.className();
        System.out.println(className);

        Set<String> names = element.classNames();
        for (String s : names){
            System.out.println(s);
        }

        //3.从元素中获取属性的值attr
        String attr = element.attr("id");
        String attrs = element.attr("class");
        System.out.println(attr + attrs);

        //4.从元素中获取所有属性attributes
        Attributes attributes = element.attributes();
        System.out.println(attributes.toString());

        //5.从元素中获取文本内容text
        String text = element.text();
        System.out.println(text);
    }

    @Test       //选择器方式
    public void testSelector() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("F:/title.html"), "utf8");
        //tagName通过标签获取元素
        Elements span = document.select("span");
        for (Element s : span){
            System.out.println(s.text());
        }
        //#id,通过id查找元素
        Element select = document.select("#city_bj").first();
        System.out.println(select.text());
        //.class通过class名称查找元素
        Element cla = document.select(".class_a").first();
        System.out.println(cla.text());
        //[attribute] 利用属性查找元素
        Element abc = document.select("[abc]").first();
        System.out.println(abc.text());
        //[attr-value]:利用属性和属性值查找元素
        Elements name = document.select("[class=s_name]");
        for (Element s : name){
            System.out.println(s.text());
        }
    }

    @Test       //组合选择器方式
    public void testSelector2() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("F:/title.html"), "utf8");
        //el#id 元素+id
        Element el = document.select("h3#city_bj").first();
        System.out.println(el.text());
        //el.class 元素+class
        Element el2 = document.select("li.class_a").first();
        System.out.println(el2.text());
        //el[attr] 元素+属性名
        Element el3 = document.select("span[abc]").first();
        System.out.println(el3.text());
        //任意组合 span[abc].s_name
        Element el4 = document.select("span[abc].s_name").first();
        System.out.println(el4.text());
        //ancestor child 查找某个元素下的子元素，比如： .city_con li 查找“city_con”下的所有li
        Elements el5 = document.select(".city_con li");
        System.out.println(el5.text());
        //parent > child 查找某个父元素下的 直接 子元素，比如city_con > ul > li
        Elements el6 = document.select(".city_con > ul > li");
        System.out.println(el6.text());
        // 查找city_con第一级（直接子元素）的ul，在查找所有ul下的第一级li
        //parent > * 查找某个父元素下的所有 直接 子元素
        Elements el7 = document.select(".city_con > *");
        System.out.println(el7.text());
    }


}
