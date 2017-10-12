package com.wts.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class Merge {
  public static void mergeExcel(String result) throws Exception {
    String fileName = "c:\\" + result;
    if (new File(fileName + ".xlsx").exists()) {
      XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
      XSSFSheet sheet = workbook.getSheetAt(0);
      int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();//获得总列数
      int rowNum = sheet.getLastRowNum();//获得总行数
      for (int i = 1; i < rowNum + 1; i++) {
        for (int j = 1; j < coloumNum + 1; j++) {
          sheet.getRow(i).getCell(j).getStringCellValue();
        }
      }
      workbook.close();
    } else if (new File(fileName + ".xls").exists()) {
      HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("c:\\" + result + ".xls"));
      HSSFSheet sheet = workbook.getSheetAt(0);
      int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();//获得总列数
      int rowNum = sheet.getLastRowNum();//获得总行数
      for (int i = 1; i < rowNum + 1; i++) {
        for (int j = 1; j < coloumNum + 1; j++) {
          sheet.getRow(i).getCell(j).getStringCellValue();
        }
      }
      workbook.close();
    }else{
      System.out.println("文件不存在");
    }
  }
}
