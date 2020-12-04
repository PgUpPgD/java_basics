package com.example.excel.webMagic.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpConfigTest {

    public static void main(String[] args) throws Exception {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求地址是; http://yun.itheima.com/search?keys=Java
        //创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        //多个参数继续.setParameter
        uriBuilder.setParameter("keys", "Java");

        //创建HttpGet对象，设置访问地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        System.out.println("httpGet:" + httpGet);

        //配置请求信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)   //创建连接的最大时间 单位：毫秒
                .setConnectionRequestTimeout(500)   //设置获取连接的最长时间 单位：毫秒
                .setSocketTimeout(10 * 1000)    //设置数据传输的最长时间
                .build();
        //给请求设置请求信息
        httpGet.setConfig(config);

        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求，获取response
            response = httpClient.execute(httpGet);
            //解析响应数据
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭response
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //关闭浏览器
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
