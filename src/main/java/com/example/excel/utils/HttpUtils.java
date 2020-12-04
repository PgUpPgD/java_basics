package com.example.excel.utils;

import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Component
public class HttpUtils {
    private PoolingHttpClientConnectionManager cm;

    public HttpUtils(){
        this.cm = new PoolingHttpClientConnectionManager();
        this.cm.setMaxTotal(100);      //最大连接数
        this.cm.setDefaultMaxPerRoute(10);      //单个主机最大连接数
    }

    //加载页面
    public String doGet(String url){
        //创建连接池管理器
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getConfig());
        setHeaders(httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                if (!StringUtils.isEmpty(entity)){
                    return EntityUtils.toString(entity, "utf8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    //下载图片
    public String doGetImage(String url){
        //创建连接池管理器
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getConfig());
        setHeaders(httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                if (!StringUtils.isEmpty(entity)){
                    //获取后缀
                    String index = url.substring(url.lastIndexOf("."));
                    //创建图片名
                    String picName = reName() + index;
                    //下载
                    OutputStream stream = new FileOutputStream(new File("F:/image/" + picName));
                    response.getEntity().writeTo(stream);
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    //配置请求信息
    public static RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)  //设置创建连接超时时间
                .setConnectionRequestTimeout(500)   //获取连接超时时间
                .setSocketTimeout(5 * 1000)     //数据传输时间
                .build();
        return config;
    }

    public static String reName(){
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String s = month + "" +  day;
        String uuid = UUID.randomUUID().toString();
        return s + uuid.substring(0,uuid.indexOf("-"));
    }

    //爬取数据时伪装成用户
    public void setHeaders(HttpGet httpGet){
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
    }

    public static void main(String[] args) {
        HttpUtils utils = new HttpUtils();
        String itemInfo = utils.doGet("https://item.jd.com/100009082466.html");
        String title = Jsoup.parse(itemInfo).select("div#itemName").text();
        System.out.println("*************" + title);
    }

}
