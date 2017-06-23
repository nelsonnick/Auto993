package com.wts.util;

import com.wts.entity.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;


public class Export {

    public static void ExportQY(List<PersonQY> PersonQYs) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("公民身份号码");
        row.createCell(1).setCellValue("个人姓名");
        row.createCell(2).setCellValue("个人编号");
        row.createCell(3).setCellValue("登记流水号");
        row.createCell(4).setCellValue("困难人员编号");
        row.createCell(5).setCellValue("性别");
        row.createCell(6).setCellValue("出生日期");
        row.createCell(7).setCellValue("单位名称");
        row.createCell(8).setCellValue("企业认定编号");
        row.createCell(9).setCellValue("未通过原因");
        row.createCell(10).setCellValue("退出日期");
        row.createCell(11).setCellValue("审批状态");
        row.createCell(12).setCellValue("创建机构");
        row.createCell(13).setCellValue("创建日期");
        row.createCell(14).setCellValue("创建人");
        row.createCell(15).setCellValue("是否已备案");

        for (PersonQY person : PersonQYs) {
            int total = sheet.getLastRowNum();
            for (int i = 1; i < total + 1; i++) {
                XSSFRow rowNew = sheet.createRow(i);
                rowNew.createCell(0).setCellValue(person.getGmsfhm());
                rowNew.createCell(1).setCellValue(person.getGrxm());
                rowNew.createCell(2).setCellValue(person.getGrbh());
                rowNew.createCell(3).setCellValue(person.getDjlsh());
                rowNew.createCell(4).setCellValue(person.getKnrybh());
                rowNew.createCell(5).setCellValue(person.getXb());
                rowNew.createCell(6).setCellValue(person.getCsrq());
                rowNew.createCell(7).setCellValue(person.getDwmc());
                rowNew.createCell(8).setCellValue(person.getQyrdbh());
                rowNew.createCell(9).setCellValue(person.getWtgyy());
                rowNew.createCell(10).setCellValue(person.getTcrq());
                rowNew.createCell(11).setCellValue(person.getSpzt());
                rowNew.createCell(12).setCellValue(person.getCjjg());
                rowNew.createCell(13).setCellValue(person.getCjrq());
                rowNew.createCell(14).setCellValue(person.getCjr());
                rowNew.createCell(15).setCellValue(person.getSfyba());
            }
        }
        FileOutputStream os = new FileOutputStream("c:\\" + "企业吸纳人员信息" + ".xlsx");
        workbook.write(os);
        os.close();

    }

    public static void ExportLH(List<PersonLH> PersonLHs) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("公民身份号码");
        row.createCell(1).setCellValue("个人姓名");
        row.createCell(2).setCellValue("个人编号");
        row.createCell(3).setCellValue("登记流水号");
        row.createCell(4).setCellValue("困难人员编号");
        row.createCell(5).setCellValue("性别");
        row.createCell(6).setCellValue("出生日期");
        row.createCell(7).setCellValue("单位名称");
        row.createCell(8).setCellValue("企业认定编号");
        row.createCell(9).setCellValue("未通过原因");
        row.createCell(10).setCellValue("退出日期");
        row.createCell(11).setCellValue("审批状态");
        row.createCell(12).setCellValue("创建机构");
        row.createCell(13).setCellValue("创建日期");
        row.createCell(14).setCellValue("创建人");
        row.createCell(15).setCellValue("是否已备案");

        for (PersonLH person : PersonLHs) {
            int total = sheet.getLastRowNum();
            for (int i = 1; i < total + 1; i++) {
                XSSFRow rowNew = sheet.createRow(i);
                rowNew.createCell(0).setCellValue(person.getGmsfhm());
                rowNew.createCell(1).setCellValue(person.getGrxm());
                rowNew.createCell(2).setCellValue(person.getGrbh());
                rowNew.createCell(3).setCellValue(person.getDjlsh());
                rowNew.createCell(4).setCellValue(person.getKnrybh());
                rowNew.createCell(5).setCellValue(person.getXb());
                rowNew.createCell(6).setCellValue(person.getCsrq());
                rowNew.createCell(7).setCellValue(person.getDwmc());
                rowNew.createCell(8).setCellValue(person.getQyrdbh());
                rowNew.createCell(9).setCellValue(person.getWtgyy());
                rowNew.createCell(10).setCellValue(person.getTcrq());
                rowNew.createCell(11).setCellValue(person.getSpzt());
                rowNew.createCell(12).setCellValue(person.getCjjg());
                rowNew.createCell(13).setCellValue(person.getCjrq());
                rowNew.createCell(14).setCellValue(person.getCjr());
                rowNew.createCell(15).setCellValue(person.getSfyba());
            }
        }
        FileOutputStream os = new FileOutputStream("c:\\" + "灵活就业人员信息" + ".xlsx");
        workbook.write(os);
        os.close();

    }

    public static void ExportGG(List<PersonGG> PersonGGs) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("公民身份号码");
        row.createCell(1).setCellValue("个人姓名");
        row.createCell(2).setCellValue("个人编号");
        row.createCell(3).setCellValue("登记流水号");
        row.createCell(4).setCellValue("困难人员编号");
        row.createCell(5).setCellValue("性别");
        row.createCell(6).setCellValue("出生日期");
        row.createCell(7).setCellValue("单位名称");
        row.createCell(8).setCellValue("企业认定编号");
        row.createCell(9).setCellValue("未通过原因");
        row.createCell(10).setCellValue("退出日期");
        row.createCell(11).setCellValue("审批状态");
        row.createCell(12).setCellValue("创建机构");
        row.createCell(13).setCellValue("创建日期");
        row.createCell(14).setCellValue("创建人");
        row.createCell(15).setCellValue("是否已备案");

        for (PersonGG person : PersonGGs) {
            int total = sheet.getLastRowNum();
            for (int i = 1; i < total + 1; i++) {
                XSSFRow rowNew = sheet.createRow(i);
                rowNew.createCell(0).setCellValue(person.getGmsfhm());
                rowNew.createCell(1).setCellValue(person.getGrxm());
                rowNew.createCell(2).setCellValue(person.getGrbh());
                rowNew.createCell(3).setCellValue(person.getDjlsh());
                rowNew.createCell(4).setCellValue(person.getKnrybh());
                rowNew.createCell(5).setCellValue(person.getXb());
                rowNew.createCell(6).setCellValue(person.getCsrq());
                rowNew.createCell(7).setCellValue(person.getDwmc());
                rowNew.createCell(8).setCellValue(person.getQyrdbh());
                rowNew.createCell(9).setCellValue(person.getWtgyy());
                rowNew.createCell(10).setCellValue(person.getTcrq());
                rowNew.createCell(11).setCellValue(person.getSpzt());
                rowNew.createCell(12).setCellValue(person.getCjjg());
                rowNew.createCell(13).setCellValue(person.getCjrq());
                rowNew.createCell(14).setCellValue(person.getCjr());
                rowNew.createCell(15).setCellValue(person.getSfyba());
            }
        }
        FileOutputStream os = new FileOutputStream("c:\\" + "公益岗位人员信息" + ".xlsx");
        workbook.write(os);
        os.close();

    }

    public static void ExportKN(List<PersonKN> PersonKNs) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("公民身份号码");
        row.createCell(1).setCellValue("个人姓名");
        row.createCell(2).setCellValue("个人编号");
        row.createCell(3).setCellValue("登记流水号");
        row.createCell(4).setCellValue("困难人员编号");
        row.createCell(5).setCellValue("性别");
        row.createCell(6).setCellValue("出生日期");
        row.createCell(7).setCellValue("单位名称");
        row.createCell(8).setCellValue("企业认定编号");
        row.createCell(9).setCellValue("未通过原因");
        row.createCell(10).setCellValue("退出日期");
        row.createCell(11).setCellValue("审批状态");
        row.createCell(12).setCellValue("创建机构");
        row.createCell(13).setCellValue("创建日期");
        row.createCell(14).setCellValue("创建人");
        row.createCell(15).setCellValue("是否已备案");

        for (PersonKN person : PersonKNs) {
            int total = sheet.getLastRowNum();
            for (int i = 1; i < total + 1; i++) {
                XSSFRow rowNew = sheet.createRow(i);
                rowNew.createCell(0).setCellValue(person.getGmsfhm());
                rowNew.createCell(1).setCellValue(person.getGrxm());
                rowNew.createCell(2).setCellValue(person.getGrbh());
                rowNew.createCell(3).setCellValue(person.getDjlsh());
                rowNew.createCell(4).setCellValue(person.getKnrybh());
                rowNew.createCell(5).setCellValue(person.getXb());
                rowNew.createCell(6).setCellValue(person.getCsrq());
                rowNew.createCell(7).setCellValue(person.getDwmc());
                rowNew.createCell(8).setCellValue(person.getQyrdbh());
                rowNew.createCell(9).setCellValue(person.getWtgyy());
                rowNew.createCell(10).setCellValue(person.getTcrq());
                rowNew.createCell(11).setCellValue(person.getSpzt());
                rowNew.createCell(12).setCellValue(person.getCjjg());
                rowNew.createCell(13).setCellValue(person.getCjrq());
                rowNew.createCell(14).setCellValue(person.getCjr());
                rowNew.createCell(15).setCellValue(person.getSfyba());
            }
        }
        FileOutputStream os = new FileOutputStream("c:\\" + "就业困难人员信息" + ".xlsx");
        workbook.write(os);
        os.close();

    }

    public static void ExportJS(List<PersonJS> PersonJSs) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("公民身份号码");
        row.createCell(1).setCellValue("个人姓名");
        row.createCell(2).setCellValue("个人编号");
        row.createCell(3).setCellValue("登记流水号");
        row.createCell(4).setCellValue("困难人员编号");
        row.createCell(5).setCellValue("性别");
        row.createCell(6).setCellValue("出生日期");
        row.createCell(7).setCellValue("单位名称");
        row.createCell(8).setCellValue("企业认定编号");
        row.createCell(9).setCellValue("未通过原因");
        row.createCell(10).setCellValue("退出日期");
        row.createCell(11).setCellValue("审批状态");
        row.createCell(12).setCellValue("创建机构");
        row.createCell(13).setCellValue("创建日期");
        row.createCell(14).setCellValue("创建人");
        row.createCell(15).setCellValue("是否已备案");

        for (PersonJS person : PersonJSs) {
            int total = sheet.getLastRowNum();
            for (int i = 1; i < total + 1; i++) {
                XSSFRow rowNew = sheet.createRow(i);
                rowNew.createCell(0).setCellValue(person.getGmsfhm());
                rowNew.createCell(1).setCellValue(person.getGrxm());
                rowNew.createCell(2).setCellValue(person.getGrbh());
                rowNew.createCell(3).setCellValue(person.getDjlsh());
                rowNew.createCell(4).setCellValue(person.getKnrybh());
                rowNew.createCell(5).setCellValue(person.getXb());
                rowNew.createCell(6).setCellValue(person.getCsrq());
                rowNew.createCell(7).setCellValue(person.getDwmc());
                rowNew.createCell(8).setCellValue(person.getQyrdbh());
                rowNew.createCell(9).setCellValue(person.getWtgyy());
                rowNew.createCell(10).setCellValue(person.getTcrq());
                rowNew.createCell(11).setCellValue(person.getSpzt());
                rowNew.createCell(12).setCellValue(person.getCjjg());
                rowNew.createCell(13).setCellValue(person.getCjrq());
                rowNew.createCell(14).setCellValue(person.getCjr());
                rowNew.createCell(15).setCellValue(person.getSfyba());
            }
        }
        FileOutputStream os = new FileOutputStream("c:\\" + "家属信息" + ".xlsx");
        workbook.write(os);
        os.close();

    }

}
