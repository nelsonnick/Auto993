package com.wts;


import org.apache.http.impl.client.CloseableHttpClient;

import static com.wts.service.common.login;
import static com.wts.service.qy.*;

public class App {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient client = login("hyzt", "7957908");
        String tableMark = openWindow(client);
        String page = getTotal(client,tableMark);
        System.out.println(getPersonAll(client,tableMark,Integer.parseInt(page)));

    }
}
