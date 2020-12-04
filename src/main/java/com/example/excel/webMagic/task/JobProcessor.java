package com.example.excel.webMagic.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.excel.entity.JobInfo;
import com.example.excel.utils.MathSalary;
import com.example.excel.webMagic.magic.JobPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class JobProcessor implements PageProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobProcessor.class);

    private int count = 1;
    public static String url = "https://search.51job.com/list/020000,000000,0000,00,9,99,java,2,1.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";

    @Autowired
    private JobPipeline jobPipeline;

    @Override
    public void process(Page page) {
        // 因为在51招聘的页面中不能直接拿到页面元素, 经分析数据在页面的js中
        // 于是想办法将js对象(json数据)解析出来, 获取到详情页面的链接放到任务队列中
        // 解析页面, 获取script中招聘详情信息
        String dataJs = page.getHtml().css("script").regex(".*SEARCH_RESULT.*").get();
        //判断获取的数据是否为空
        if (!StringUtils.isEmpty(dataJs)){
            LOGGER.info("开始抓取第" + count++ + "页");
            //解析拿到的Json字符串
            String json = dataJs.substring(dataJs.indexOf("{"), dataJs.lastIndexOf("}") + 1);
            // 创建json对象
            JSONObject jsonObject = (JSONObject)JSONObject.parse(json);
            // 根据分析拿到放置信息的数组
            JSONArray array = jsonObject.getJSONArray("engine_search_result");
            // 判断数组中是否存在数据
            if (array.size() > 0){
                for (int i = 0; i < array.size(); i++){
                    //获取数组中的每一个对象
                    JSONObject arrObj = (JSONObject)array.get(i);
                    //把获取到的url地址放到任务队列中
                    String href = String.valueOf(arrObj.get("job_href"));
                    page.addTargetRequest(href);
                }
            }
            //获取下一页的地址
            String bkUrl = "https://search.51job.com/list/020000,000000,0000,00,9,99,java,2," + count + ".html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";
            page.addTargetRequest(bkUrl);
        }else {
            // 如果为空, 表示这是招聘详情页, 解析页面, 获取招聘详情信息, 保存数据
            this.saveJobInfo(page);
        }
    }

    // 解析页面, 获取招聘详情信息, 保存数据
    private void saveJobInfo(Page page) {
        //创建招聘详情对象
        JobInfo info = new JobInfo();
        //解析页面
        Html html = page.getHtml();
        String text = Jsoup.parse(html.css("p.msg.ltype").toString()).text();
        if (text == null) {
            // 有极少数非51内部网站无法找到
            return;
        }
        //公司名称
        String companyName = html.css("p.cname a", "text").nodes().get(0).toString().trim();
        info.setCompanyName(companyName);
        //公司地址
        String addr = html.css("div.bmsg > p.fp", "text").toString();
        info.setCompanyAddr(addr);
        //公司简介
        String companyInfo = html.css("div.tBorderTop_box > div.tmsg.inbox", "text").toString();
        info.setCompanyInfo(companyInfo);
        //职位名称
        String job = html.css("div.cn > h1", "text").toString();
        info.setJobName(job);
        //工作地址
        String jobAddr = Jsoup.parse(html.css("div.cn > p.msg").toString()).text();
        String sad = jobAddr.substring(0, jobAddr.indexOf("|")).trim();
        info.setJobAddr(sad);
        //职位信息
        String jobin = Jsoup.parse(html.css("div.job_msg").toString())
                .select("p").text();
        info.setJobInfo(jobin);
        //薪资范围
        String salaryText = html.css("div.in > div.cn strong", "text").toString();
        if (!StringUtils.isEmpty(salaryText)) {
            // 使用工具类转换薪资字符串
            Integer[] salary = MathSalary.getSalary(salaryText);
            int a = salary[0];
            int b = salary[1];
            info.setSalaryMax(a);
            info.setSalaryMin(b);
        } else {
            // 没有则设为零
            info.setSalaryMax(0);
            info.setSalaryMin(0);
        }
        //当前地址
        info.setUrl(page.getUrl().toString());
        //发布时间
        if (text.contains("|") && text.contains("发布")){
            String time = reTime(text);
            info.setTime(time);
        }
        //保存结果
        page.putField("jobInfo", info);
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

    // initialDelay当任务启动后, 等多久执行方法
    // fixedDelay每隔多久执行方法
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void process() {
        Spider.create(new JobProcessor())
                .addUrl(url)
                // 设置Secheduler
                .setScheduler(new QueueScheduler()
                // 设置Bloom去重
                .setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                // 设置自定义的Pipeline储存数据
                .addPipeline(this.jobPipeline)
                .run();
    }

    public String reTime(String text){
        String s2 = text.replace("|", "#").trim();
        String[] split = s2.split("#");
        String time1 = "";
        for (String sp : split){
            boolean b = sp.contains("发布");
            if (b){
                String s1 = sp.substring(0, sp.lastIndexOf("发"));
                char[] chars = s1.toCharArray();
                for (char s : chars){
                    String value = String.valueOf(s);
                    Pattern pattern = Pattern.compile("[0-9]*");
                    boolean bl = pattern.matcher(value).matches();
                    if (bl){
                        time1 = s1.substring(s1.indexOf(s), s1.length());
                        break;
                    }
                }
                break;
            }
        }
        String format = "";
        if (StringUtils.isEmpty(time1)){
            format = new SimpleDateFormat("yyyy").format(new Date());
            return format;
        }
        format = new SimpleDateFormat("yyyy-").format(new Date());
        return format + time1;
    }

}
