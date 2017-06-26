package com.wts;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;


public class t {
  public static void main(String[] args) {
    Properties prop = new Properties();
    try {
      InputStream is = t.class.getClassLoader().getResourceAsStream("info.properties");
      InputStream in = new BufferedInputStream(new FileInputStream("resources/info.properties"));
      prop.load(in);     ///加载属性列表
      Iterator<String> it = prop.stringPropertyNames().iterator();
      while (it.hasNext()) {
        String key = it.next();
        System.out.println(key + ":" + prop.getProperty(key));
      }
      in.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
