package com.wts.function.security;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import static com.wts.function.security.Security.getSecurityType;
import static com.wts.util.Kit.copyFile;

/**
 * 公共业务子系统
 */
public class Ggywzxt {
  /**
   * 分析公共业务子系统中的下载的TXT文档
   *
   * @throws Exception
   */
  public static void analysis() throws Exception {

    System.out.println(" ");
    System.out.println("               6、分析社保信息         ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先运行下载社保信息程序（功能5）！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：用于分析本程序从《公共业务子系统》中下载的TXT文件！");
    System.out.println("2：用于分析制定月份的社保缴纳情况！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");
    String result, months, month;
    do {
      // 输出提示文字
      System.out.print("请输入待查的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists()); // 当用户输入无效的时候，反复提示要求用户输入
    do {
      // 输出提示文字
      System.out.print("请输入要查询的6位年月：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      months = new BufferedReader(is_reader).readLine();
    } while (!months.matches("\\d{6}")); // 当用户输入无效的时候，反复提示要求用户输入

    month = months.substring(0, 4) + '.' + months.substring(4, 6);

    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
    int count = sheetBefore.getRow(0).getPhysicalNumberOfCells();
    int totals = sheetBefore.getLastRowNum();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("sheet1");
    XSSFRow row = sheet.createRow(0);
    row.createCell(0).setCellValue("证件号码");
    row.createCell(1).setCellValue("人员姓名");
    row.createCell(2).setCellValue("缴费年月");
    row.createCell(3).setCellValue("单位编号");
    row.createCell(4).setCellValue("单位名称");
    row.createCell(5).setCellValue("缴费性质");
    row.createCell(6).setCellValue("缴费情况");
    row.createCell(7).setCellValue("缴费时间");
    for (int k = 1; k < totals + 1; k++) {
      String id = sheetBefore.getRow(k).getCell(0).getStringCellValue();
      String name = sheetBefore.getRow(k).getCell(1).getStringCellValue();

      String fileName = "c:/" + result + "_社保下载数据/" + id + name + ".txt";
      File f = new File(fileName);
      if (!f.exists()) {
        int total = sheet.getLastRowNum();
        XSSFRow newRow = sheet.createRow(total + 1);
        newRow.createCell(0).setCellValue(id);
        newRow.createCell(1).setCellValue(name);
        newRow.createCell(2).setCellValue(month);
        newRow.createCell(3).setCellValue("无下载信息");
        newRow.createCell(4).setCellValue("无下载信息");
        newRow.createCell(5).setCellValue("无下载信息");
        newRow.createCell(6).setCellValue("无下载信息");
        newRow.createCell(7).setCellValue("无下载信息");
      } else {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String lineTxt = null;

        String xzbz = "", qsny = "", zzny = "", jfjs = "", zdlsh = "", dwbh = "", dwmc = "", dwjfjs = "", qrsj = "", aa = "", bb = "";
        while ((lineTxt = reader.readLine()) != null) {
          String[] msg = lineTxt.split("\\t");
          if (msg[1].equals("A")
                  && Double.parseDouble(msg[2]) <= Double.parseDouble(month)
                  && Double.parseDouble(msg[3]) >= Double.parseDouble(month)
                  ) {
            xzbz = msg[1];
            qsny = msg[2];
            zzny = msg[3];
            jfjs = msg[4];
            zdlsh = msg[5];
            dwbh = msg[6];
            dwmc = msg[7];
            dwjfjs = msg[8];
            qrsj = msg[9];
          }
        }
        if (!dwbh.equals("")) {
          if (zdlsh.equals("计划  ")) {
            aa = "正常缴费";
            bb = "当月缴费";
          } else if (zdlsh.trim().equals("未填单据")) {
            aa = "未交费";
            bb = "开出单据未缴费";
          } else {
            aa = "补缴";
            bb = qrsj;
          }
          int total = sheet.getLastRowNum();
          XSSFRow newRow = sheet.createRow(total + 1);
          newRow.createCell(0).setCellValue(id);
          newRow.createCell(1).setCellValue(name);
          newRow.createCell(2).setCellValue(month);
          newRow.createCell(3).setCellValue(dwbh);
          newRow.createCell(4).setCellValue(dwmc);
          newRow.createCell(5).setCellValue(getSecurityType(dwbh));
          newRow.createCell(6).setCellValue(aa);
          newRow.createCell(7).setCellValue(bb);
        } else {
          int total = sheet.getLastRowNum();
          XSSFRow newRow = sheet.createRow(total + 1);
          newRow.createCell(0).setCellValue(id);
          newRow.createCell(1).setCellValue(name);
          newRow.createCell(2).setCellValue(month);
          newRow.createCell(3).setCellValue("无养老缴费记录");
          newRow.createCell(4).setCellValue("无养老缴费记录");
          newRow.createCell(5).setCellValue("无养老缴费记录");
          newRow.createCell(6).setCellValue("无养老缴费记录");
          newRow.createCell(7).setCellValue("无养老缴费记录");
        }
      }
    }
    FileOutputStream os = new FileOutputStream("c:/" + result + "_社保下载数据/结果" + months + ".xlsx");
    workbook.write(os);
    os.close();

    System.out.println("  ");
    System.out.println("  ");
    System.out.println("数据分析完成！");
    System.out.println("请查看文件--> c:/" + result + "_社保下载数据/结果" + months + ".xlsx");
    System.out.println("  ");
    System.out.println("按回车键退出程序...");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }
  }

  /**
   * 从公共业务子系统中的下载指定人员信息并生产TXT文档
   *
   * @throws Exception
   */
  public static void download() throws Exception {

    System.out.println(" ");
    System.out.println("               5、下载社保信息         ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先切换到社保内网环境！");
    System.out.println("2：待查的Excel文件必须放置在C盘根目录下！");
    System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
    System.out.println("4：Excel文件第一行为标题，如果没有可以不填写！");
    System.out.println("5：Excel文件第一列内容必须为身份证号码！");
    System.out.println("6：Excel文件第二列内容必须为姓名，且不含有空格！");
    System.out.println("7：Excel文件中的信息必须完整，即不能有空白的单元格！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：下载结果跟公共业务子系统中的社保下载结果保持一致！");
    System.out.println("2：下载结果为TXT文本文件！");
    System.out.println("3：下载结果可采用前期开发的VB程序进行分析！");
    System.out.println("4：下载过程采用多线程，多核CPU有额外优势！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");
    String result;
    do {
      // 输出提示文字
      System.out.print("请输入待下载的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists()); // 当用户输入无效的时候，反复提示要求用户输入

    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
    int total = sheetBefore.getLastRowNum() + 1;
    System.out.println("总人数：" + total);
    int total1, total2, total3, total4, total5, total6, total7;
    if (total <= 8) {
      total1 = total;
      total2 = 0;
      total3 = 0;
      total4 = 0;
      total5 = 0;
      total6 = 0;
      total7 = 0;
    } else {
      total1 = (int) Math.ceil(total / 8) + 1;
      total2 = (int) Math.ceil(total / 8) * 2 + 1;
      total3 = (int) Math.ceil(total / 8) * 3 + 1;
      total4 = (int) Math.ceil(total / 8) * 4 + 1;
      total5 = (int) Math.ceil(total / 8) * 5 + 1;
      total6 = (int) Math.ceil(total / 8) * 6 + 1;
      total7 = (int) Math.ceil(total / 8) * 7 + 1;
    }

    //  创建文件夹
    File files = new File("C:/" + result + "_社保下载数据");
    files.mkdir();
    copyFile("c:\\" + result + ".xlsx", "c:/" + result + "_社保下载数据/" + result + ".xlsx");
    if (total > 8) {
      Thread t1 = new Thread(result, 1, total1, sheetBefore);
      Thread t2 = new Thread(result, total1, total2, sheetBefore);
      Thread t3 = new Thread(result, total2, total3, sheetBefore);
      Thread t4 = new Thread(result, total3, total4, sheetBefore);
      Thread t5 = new Thread(result, total4, total5, sheetBefore);
      Thread t6 = new Thread(result, total5, total6, sheetBefore);
      Thread t7 = new Thread(result, total6, total7, sheetBefore);
      Thread t8 = new Thread(result, total7, total + 2, sheetBefore);
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
      t6.start();
      t7.start();
      t8.start();
      do {
      } while (!(t1.exit && t2.exit && t3.exit && t4.exit && t5.exit && t6.exit && t7.exit && t8.exit));
    } else {
      Thread t = new Thread(result, 1, total, sheetBefore);
      t.start();
      do {
      } while (!t.exit);
    }

    System.out.println("  ");
    System.out.println("  ");
    System.out.println("请查看下载后的文件--> c:/" + result + "_社保下载数据/");
    System.out.println("  ");
    System.out.println("按回车键退出程序...");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }

  }

}
