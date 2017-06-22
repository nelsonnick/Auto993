package com.wts.util;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPKit {
    /**
     * 设置本地链接的IP为10.153.73.166
     */
    public static void IPset() {
        try {
            Runtime.getRuntime().exec("netsh    interface    ip    set    addr    \"本地连接\"    static    10.153.73.166    255.255.255.0     10.153.73.254     ");
        } catch (IOException e) {
            System.out.println("无法设置本地链接的IP为10.153.73.166");
        }
    }

    /**
     * 设置本地链接的IP为自动获取
     */
    public static void IPback() {
        try {
            Runtime.getRuntime().exec("netsh    interface    ip    set    address name = \"本地连接\"    source = dhcp");
        } catch (IOException e) {
            System.out.println("无法设置本地链接的IP为自动获取");
        }
    }

    /**
     * 获取本地链接的IP地址
     */
    public static String IPget() {
        String Ip = null;
        try {
            Ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Ip;
    }
}
