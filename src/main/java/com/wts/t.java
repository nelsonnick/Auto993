package com.wts;


import org.apache.http.impl.client.CloseableHttpClient;

import static com.wts.check.commerce.getCommerce;
import static com.wts.service.common.login;

public class t {
  public static void main(String[] args) throws Exception{
    CloseableHttpClient client= login("hyddj","888888");
    System.out.println(getCommerce(client,"370104610705133"));
  }
}
