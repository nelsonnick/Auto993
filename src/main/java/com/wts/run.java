package com.wts;

import com.wts.function.security.Thread;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import static com.wts.function.security.Security.getSecurityType;
import static com.wts.util.Kit.copyFile;

public class run {
  public static void go(String result,String months) throws Exception{
    String month = months.substring(0, 4) + '.' + months.substring(4, 6);

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
    String sfzhm = "";
    for (int k = 1; k < totals + 1; k++) {
      String id = sheetBefore.getRow(k).getCell(0).getStringCellValue();
      String name = sheetBefore.getRow(k).getCell(1).getStringCellValue();
      if (id.equals(sfzhm)) {
        continue;
      } else {
        sfzhm = id;
      }
      String fileName = "c:/" + result + "_社保下载数据/" + id + name + ".txt";
      File f = new File(fileName);
      if (!f.exists()) {
        int total = sheet.getLastRowNum();
        XSSFRow newRow = sheet.createRow(total + 1);
        newRow.createCell(0).setCellValue(id);
        newRow.createCell(1).setCellValue(name);
        newRow.createCell(2).setCellValue(months);
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
          newRow.createCell(2).setCellValue(months);
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
          newRow.createCell(2).setCellValue(months);
          newRow.createCell(3).setCellValue("无养老缴费记录");
          newRow.createCell(4).setCellValue("无养老缴费记录");
          newRow.createCell(5).setCellValue("无养老缴费记录");
          newRow.createCell(6).setCellValue("无养老缴费记录");
          newRow.createCell(7).setCellValue("无养老缴费记录");
        }
      }
    }
    FileOutputStream os = new FileOutputStream("c:/" + result + "_社保下载数据/"+result + months + ".xlsx");
    workbook.write(os);
    os.close();
    System.out.println("请查看文件--> c:/" + result + "_社保下载数据/"+result + months + ".xlsx");
  }
  public static void main(String[] args) throws Exception {

    String[] locations = new String[]{"西市场", "五里沟", "道德街",  "青年公园", "中大", "振兴街", "南辛庄", "段北", "匡山", "张庄", "美里湖", "腊山", "吴家堡", "玉清湖", "兴福"};
    String[] months = new String[]{"201710", "201711", "201712"};
    for(String location : locations ){
      for(String month : months ) {
        go(location, month);
        com.wts.function.security.Check.checkSecury(location, month);
      }
    }

//    com.wts.function.commerce.Jnjgfw.analysis();
//    com.wts.function.commerce.Jnjgfw.download();
//    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
//    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
//    int total = sheetBefore.getLastRowNum() + 1;
//    System.out.println("总人数：" + total);
//    //  创建文件夹
//    File files = new File("C:/" + result + "_社保下载数据");
//    files.mkdir();
//    copyFile("c:\\" + result + ".xlsx", "c:/" + result + "_社保下载数据/" + result + ".xlsx");
//    Thread t = new Thread(result, 1, total, sheetBefore);
//    t.start();
//    do {
//    } while (!t.exit);
//    System.out.println("  ");
//    System.out.println("  ");
//    System.out.println("请查看下载后的文件--> c:/" + result + "_社保下载数据/");
  }
}

