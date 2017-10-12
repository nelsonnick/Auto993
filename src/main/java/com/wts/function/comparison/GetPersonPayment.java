package com.wts.function.comparison;

import java.io.*;

import static com.wts.function.security.Security.getSecurityNumber;

public class GetPersonPayment {

  public static Boolean checkMonthA(String monthA, String monthB) {
    String mm = monthA.substring(0, 4) + monthA.substring(5, 7);
    if (Integer.parseInt(mm) <= Integer.parseInt(monthB)) {
      return true;
    } else {
      return false;
    }
  }

  public static Boolean checkMonthB(String monthA, String monthB) {
    String mm = monthA.substring(0, 4) + monthA.substring(5, 7);
    if (Integer.parseInt(mm) >= Integer.parseInt(monthB)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 分析公共业务子系统中的下载的TXT文档中每一行的信息
   *
   * @throws Exception
   */
  public static String analysisEachLine(String each_line, String checkMonth) throws Exception {
    String[] lineArr = each_line.split("\\t");
    if (lineArr.length > 0) {
      if (lineArr[1].equals("A")
              && checkMonthA(lineArr[2], checkMonth)
              && checkMonthB(lineArr[3], checkMonth)) {
        if (!lineArr[4].substring(0, 1).equals("-")) {
          if (lineArr[5].trim().equals("计划")) {
            return getSecurityNumber(lineArr[6].trim());
          } else {
            String[] month = lineArr[9].split(" ")[0].split("/");
            if (checkMonth.equals(month[0] + "." + month[1])) {
              return getSecurityNumber(lineArr[6].trim());
            } else {
              return "+"; //补缴
            }
          }
        } else {
          return "-"; //退款
        }
      } else {
        return "0"; //空白
      }
    } else {
      return "e"; //异常
    }
  }

  /**
   * 分析公共业务子系统中的下载的TXT文档
   *
   * @throws Exception
   */
  public static String analysisText(String result, String filename, String checkMonth) throws Exception {

    String pathname = "c:/" + result + "_社保下载数据/" + filename + ".txt";
    if (new File(pathname).exists()) {
      File file = new File(pathname);
      InputStreamReader isr = new InputStreamReader(
              new FileInputStream(file), "UTF-8");
      BufferedReader br = new BufferedReader(isr);
      String line = "";
      line = br.readLine();
      if (line == null) {
        System.out.println("缴费文件中无内容");
        return "缴费文件中无内容";
      }
      if (!line.split("\\t")[0].trim().equals("\uFEFFsblb")) {
        System.out.println("缴费文件内容有误");
        return "缴费文件内容有误";
      }
      String analysisText = "";
      while (line != null) {
        line = br.readLine(); // 一次读入一行数据
        if (line != null) {
          analysisText = analysisText + analysisEachLine(line, checkMonth);
        }
      }
      if (analysisText.contains("e")){
        return "数据异常";
      } else if (analysisText.contains("-")){
        return "缴费退款";
      } else if (analysisText.contains("+")){
        return "非当月补缴";
      } else if (analysisText.contains("1")){
        return "个人一险";
      } else if (analysisText.contains("2")){
        return "个人两险";
      } else if (analysisText.contains("5")){
        return "单位五险";
      } else {
        return "无缴费信息";
      }
    } else {
      return pathname + "文件不存在";
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println(analysisText("a", "370104196701121617秦岭", "200003"));
//    String each_line = "01\tA\t2000.01\t2000.01\t763.0\t计划  \t0010400208\t济南东风医药科技开发有限公司\t763.0\t \t";
//    System.out.println(analysisEachLine(each_line, "200001"));
  }
}
