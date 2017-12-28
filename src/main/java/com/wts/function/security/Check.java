package com.wts.function.security;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Check {
    public static void checkSecury(String result, String month) throws Exception {
        // 生成查询结果文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("证件号码");
        row.createCell(1).setCellValue("人员姓名");
        row.createCell(2).setCellValue("缴费年月");
        row.createCell(3).setCellValue("错误说明");
        // 分析生成的excel
        String analysis_name = result + month;
        XSSFWorkbook workbook_analysis = new XSSFWorkbook(new FileInputStream("c:\\" + result + "_社保下载数据/" + analysis_name + ".xlsx"));
        XSSFSheet sheet_analysis = workbook_analysis.getSheetAt(0);
        int total_analysis = sheet_analysis.getLastRowNum() + 1;
        // 下载的excel
        String down_name = result + "";
        XSSFWorkbook workbook_down = new XSSFWorkbook(new FileInputStream("c:\\" + result + "_社保下载数据/" + down_name + ".xlsx"));
        XSSFSheet sheet_down = workbook_down.getSheetAt(0);
        int down_analysis = sheet_down.getLastRowNum() + 1;
        String sfzhm = "";
        for (int i = 1; i < total_analysis; i++) {
            String zjhm_analysis = sheet_analysis.getRow(i).getCell(0).getStringCellValue();//身份证号码
            String ryxm_analysis = sheet_analysis.getRow(i).getCell(1).getStringCellValue();//姓名
            String jfny_analysis = sheet_analysis.getRow(i).getCell(2).getStringCellValue();//缴费年月
            String dwbh_analysis = sheet_analysis.getRow(i).getCell(3).getStringCellValue();//单位编号
            String dwmc_analysis = sheet_analysis.getRow(i).getCell(4).getStringCellValue();//单位名称
            String jfxz_analysis = sheet_analysis.getRow(i).getCell(5).getStringCellValue();//缴费性质
            String jfqk_analysis = sheet_analysis.getRow(i).getCell(6).getStringCellValue();//缴费情况
            String jfsj_analysis = sheet_analysis.getRow(i).getCell(7).getStringCellValue();//缴费时间
            if (zjhm_analysis.equals(sfzhm)) {
                continue;
            } else {
                sfzhm = zjhm_analysis;
            }
            int find = 0;
            for (int j = 1; j < down_analysis; j++) {
                String zjhm_down = sheet_down.getRow(j).getCell(0).getStringCellValue();//身份证号码
                String ryxm_down = sheet_down.getRow(j).getCell(1).getStringCellValue();//姓名
                String ryxb_down = sheet_down.getRow(j).getCell(2).getStringCellValue();//性别
                String btny_down = sheet_down.getRow(j).getCell(3).getStringCellValue();//补贴年月
                String ylbt_down = sheet_down.getRow(j).getCell(4).getStringCellValue();//养老补贴
                String yybt_down = sheet_down.getRow(j).getCell(5).getStringCellValue();//医疗补贴
                String sybt_down = sheet_down.getRow(j).getCell(6).getStringCellValue();//失业补贴
                if (zjhm_analysis.equals(zjhm_down) && jfny_analysis.equals(btny_down)) {
                    if (!jfqk_analysis.equals("正常缴费")) {
                        int total = sheet.getLastRowNum();
                        XSSFRow newRow = sheet.createRow(total + 1);
                        newRow.createCell(0).setCellValue(zjhm_analysis);
                        newRow.createCell(1).setCellValue(ryxm_analysis);
                        newRow.createCell(2).setCellValue(jfny_analysis);
                        newRow.createCell(3).setCellValue("非正常缴费");
                    } else {
                        if (jfxz_analysis.equals("个人一险")) {
                            if (!(ylbt_down.equals(Security.Yang_Lao) && yybt_down.equals("0"))) {
                                int total = sheet.getLastRowNum();
                                XSSFRow newRow = sheet.createRow(total + 1);
                                newRow.createCell(0).setCellValue(zjhm_analysis);
                                newRow.createCell(1).setCellValue(ryxm_analysis);
                                newRow.createCell(2).setCellValue(jfny_analysis);
                                newRow.createCell(3).setCellValue("个人一险录入错误");
                            }
                        } else if (jfxz_analysis.equals("个人两险")) {
                            if (!(ylbt_down.equals(Security.Yang_Lao) && yybt_down.equals(Security.Yi_Liao))) {
                                int total = sheet.getLastRowNum();
                                XSSFRow newRow = sheet.createRow(total + 1);
                                newRow.createCell(0).setCellValue(zjhm_analysis);
                                newRow.createCell(1).setCellValue(ryxm_analysis);
                                newRow.createCell(2).setCellValue(jfny_analysis);
                                newRow.createCell(3).setCellValue("个人两险录入错误");
                            }
                        } else if (jfxz_analysis.equals("单位五险")) {
                            int total = sheet.getLastRowNum();
                            XSSFRow newRow = sheet.createRow(total + 1);
                            newRow.createCell(0).setCellValue(zjhm_analysis);
                            newRow.createCell(1).setCellValue(ryxm_analysis);
                            newRow.createCell(2).setCellValue(jfny_analysis);
                            newRow.createCell(3).setCellValue("单位五险，不应享受补贴");
                        } else if (jfxz_analysis.equals("无养老缴费记录")) {
                            int total = sheet.getLastRowNum();
                            XSSFRow newRow = sheet.createRow(total + 1);
                            newRow.createCell(0).setCellValue(zjhm_analysis);
                            newRow.createCell(1).setCellValue(ryxm_analysis);
                            newRow.createCell(2).setCellValue(jfny_analysis);
                            newRow.createCell(3).setCellValue("无养老缴费记录，不应享受补贴");
                        } else {
                            int total = sheet.getLastRowNum();
                            XSSFRow newRow = sheet.createRow(total + 1);
                            newRow.createCell(0).setCellValue(zjhm_analysis);
                            newRow.createCell(1).setCellValue(ryxm_analysis);
                            newRow.createCell(2).setCellValue(jfny_analysis);
                            newRow.createCell(3).setCellValue("无法识别缴费性质");
                        }
                    }
                } else {
                    find = find + 1;
                }
            }
            if (find == down_analysis - 1) {
                int total = sheet.getLastRowNum();
                XSSFRow newRow = sheet.createRow(total + 1);
                newRow.createCell(0).setCellValue(zjhm_analysis);
                newRow.createCell(1).setCellValue(ryxm_analysis);
                newRow.createCell(2).setCellValue(jfny_analysis);
                newRow.createCell(3).setCellValue("当前月份未录入补贴，请核实");
            }
        }

        for (int j = 1; j < down_analysis; j++) {
            String zjhm_down = sheet_down.getRow(j).getCell(0).getStringCellValue();//身份证号码
            String ryxm_down = sheet_down.getRow(j).getCell(1).getStringCellValue();//姓名
            String btny_down = sheet_down.getRow(j).getCell(3).getStringCellValue();//补贴年月
            int find = 0;
            for (int i = 1; i < total_analysis; i++) {
                String zjhm_analysis = sheet_analysis.getRow(i).getCell(0).getStringCellValue();//身份证号码
                String ryxm_analysis = sheet_analysis.getRow(i).getCell(1).getStringCellValue();//姓名
                String jfny_analysis = sheet_analysis.getRow(i).getCell(2).getStringCellValue();//缴费年月
                if (jfny_analysis.equals(btny_down)) {
                    if (!zjhm_analysis.equals(zjhm_down)) {
                        find = find + 1;
                    }
                }
            }
            if (find == total_analysis - 1) {
                int total = sheet.getLastRowNum();
                XSSFRow newRow = sheet.createRow(total + 1);
                newRow.createCell(0).setCellValue(zjhm_down);
                newRow.createCell(1).setCellValue(ryxm_down);
                newRow.createCell(2).setCellValue(btny_down);
                newRow.createCell(3).setCellValue("该人员不应录入补贴");
            }
        }


        FileOutputStream os = new FileOutputStream("c:/" + result + "_社保下载数据/" + result + month + "比对结果.xlsx");
        workbook.write(os);
        os.close();
        System.out.println(result + month + "_数据比对完成！");
    }

    public static void goCheck() throws Exception {
        System.out.println(" ");
        System.out.println("                 a、比对社保数据        ");
        System.out.println(" ");
        System.out.println("------------------------用前须知------------------------");
        System.out.println(" ");
        System.out.println("1：运行本功能前，请先从993系统中导出汇总表！");
        System.out.println("2：运行本功能前，请先用功能6生成分析表！");
        System.out.println("3：Excel文件后缀为xlsx，xls后缀的低版本Excel文件无法读取！");
        System.out.println(" ");
        System.out.println("------------------------结果说明------------------------");
        System.out.println(" ");
        System.out.println("1：根据文件内容进行排查！");
        System.out.println(" ");
        System.out.println("-------------------------------------------------------");
        System.out.println(" ");

        String result, month;
        do {
            System.out.print("请输入待录入的Excel文件名：");
            InputStreamReader is_reader = new InputStreamReader(System.in);
            result = new BufferedReader(is_reader).readLine();
        } while (!new File("C:\\" + result + "_社保下载数据/" + result + "导出.xlsx").exists());
        do {
            System.out.print("请输入要核查的6位年月：");
            InputStreamReader is_reader = new InputStreamReader(System.in);
            month = new BufferedReader(is_reader).readLine();
        } while (!month.matches("\\d{6}"));
        checkSecury(result, month);
    }

    public static void main(String[] args) throws Exception {
        checkSecury("五里沟", "201710");

    }
}
