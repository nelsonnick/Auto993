package com.wts;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
  public static void main(String[] args) throws Exception {
    System.out.println("                        欢迎使用自动993程序");
    System.out.println(" ");
    System.out.println("请选择要进行的操作，然后按回车键确认");
    System.out.println(" ");
    System.out.println("1、核查工商信息--内网");
    System.out.println("2、下载工商信息--外网");
    System.out.println("3、分析工商信息--外网");
    System.out.println("4、核查社保信息--内网");
    System.out.println("5、下载社保信息--内网");
    System.out.println("6、分析社保信息--内网");
    System.out.println("7、自动录入补贴--内网");
    System.out.println(" ");
    String result;
    do {
      // 输出提示文字
      System.out.print("请输入数字1到数字7：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!result.equals("1")
            && !result.equals("2")
            && !result.equals("3")
            && !result.equals("4")
            && !result.equals("5")
            && !result.equals("6")
            && !result.equals("7")); // 当用户输入无效的时候，反复提示要求用户输入
    if (result.equals("1")) {
      com.wts.check.commerce.GetBy993.get();
    } else if (result.equals("2")) {
      com.wts.check.commerce.Jnjgfw.download();
    } else if (result.equals("3")) {
      com.wts.check.commerce.Jnjgfw.analysis();
    } else if (result.equals("4")) {
      com.wts.check.security.GetBy993.get();
    } else if (result.equals("5")) {
      com.wts.check.security.Ggywzxt.download();
    } else if (result.equals("6")) {
      com.wts.check.security.Ggywzxt.analysis();
    } else if (result.equals("7")) {
      ;
    }
  }
}
