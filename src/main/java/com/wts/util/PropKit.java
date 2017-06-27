package com.wts.util;

import java.io.*;
import java.util.Properties;


public class PropKit {
    /**
     * 加载属性文件
     * @param filePath 文件路径
     * @return
     */
    public static Properties loadProps(String filePath){
        Properties properties = new Properties();
        try {
            InputStream in =new BufferedInputStream(new FileInputStream(filePath));
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 读取配置文件
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties,String key){
        return properties.getProperty(key);
    }

    /**
     * 更新properties文件的键值对
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插入一对键值。
     *
     * @param keyname  键名
     * @param keyvalue 键值
     */
    public static void updateProperty(Properties properties,String filePath,String keyname,String keyvalue) {
        try {
            properties.setProperty(keyname, keyvalue);
            FileOutputStream outputFile = new FileOutputStream(filePath);
            properties.store(outputFile, null);
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String path = PropKit.class.getClassLoader().getResource("info.properties").getPath();
        Properties properties=loadProps(path);
        System.out.println(getString(properties,"passwd"));
        updateProperty(properties,path,"passwd","899");
        System.out.println(getString(properties,"passwd"));
    }
}
