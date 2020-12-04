package com.example.excel.webMagic.task;

import com.example.excel.entity.Item;
import com.example.excel.service.ItemService;
import com.example.excel.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    public static final ObjectMapper MAPPER = new ObjectMapper();

    //间隔多长时间后执行下一次任务
//    @Scheduled(fixedDelay = 100 * 1000)
    public void item() throws Exception{
        //声明初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&page=";
        //按照页面对手机的搜索结果进行遍历
        for (int i = 1; i < 2; i = i + 2){
            String html = httpUtils.doGet(url + i);
            //解析页面，获取数据并保存
            this.parse(html);
        }
    }

    public void parse(String html) throws Exception{
        //解析html页面，获取Document对象
        Document doc = Jsoup.parse(html);
        //获取手机数据列表
        Elements spuEls = doc.select("div#J_goodsList > ul > li");
        for (Element spuEl : spuEls){
            Item item = new Item();
            //排除没有data-spu的值的广告
            if (!StringUtils.isEmpty(spuEl.attr("data-spu"))){
                //获取spu
                Long spu = Long.parseLong(spuEl.attr("data-spu"));
                //获取sku信息
                Elements skuEls = spuEl.select("li.ps-item");
                for (Element skuEl : skuEls){
                    item.setSpu(spu);
                    //获取sku
                    Long sku = Long.parseLong(skuEl.select("img[data-sku]").first().attr("data-sku"));
                    item.setSku(sku);
                    //根据spu sku查询商品数据，存在则跳过
                    List<Item> items = itemService.select(item);
                    if (items.size() > 0){
                        continue;
                    }
                    //商品详情地址
                    String itemUrl = "https://item.jd.com/" + spu + ".html";
                    item.setUrl(itemUrl);
                    //获取标题
                    String titleHtml = httpUtils.doGet(itemUrl);
                    //有坑    未解决
                    String title = Jsoup.parse(titleHtml).select("div#itemName").text();
                    item.setTitle(title);
                    //获取价格
                    String priceJson = httpUtils.doGet("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                    double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                    item.setPrice(price);
                    //商品图片
                    String image = skuEl.select("img[data-sku]").attr("data-lazy-img");
                    String picUrl = "http:" + image.replace("/n7/", "/n1/");
                    //下载图片
                    String picName = httpUtils.doGetImage(picUrl);
                    item.setPic(picName);
                    //创建时间
                    item.setCreated(LocalDateTime.now());
                    //更新时间
                    item.setUpdated(new Date());
                    //存储数据
                    int i = itemService.save(item);
                    if (i > 0){
                        System.out.println("存入成功");
                    }
                }
            }

        }

    }
}
