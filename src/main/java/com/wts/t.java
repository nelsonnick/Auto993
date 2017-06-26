package com.wts;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;


public class t {

    private static void saveProperty() throws Exception {
        // 保存文件
        Properties propertie = new Properties();


        ClassLoader classLoader = t.class.getClassLoader();
        URL resource = classLoader.getResource("info.properties");
        String path = resource.getPath();
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file);
        propertie.load(fis);
        propertie.setProperty("passwd", "111111111");

        propertie.store(fos, "保存文件");

        fos.close();
    }


    public static void getProperty() {
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = t.class.getClassLoader();
            URL resource = classLoader.getResource("info.properties");
            String path = resource.getPath();
            InputStream in = classLoader.getResourceAsStream("info.properties");

            prop.load(in);     ///加载属性列表
            prop.setProperty("passwd", "11111");

            in.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        saveProperty();
    }
}
