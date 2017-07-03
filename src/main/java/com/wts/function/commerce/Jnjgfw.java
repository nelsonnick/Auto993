package com.wts.function.commerce;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import static com.wts.util.Kit.copyFile;

/**
 * http://www.jnjgfw.gov.cn/
 * 济南市事中事后监管服务系统
 */
public class Jnjgfw {

  /**
   * 分析从《济南市事中事后监管服务系统》中下载的TXT文件
   *
   * @throws Exception
   */
  public static void analysis() throws Exception {

    System.out.println("               3、分析工商信息         ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先运行下载工商信息程序（功能2）！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：用于分析本程序从《济南市事中事后监管服务系统》中下载的TXT文件！");
    System.out.println("2：Excel文件第一列为身份证号码，第二列内容必须为人员姓名！");
    System.out.println("3：分析结果中不含有联系电话等信息，如有需要，请通过993二次核查！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");
    String result;
    do {
      // 输出提示文字
      System.out.print("请输入待查的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists()); // 当用户输入无效的时候，反复提示要求用户输入

    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
    int count = sheetBefore.getRow(0).getPhysicalNumberOfCells();
    int totals = sheetBefore.getLastRowNum();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("sheet1");
    XSSFRow row = sheet.createRow(0);
    row.createCell(0).setCellValue("证件号码");
    row.createCell(1).setCellValue("人员姓名");
    row.createCell(2).setCellValue("企业名称");
    row.createCell(3).setCellValue("信用代码");
    row.createCell(4).setCellValue("营业执照");
    row.createCell(5).setCellValue("类型");
    row.createCell(6).setCellValue("经营者");
    row.createCell(7).setCellValue("注册日期");
    row.createCell(8).setCellValue("核准日期");
    row.createCell(9).setCellValue("营业期限自");
    row.createCell(10).setCellValue("营业期限至");
    row.createCell(11).setCellValue("登记机关");
    row.createCell(12).setCellValue("登记状态");
    row.createCell(13).setCellValue("注册资本");
    row.createCell(14).setCellValue("住所");
    row.createCell(15).setCellValue("经营场所");
    row.createCell(16).setCellValue("经营范围");
    for (int k = 1; k < totals + 1; k++) {
      String id = sheetBefore.getRow(k).getCell(0).getStringCellValue();
      String name = sheetBefore.getRow(k).getCell(1).getStringCellValue();

      String fileName = "c:/" + result + "_工商下载数据/" + id + name + ".txt";
      File f = new File(fileName);
      if (!f.exists()) {
        int total = sheet.getLastRowNum();
        XSSFRow newRow = sheet.createRow(total + 1);
        newRow.createCell(0).setCellValue(id);
        newRow.createCell(1).setCellValue(name);
        newRow.createCell(2).setCellValue("无下载信息");
        newRow.createCell(3).setCellValue("无下载信息");
        newRow.createCell(4).setCellValue("无下载信息");
        newRow.createCell(5).setCellValue("无下载信息");
        newRow.createCell(6).setCellValue("无下载信息");
        newRow.createCell(7).setCellValue("无下载信息");
        newRow.createCell(8).setCellValue("无下载信息");
        newRow.createCell(9).setCellValue("无下载信息");
        newRow.createCell(10).setCellValue("无下载信息");
        newRow.createCell(11).setCellValue("无下载信息");
        newRow.createCell(12).setCellValue("无下载信息");
        newRow.createCell(13).setCellValue("无下载信息");
        newRow.createCell(14).setCellValue("无下载信息");
        newRow.createCell(15).setCellValue("无下载信息");
        newRow.createCell(16).setCellValue("无下载信息");
      } else {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String lineTxt = null;
        String qymc = "", xydm = "", yyzz = "", lx = "", jyz = "", zcrq = "", hzrq = "", yyqx1 = "", yyqx2 = "", djjg = "", djzt = "", zczb = "", zs = "", jycs = "", jyfw = "";
        while ((lineTxt = reader.readLine()) != null) {
          String[] msg = lineTxt.split("\\t");
          int total = sheet.getLastRowNum();
          XSSFRow newRow = sheet.createRow(total + 1);
          newRow.createCell(0).setCellValue(id);
          newRow.createCell(1).setCellValue(name);
          newRow.createCell(2).setCellValue(msg[0]);
          newRow.createCell(3).setCellValue(msg[1]);
          newRow.createCell(4).setCellValue(msg[2]);
          newRow.createCell(5).setCellValue(msg[3]);
          newRow.createCell(6).setCellValue(msg[4]);
          newRow.createCell(7).setCellValue(msg[5]);
          newRow.createCell(8).setCellValue(msg[6]);
          newRow.createCell(9).setCellValue(msg[7]);
          newRow.createCell(10).setCellValue(msg[8]);
          newRow.createCell(11).setCellValue(msg[9]);
          newRow.createCell(12).setCellValue(msg[10]);
          newRow.createCell(13).setCellValue(msg[11]);
          newRow.createCell(14).setCellValue(msg[12]);
          newRow.createCell(15).setCellValue(msg[13]);
          newRow.createCell(16).setCellValue(msg[14]);
        }
      }
    }
    FileOutputStream os = new FileOutputStream("c:/" + result + "_工商下载数据/结果.xlsx");
    workbook.write(os);
    os.close();


    System.out.println("  ");
    System.out.println("  ");
    System.out.println("数据分析完成！");
    System.out.println("请查看文件--> c:/" + result + "_工商下载数据/结果.xlsx");
    System.out.println("  ");
    System.out.println("按回车键退出程序...");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }
  }

  /**
   * 从《济南市事中事后监管服务系统》下载的TXT文件
   *
   * @throws Exception
   */
  public static void download() throws Exception {
    System.out.println("               2、下载工商信息         ");
    System.out.println(" ");
    System.out.println("------------------------用前须知------------------------");
    System.out.println(" ");
    System.out.println("1：运行本功能前，请先切换到外网环境！");
    System.out.println("2：待查的Excel文件必须放置在C盘根目录下！");
    System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
    System.out.println("4：Excel文件第一行为标题，如果没有可以不填写！");
    System.out.println("5：Excel文件第一列内容必须为身份证号码！");
    System.out.println("6：Excel文件第二列内容必须为姓名，且不含有空格！");
    System.out.println("7：Excel文件中的信息必须完整，即不能有空白的单元格！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：下载结果跟《济南市事中事后监管服务系统》的工商查询结果保持一致！");
    System.out.println("2：下载结果为TXT文本文件！");
    System.out.println("3：下载过程采用多线程，多核CPU有额外优势！");
    System.out.println("4：下载速度约每分钟500人次，但单次下载超过800人时，系统服务器可能会无法响应。有必要时请重新启动程序！");
    System.out.println(" ");
    System.out.println("-------------------------------------------------------");
    System.out.println(" ");
    String result;
    do {
      // 输出提示文字
      System.out.print("请输入待下载的Excel文件名：");
      InputStreamReader is_reader = new InputStreamReader(System.in);
      result = new BufferedReader(is_reader).readLine();
    } while (!new File("C:\\" + result + ".xlsx").exists());// 当用户输入无效的时候，反复提示要求用户输入

    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
    int total = sheetBefore.getLastRowNum() + 1;
    int total1, total2, total3, total4, total5;
    if (total <= 6) {
      total1 = total;
      total2 = 0;
      total3 = 0;
      total4 = 0;
      total5 = 0;
    } else {
      total1 = (int) Math.ceil(total / 6) + 1;
      total2 = (int) Math.ceil(total / 6) * 2 + 1;
      total3 = (int) Math.ceil(total / 6) * 3 + 1;
      total4 = (int) Math.ceil(total / 6) * 4 + 1;
      total5 = (int) Math.ceil(total / 6) * 5 + 1;
    }

    //  创建文件夹
    File files = new File("C:/" + result + "_工商下载数据");
    files.mkdir();
    copyFile("c:\\" + result + ".xlsx", "c:/" + result + "_工商下载数据/" + result + ".xlsx");
    if (total > 8) {
      Thread t1 = new Thread(result, 1, total1, sheetBefore);
      Thread t2 = new Thread(result, total1, total2, sheetBefore);
      Thread t3 = new Thread(result, total2, total3, sheetBefore);
      Thread t4 = new Thread(result, total3, total4, sheetBefore);
      Thread t5 = new Thread(result, total4, total5, sheetBefore);
      Thread t6 = new Thread(result, total5, total + 1, sheetBefore);
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
      t6.start();
      do {
      } while (!(t1.exit && t2.exit && t3.exit && t4.exit && t5.exit && t6.exit));
    } else {
      Thread t = new Thread(result, 1, total, sheetBefore);
      t.start();
      do {
      } while (!t.exit);
    }

    System.out.println("  ");
    System.out.println("  ");
    System.out.println("请查看下载后的文件--> c:/" + result + "_工商下载数据/");
    System.out.println("  ");
    System.out.println("按回车键退出程序...");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }
  }

}
