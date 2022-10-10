package com.amp.utils;

import io.micrometer.core.lang.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * http请求工具类
 *
 * @ClassName HttpClientUtil
 * @Author Emi.Wu
 * @Date 2022-06-27 15:15
 * @Version 1.0
 */
public class HttpClientUtil {

    /**
     * 编码格式
     */
    private final static String UTF_CODE = "utf-8";

    /**
     * 响应成功状态编码
     */
    private final static int SUCCESS_STATUS = 200;

    /**
     * get请求
     *
     * @param url            路径
     * @param paramMap       参数
     * @param connectTimeout 连接超时时长  单位：秒
     * @param requestTimeout 请求超时时长  单位：秒
     * @param headerMap      请求头 可以不设置
     * @return 响应json字符串
     * @author Emil.Wu
     * @date 2022-06-27 15:10
     * @version 1.0
     */
    public static String doGet(String url,@Nullable Map<String, String> paramMap, int connectTimeout, int requestTimeout, @Nullable Map<String, String> headerMap) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (ObjectUtils.isNotEmpty(paramMap)) {
                paramMap.keySet().forEach(key -> builder.addParameter(key, paramMap.get(key)));
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (ObjectUtils.isNotEmpty(headerMap)) {
                headerMap.keySet().forEach(key -> httpGet.addHeader(key, headerMap.get(key)));
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout * 1000).setConnectionRequestTimeout(requestTimeout * 1000).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == SUCCESS_STATUS) {
                resultString = EntityUtils.toString(response.getEntity(), UTF_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * post 请求,参数为json
     *
     * @param url            路径
     * @param paramMap       参数
     * @param connectTimeout 连接超时时长  单位：秒
     * @param requestTimeout 请求超时时长  单位：秒
     * @param headerMap      请求头 可以不设置
     * @return 响应json字符串
     */
    public static String doPost(String url, @Nullable Map<String, String> paramMap, int connectTimeout, int requestTimeout, @Nullable Map<String, String> headerMap) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if (ObjectUtils.isNotEmpty(headerMap)) {
                headerMap.keySet().forEach(key -> httpPost.addHeader(key, headerMap.get(key)));
            }
            // 设置5秒未请求到对方服务即提示，请求超时时间1分钟，socket超时时间2分钟
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout * 1000).setConnectionRequestTimeout(requestTimeout * 1000).build();
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (ObjectUtils.isNotEmpty(paramMap)) {
                List<BasicNameValuePair> paramList = paramMap.keySet().stream().map(key -> new BasicNameValuePair(key, paramMap.get(key))).collect(Collectors.toList());
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, UTF_CODE);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == SUCCESS_STATUS) {
                resultString = EntityUtils.toString(response.getEntity(), UTF_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
