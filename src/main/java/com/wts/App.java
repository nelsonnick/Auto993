package com.wts;


import org.apache.http.impl.client.CloseableHttpClient;

import static com.wts.action.common.login;
import static com.wts.action.qy.goPersonQYs;

public class App {
    public static void main(String[] args) throws Exception{
//        CloseableHttpClient client = login("hyzt", "7957908");
//        System.out.println(goPersonQYs(client));
        String qsny="20140625000000".substring(0,6);
        System.out.println(qsny);
    }
}
