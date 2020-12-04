package com.example.excel.webMagic.magic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * WebMagic的结构分为Downloader、PageProcessor、Scheduler、Pipeline四大组件，
 * 并由Spider将它们彼此组织起来。这四大组件对应爬虫生命周期中的下载、处理、管理和持久化等功能。
 *
 * WebMagic的四个组件:
 * Downloader
 * Downloader负责从互联网上下载页面，以便后续处理。WebMagic默认使用了Apache HttpClient作为下载工具。
 *
 * PageProcessor
 * PageProcessor负责解析页面，抽取有用信息，以及发现新的链接。
 * WebMagic使用Jsoup作为HTML解析工具，并基于其开发了解析XPath的工具Xsoup。
 * 在这四个组件中，PageProcessor对于每个站点每个页面都不一样，是需要使用者定制的部分。
 *
 * Scheduler
 * Scheduler负责管理待抓取的URL，以及一些去重的工作。
 * WebMagic默认提供了JDK的内存队列来管理URL，并用集合来进行去重。也支持使用Redis进行分布式管理。
 *
 * Pipeline
 * Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。
 * WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。
 * Pipeline定义了结果保存的方式，如果你要保存到指定数据库，则需要编写对应的Pipeline。
 * 对于一类需求一般只需编写一个Pipeline。
 *
 * 用于数据流传的对象
 * Request
 * Request是对URL地址的一层封装，一个Request对应一个URL地址。
 * 它是PageProcessor与Downloader交互的载体，也是PageProcessor控制Downloader唯一方式。
 * 除了URL本身外，它还包含一个Key-Value结构的字段extra。
 * 你可以在extra中保存一些特殊的属性，然后在其他地方读取，以完成不同的功能。
 * 例如附加上一个页面的一些信息等。
 *
 * Page
 * Page代表了从Downloader下载到的一个页面——可能是HTML，也可能是JSON或者其他文本格式的内容。
 * Page是WebMagic抽取过程的核心对象，它提供一些方法可供抽取、结果保存等。
 *
 * ResultItems
 * ResultItems相当于一个Map，它保存PageProcessor处理的结果，供Pipeline使用。
 * 它的API与Map很类似，值得注意的是它
 * 一个字段skip，若设置为true，则不应被Pipeline处理。
 */
//入门案例
public class JobPage implements PageProcessor {
    //解析页面
    @Override
    public void process(Page page) {
        //解析返回的数据Page，并且把解析的结果集自动放到ResultItems中

        System.out.println("----------- 地址一 -------------");
        //css表达式
        page.putField("title", page.getHtml().css("title"));
        page.putField("div4", page.getHtml().css("div._3oESJEbA6EDqNUcNu8namk ul li"));
        //XPath
        page.putField("div", page.getHtml().xpath("//div[@id=shortcut-2014]/div/ul/li/div/a/text()"));

        //正则表达式
        page.putField("div1", page.getHtml().css("div#shortcut-2014 a").regex(".*京东.*").all());

        //处理结果Api       多条数据只返回第一条，两种方式
        page.putField("div2", page.getHtml().css("div#shortcut-2014 a").regex(".*京东.*").get());
        page.putField("div3", page.getHtml().css("div#shortcut-2014 a").regex(".*京东.*").toString());

        System.out.println("----------- 地址二 -------------");

        //提取链接
        List<String> list = page.getHtml().css("div.top_newslist ul li a").links().regex(".*5[.]shtml$").all();
        page.addTargetRequests(list);
        page.putField("url", page.getHtml().css("div.second-title").all());
    }

    //站点信息
    private Site site = Site.me()
            .setCharset("utf8") // 设置编码
            .setTimeOut(10000) // 设置超时时间, 单位是ms毫秒
            .setRetrySleepTime(3000) // 设置重试的间隔时间
            .setSleepTime(3); // 设置重试次数 ;
    @Override
    public Site getSite() {
        return site;
    }

    //执行程序
    public static void main(String[] args) {
        Spider.create(new JobPage())
                //初始地址
//                .addUrl("https://kuaibao.jd.com/")
                .addUrl("https://www.sina.com.cn/")
                // 设置保存到文件夹
                .addPipeline(new FilePipeline("F:/magic"))
                .thread(5) // 设置有5个线程处理
                .run(); // 执行爬虫

    }




}
