package com.wts.function;

import com.wts.entity.PersonGG;
import com.wts.entity.PersonLH;
import com.wts.entity.PersonQY;
import com.wts.service.Common;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import static com.wts.service.Common.login;
import static com.wts.util.Export.*;
import static com.wts.util.Import.ImportGG;
import static com.wts.util.Import.ImportLH;
import static com.wts.util.Import.ImportQY;

public class Input {

  public static void inputQY() throws Exception {
    System.out.println(" ");
    System.out.println("                 8、自动录入补贴（企业吸纳）        ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先切换到社保内网环境！");
    System.out.println("2：待录入的Excel文件必须放置在C盘根目录下！");
    System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
    System.out.println("4：Excel文件第一行为标题，如果没有可以不填写！");
    System.out.println("5：Excel文件第一列内容必须为身份证号码！");
    System.out.println("6：Excel文件第二列内容必须为姓名，且不含有空格！");
    System.out.println("7：Excel文件中的信息必须完整，即不能有空白的单元格！");
    System.out.println("8：单次录入人数不要超过300人，否则后期录入速度会非常慢！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：录入时会根据指定的起始年月和终止年月进行逐一录入！");
    System.out.println("2：录入时会生成当前年月录入结果的Excel表格！");
    System.out.println("3：录入完成后，可通过每月的表格观察录入情况！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");

    String result, qsny, zzny;
    do {
      System.out.print("请输入待录入的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists());
    do {
      System.out.print("请输入要录入的6位起始年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      qsny = new BufferedReader(is_reader).readLine();
    } while (!qsny.matches("\\d{6}"));

    do {
      System.out.print("请输入要录入的6位终止年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      zzny = new BufferedReader(is_reader).readLine();
    } while (!zzny.matches("\\d{6}") && Integer.parseInt(qsny) > Integer.parseInt(zzny));

    CloseableHttpClient client = login(Common.userid, Common.passwd);
    List<PersonQY> persons = ImportQY(result);
    ExportQYResult(client, persons, qsny, zzny);
  }

  public static void inputGG() throws Exception {
    System.out.println(" ");
    System.out.println("                 8、自动录入补贴（公益岗位）        ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先切换到社保内网环境！");
    System.out.println("2：待录入的Excel文件必须放置在C盘根目录下！");
    System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
    System.out.println("4：Excel文件第一行为标题，如果没有可以不填写！");
    System.out.println("5：Excel文件第一列内容必须为身份证号码！");
    System.out.println("6：Excel文件第二列内容必须为姓名，且不含有空格！");
    System.out.println("7：Excel文件中的信息必须完整，即不能有空白的单元格！");
    System.out.println("8：单次录入人数不要超过300人，否则后期录入速度会非常慢！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：录入时会根据指定的起始年月和终止年月进行逐一录入！");
    System.out.println("2：录入时会生成当前年月录入结果的Excel表格！");
    System.out.println("3：录入完成后，可通过每月的表格观察录入情况！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");

    String result, qsny, zzny;
    do {
      System.out.print("请输入待录入的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists());
    do {
      System.out.print("请输入要录入的6位起始年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      qsny = new BufferedReader(is_reader).readLine();
    } while (!qsny.matches("\\d{6}"));

    do {
      System.out.print("请输入要录入的6位终止年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      zzny = new BufferedReader(is_reader).readLine();
    } while (!zzny.matches("\\d{6}") && Integer.parseInt(qsny) > Integer.parseInt(zzny));

    CloseableHttpClient client = login(Common.userid, Common.passwd);
    List<PersonGG> persons = ImportGG(result);
    ExportGGResult(client, persons, qsny, zzny);
  }

  public static void inputLH() throws Exception {
    System.out.println(" ");
    System.out.println("                 7、自动录入补贴（灵活就业）        ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先切换到社保内网环境！");
    System.out.println("2：待录入的Excel文件必须放置在C盘根目录下！");
    System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
    System.out.println("4：Excel文件第一行为标题，如果没有可以不填写！");
    System.out.println("5：Excel文件第一列内容必须为身份证号码！");
    System.out.println("6：Excel文件第二列内容必须为姓名，且不含有空格！");
    System.out.println("7：Excel文件中的信息必须完整，即不能有空白的单元格！");
    System.out.println("8：单次录入人数不要超过300人，否则后期录入速度会非常慢！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：录入时会根据指定的起始年月和终止年月进行逐一录入！");
    System.out.println("2：录入时会生成当前年月录入结果的Excel表格！");
    System.out.println("3：录入完成后，可通过每月的表格观察录入情况！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");

    String result, qsny, zzny;
    do {
      System.out.print("请输入待录入的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists());
    do {
      System.out.print("请输入要录入的6位起始年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      qsny = new BufferedReader(is_reader).readLine();
    } while (!qsny.matches("\\d{6}"));

    do {
      System.out.print("请输入要录入的6位终止年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      zzny = new BufferedReader(is_reader).readLine();
    } while (!zzny.matches("\\d{6}"));

    if (Integer.parseInt(qsny) > Integer.parseInt(zzny)) {
      System.out.print("起始年月" + qsny + "不能晚于终止年月" + zzny + "！");
      System.out.print("按回车关闭程序...");
      while (true) {
        if (System.in.read() == '\n')
          System.exit(0);
      }
    }
    CloseableHttpClient client = login(Common.userid, Common.passwd);
    List<PersonLH> persons = ImportLH(result);
    ExportLHResult(client, persons, qsny, zzny);
  }

}
