package com.wts;


import com.wts.service.Common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
  public static void main(String[] args) throws Exception {
    System.out.println(" ");
    System.out.println("                        欢迎使用自动993_v3.4版");
    System.out.println(" ");
    String userid, passwd;
    do {
      System.out.print("请输入您在993系统中的用户名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      userid = new BufferedReader(is_reader).readLine();
    } while (userid.equals(""));
    do {
      System.out.print("请输入您在993系统中的密码：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      passwd = new BufferedReader(is_reader).readLine();
    } while (passwd.equals(""));
    Common.userid = userid;
    Common.passwd = passwd;


    System.out.println("993当前用户名：" + Common.userid + "   密码：" + Common.passwd);
    System.out.println(" ");
    System.out.println("本程序提供目前提供的功能有：");
    System.out.println(" ");
    System.out.println("1、核查工商信息--内网--993");
//    System.out.println("2、下载工商信息--外网");
//    System.out.println("3、分析工商信息");
    System.out.println("2、核查社保信息--内网--993");
    System.out.println("3、下载社保信息--内网--公共业务子系统");
    System.out.println("4、分析第3项下载社保信息");
//    System.out.println("7、自动录入灵活就业补贴（自行提供人员信息）--内网");
//    System.out.println("8、自动录入灵活就业补贴（自动下载人员信息）--内网");
//    System.out.println("9、自动录入公益岗位补贴（自行提供人员信息）--内网");
//    System.out.println("0、自动录入公益岗位补贴（自动下载人员信息）--内网");
//    System.out.println("a、比对社保数据");
    System.out.println(" ");
    String result;
    do {
      // 输出提示文字
      System.out.print("请输入数字1到数字4，并按回车键确认：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!result.equals("1")
            && !result.equals("2")
            && !result.equals("3")
            && !result.equals("4")); // 当用户输入无效的时候，反复提示要求用户输入
    switch (result) {
      case "1":
        com.wts.function.commerce.GetBy993.get();
        break;
      case "2":
        com.wts.function.security.GetBy993.get();
        break;
      case "3":
        com.wts.function.security.Ggywzxt.download();
        break;
      case "4":
        com.wts.function.security.Ggywzxt.analysis();
        break;
//      case "2":
//        com.wts.function.commerce.Jnjgfw.download();
//        break;
//      case "3":
//        com.wts.function.commerce.Jnjgfw.analysis();
//        break;
//      case "4":
//        com.wts.function.security.GetBy993.get();
//        break;
//      case "5":
//        com.wts.function.security.Ggywzxt.download();
//        break;
//      case "6":
//        com.wts.function.security.Ggywzxt.analysis();
//        break;
//      case "7":
//        com.wts.function.Input.inputLH();
//        break;
//      case "8":
//        com.wts.function.Input.inputLHBy993();
//        break;
//      case "9":
//        com.wts.function.Input.inputGG();
//        break;
//      case "0":
//        com.wts.function.Input.inputGGBy993();
//        break;
//      case "a":
//        com.wts.function.security.Check.goCheck();
//        break;
      default:
        System.out.println("请输入数字1到字母4，并按回车键确认：");
    }

  }
}
