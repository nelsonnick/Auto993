package com.wts.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class util {
    /**
     * 将形如20140625000000变成2014/06/25 00:00:00
     */
    public static String getTime(String str) {
        String y = str.substring(0, 4);
        String m;
        if (str.substring(4, 5).equals("0")) {
            m = str.substring(5, 6);
        } else {
            m = str.substring(4, 6);
        }
        String d = str.substring(6, 8);
        String h = str.substring(8, 10);
        String M = str.substring(10, 12);
        String s = str.substring(12, 14);
        return y + "/" + m + "/" + d + " " + h + ":" + M + ":" + s;
    }

    /**
     * 获取加密码
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 记录出现次数
     */
    public static int getCount(String str, String sub) {
        int index = 0;
        int count = 0;
        while ((index = str.indexOf(sub, index)) != -1) {
            index = index + sub.length();
            count++;
        }
        return count;
    }
    /**
     * 获取出现位置
     */
    public static int getCharacterPosition(String string, String substring, int count) {
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile(substring).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == count) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

}
