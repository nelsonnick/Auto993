package com.wts.service;

import com.wts.util.util;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;


public class common {
    /**
     * 登录
     */
    public static CloseableHttpClient login(String userid, String passwd) throws Exception {
        URI u = new URIBuilder()
                .setScheme("http")
                .setHost("10.153.50.108:7001")
                .setPath("/lemis3/logon.do")
                .setParameter("method", "doLogon")
                .setParameter("userid", userid)
                .setParameter("passwd", util.getMD5(passwd))
                .setParameter("userLogSign", "0")
                .setParameter("passWordLogSign", "0")
                .setParameter("screenHeight", "768")
                .setParameter("screenWidth", "1024")
                .setParameter("mode", "")
                .build();
        HttpPost post = new HttpPost(u);
        // 创建默认的httpClient实例.
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            System.out.println("是否成功登录数据库: " + EntityUtils.toString(entity, "UTF-8"));
        }
        Thread.sleep(1000);
        System.out.println("登录....");
        return client;
    }

}
