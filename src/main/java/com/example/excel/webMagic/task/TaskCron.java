package com.example.excel.webMagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * 定时任务
 * @Scheduled注解，其属性如下：
 * 1）**cron：**cron表达式，指定任务在特定时间执行；
 * 2）fixedDelay：上一次任务执行完后多久再执行，参数类型为long，单位ms
 * 3）**fixedDelayString：**与fixedDelay含义一样，只是参数类型变为String
 * 4）**fixedRate：**按一定的频率执行任务，参数类型为long，单位ms
 * 5）fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String
 * 6）**initialDelay：**延迟多久再第一次执行任务，参数类型为long，单位ms
 * 7）**initialDelayString：**与initialDelay的含义一样，只是将参数类型变为String
 * 8）**zone：**时区，默认为当前时区，一般没有用到
 * 1. Cron表达式
 * cron的表达式是字符串，实际上是由七子表达式，描述个别细节的时间表。这些子表达式是分开的空白，代
 * Seconds
 * Minutes
 * Hours
 * Day-of-Month
 * Month
 * Day-of-Week
 * Year (可选字段)
 *
 * 例 "0 0 12 ? * WED" 在每星期三下午12:00 执行,
 * “*” 代表整个时间段
 * 每一个字段都有一套可以指定有效值，如
 * Seconds (秒) ：可以用数字0－59 表示，
 * Minutes(分) ：可以用数字0－59 表示，
 * Hours(时) ：可以用数字0-23表示,
 * Day-of-Month(天) ：可以用数字1-31 中的任一一个值，但要注意一些特别的月份
 * Month(月) ：可以用0-11 或用字符串:
 * JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
 * Day-of-Week(天) ：可以用数字1-7表示（1 ＝ 星期日）或用字符口串:
 * SUN, MON, TUE, WED, THU, FRI, SAT
 * “/”：为特别单位，表示为“每”如“0/15”表示每隔15分钟执行一次,“0”表示为从“0”分开始, “3/20”表示表示每隔20分钟执行一次，“3”表示从第3分钟开始执行
 * “?”：表示每月的某一天，或第周的某一天
 * “L”：用于每月，或每周，表示为每月的最后一天，或每个月的最后星期几如“6L”表示“每月的最后一个星期五”
 * 可以使用CronExpBuilder (表达式生成器)生成表达式
 *
 * 网页去重
 * 之前我们对下载的url地址进行了去重操作，避免同样的url下载多次。其实不光url需要去重，我们对下载的内容也需要去重。
 * 在网上我们可以找到许多内容相似的文章。但是实际我们只需要其中一个即可
 *  用java 实现 SimHash 算法，实现匹配 一般签名距离为3以下即可认为很相似
 *
 * 代理的使用
 * 提供免费代理ip的服务商网站：米扑代理 https://proxy.mimvp.com/free.php 免费的有的用不了，多试几个
 * 请求能返回地址的api  https://api.myip.com/
 *
 */
//@Component
public class TaskCron implements PageProcessor{

    @Scheduled(cron = "0/10 * * * * *")
    public void process() {
        //创建下载器 Downloader
        HttpClientDownloader downloader = new HttpClientDownloader();
        //给下载器设置代理服务器
        downloader.setProxyProvider(SimpleProxyProvider.from
                (new Proxy("1.181.48.68", 3128, "", "")));
        Spider.create(new TaskCron())
//                .addUrl("http://api.myip.com/")
                .addUrl("https://www.eastmoney.com/")
                .setDownloader(downloader)
                .thread(2)
                .run();
    }

    @Override
    public void process(Page page) {
        System.out.println("page:" + page.getHtml().toString());
    }

    private Site site = Site.me()
            .setCharset("gbk") // 设置编码
            .setTimeOut(10*1000) // 设置超时时间
            .setRetrySleepTime(3000) // 设置重试的间隔时间
            .setRetryTimes(3); // 设置重试的次数
    @Override
    public Site getSite() {
        return site;
    }

}
