package com.wts.function;

import com.wts.util.PropKit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import static com.wts.util.PropKit.getString;
import static com.wts.util.PropKit.loadProps;
import static com.wts.util.PropKit.updateProperty;

public class Settings {
  public static void change() throws Exception {
    Runtime.getRuntime().exec("cls");
    String path = PropKit.class.getClassLoader().getResource("info.properties").getPath();
    Properties properties=loadProps(path);
    System.out.println("                 0、修改用户名和密码             ");
    System.out.println(" ");
    System.out.println("993当前用户名：" + getString(properties, "userid") + "   密码：" + getString(properties, "passwd"));
    System.out.println(" ");
    String userid,passwd;
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

    updateProperty(properties,path,"userid",userid);
    updateProperty(properties,path,"passwd",passwd);
    System.out.println("  ");
    System.out.println("修改完成，按回车键退出程序");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }
  }
}
