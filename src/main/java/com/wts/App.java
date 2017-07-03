package com.wts;


import com.wts.function.Input;
import com.wts.function.Settings;
import com.wts.util.PropKit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import static com.wts.util.PropKit.getString;
import static com.wts.util.PropKit.loadProps;

public class App {
  public static void main(String[] args) throws Exception {
    Properties properties = loadProps(PropKit.class.getClassLoader().getResource("info.properties").getPath());
    System.out.println("                        欢迎使用自动993程序");
    System.out.println(" ");
    System.out.println("993当前用户名：" + getString(properties, "userid") + "   密码：" + getString(properties, "passwd"));
    System.out.println(" ");
    System.out.println("本程序提供如下功能：");
    System.out.println(" ");
    System.out.println("0、修改用户名和密码");
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
      System.out.print("请输入数字0到数字7，并按回车键确认：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!result.equals("1")
            && !result.equals("2")
            && !result.equals("3")
            && !result.equals("4")
            && !result.equals("5")
            && !result.equals("6")
            && !result.equals("7")
            && !result.equals("0")); // 当用户输入无效的时候，反复提示要求用户输入
    switch (result) {
      case "1":
        com.wts.function.commerce.GetBy993.get();
        break;
      case "2":
        com.wts.function.commerce.Jnjgfw.download();
        break;
      case "3":
        com.wts.function.commerce.Jnjgfw.analysis();
        break;
      case "4":
        com.wts.function.security.GetBy993.get();
        break;
      case "5":
        com.wts.function.security.Ggywzxt.download();
        break;
      case "6":
        com.wts.function.security.Ggywzxt.analysis();
        break;
      case "7":
        Input.input();
        break;
      case "0":
        Settings.change();
        break;
      default:
        System.out.println("请输入数字0到数字7，并按回车键确认：");
    }

  }
}
