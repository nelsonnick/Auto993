package com.wts.function.security;

import com.wts.service.Common;
import com.wts.util.Kit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URI;

import static com.wts.function.security.Security.getSecurityType;
import static com.wts.util.Kit.getCharacterPosition;

public class GetBy993 {

  /**
   * 保存记录
   */
  private static void save(JSONArray jsStrs, XSSFSheet SheetBefore, XSSFSheet SheetAfter, int count, int i) {
    XSSFRow nextRows = SheetAfter.createRow(SheetAfter.getLastRowNum() + 1);
    for (int j = 0; j < count; j++) {
      nextRows.createCell(j).setCellValue(SheetBefore.getRow(i).getCell(j).toString());
    }
    if (jsStrs.size() > 0) {
      JSONObject jsStr = jsStrs.getJSONObject(0);
      nextRows.createCell(count).setCellValue(jsStr.getString("grxm"));
      nextRows.createCell(count + 1).setCellValue(jsStr.getString("dwmc"));
      nextRows.createCell(count + 2).setCellValue(jsStr.getString("dwsbjfbh"));
      nextRows.createCell(count + 3).setCellValue(jsStr.getString("dwxz"));
      nextRows.createCell(count + 4).setCellValue(getSecurityType(jsStr.getString("dwsbjfbh")));
      nextRows.createCell(count + 5).setCellValue(jsStr.getString("zzny"));
    } else {
      nextRows.createCell(count).setCellValue("");
      nextRows.createCell(count + 1).setCellValue("");
      nextRows.createCell(count + 2).setCellValue("");
      nextRows.createCell(count + 3).setCellValue("");
      nextRows.createCell(count + 4).setCellValue("");
      nextRows.createCell(count + 5).setCellValue("无缴费记录");
    }
  }

  /**
   * 保存记录
   */
  private static void saveNo(XSSFSheet SheetBefore, XSSFSheet SheetAfter, int count, int i) {
    XSSFRow nextRows = SheetAfter.createRow(SheetAfter.getLastRowNum() + 1);
    for (int j = 0; j < count; j++) {
      nextRows.createCell(j).setCellValue(SheetBefore.getRow(i).getCell(j).toString());
    }
    nextRows.createCell(count).setCellValue("");
    nextRows.createCell(count + 1).setCellValue("");
    nextRows.createCell(count + 2).setCellValue("");
    nextRows.createCell(count + 3).setCellValue("");
    nextRows.createCell(count + 4).setCellValue("");
    nextRows.createCell(count + 5).setCellValue("无缴费记录");
  }

  /**
   * 发起请求
   */
  private static void send(CloseableHttpClient login_httpclient, XSSFSheet sheetBefore, XSSFSheet sheetAfter, int count, int i, String personNumber) throws Exception {
    URI gsUrl = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3Person.do")
            .setParameter("method", "querySbjPersonInfo")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + personNumber + "\" xshs=\"200\" /></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost gs_post = new HttpPost(gsUrl);
    // 创建默认的httpClient实例.
    CloseableHttpResponse gs_response = login_httpclient.execute(gs_post);
    HttpEntity gs_entity = gs_response.getEntity();
    String res = EntityUtils.toString(gs_entity, "UTF-8");
    String s1 = "init('true','true','[";
    String e1 = "]');</script>";
    String s2 = "init\\(\\'true\\'\\,\\'true\\'\\,\\'\\[";
    String e2 = "\\]\\'\\)\\;\\<\\/script\\>";
    if (Kit.getCount(res, s1) == 0) {
      saveNo(sheetBefore, sheetAfter, count, i);
    } else if (Kit.getCount(res, s1) == 1) {
      if (res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") > 0) {
        JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1));
        save(jsStrs, sheetBefore, sheetAfter, count, i);
      } else {
        saveNo(sheetBefore, sheetAfter, count, i);
      }
    } else if (Kit.getCount(res, s1) == 2) {
      if (res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1).lastIndexOf("dwsbjfbh") <= 0 && res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") <= 0) {
        saveNo(sheetBefore, sheetAfter, count, i);
      } else {
        if (res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1).lastIndexOf("dwsbjfbh") > 0) {
          JSONArray jsStrs1 = JSONArray.fromObject(res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1));
          save(jsStrs1, sheetBefore, sheetAfter, count, i);
        }
        if (res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") > 0) {
          JSONArray jsStrs2 = JSONArray.fromObject(res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1));
          save(jsStrs2, sheetBefore, sheetAfter, count, i);
        }
      }
    } else if (Kit.getCount(res, s1) == 3) {
      if (res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") <= 0 && res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1).lastIndexOf("dwsbjfbh") <= 0 && res.substring(getCharacterPosition(res, s2, 2) + 20, getCharacterPosition(res, e2, 2) + 1).lastIndexOf("dwsbjfbh") <= 0) {
        saveNo(sheetBefore, sheetAfter, count, i);
      } else {
        if (res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") > 0) {
          JSONArray jsStrs1 = JSONArray.fromObject(res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1));
          save(jsStrs1, sheetBefore, sheetAfter, count, i);
        }
        if (res.substring(getCharacterPosition(res, s2, 2) + 20, getCharacterPosition(res, e2, 2) + 1).lastIndexOf("dwsbjfbh") > 0) {
          JSONArray jsStrs2 = JSONArray.fromObject(res.substring(getCharacterPosition(res, s2, 2) + 20, getCharacterPosition(res, e2, 2) + 1));
          save(jsStrs2, sheetBefore, sheetAfter, count, i);
        }
        if (res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1).lastIndexOf("dwsbjfbh") > 0) {
          JSONArray jsStrs3 = JSONArray.fromObject(res.substring(res.lastIndexOf(s1) + 20, res.lastIndexOf(e1) + 1));
          save(jsStrs3, sheetBefore, sheetAfter, count, i);
        }
      }
    } else {
      if (res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1).indexOf("dwsbjfbh") > 0) {
        JSONArray jsStrs1 = JSONArray.fromObject(res.substring(res.indexOf(s1) + 20, res.indexOf(e1) + 1));
        save(jsStrs1, sheetBefore, sheetAfter, count, i);
      }
      if (res.substring(getCharacterPosition(res, s2, 2) + 20, getCharacterPosition(res, e2, 2) + 1).lastIndexOf("dwsbjfbh") > 0) {
        JSONArray jsStrs2 = JSONArray.fromObject(res.substring(getCharacterPosition(res, s2, 2) + 20, getCharacterPosition(res, e2, 2) + 1));
        save(jsStrs2, sheetBefore, sheetAfter, count, i);
      }
    }
    gs_response.close();
  }

  public static void get() throws Exception {

    System.out.println(" ");
    System.out.println("               4、核查社保信息         ");
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
    System.out.println("8：单次查询人数不要超过1000人，否则抓取速度会非常慢！");
    System.out.println(" ");
    System.out.println("------------------------结果说明------------------------");
    System.out.println(" ");
    System.out.println("1：核查结果跟劳动993系统中的社保查询结果保持一致！");
    System.out.println("2：查询结果仅包含最近一次的缴费情况！");
    System.out.println("3：查询人员同时存在多个缴纳类型时，程序会尝试全部抓取下来！");
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

//          IPset();
//          do {
//            Thread.sleep(500);
//          } while (!IPget().equals("10.153.73.166"));
//          System.out.println("切换到内网");
    URI loginUri = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/logon.do")
            .setParameter("method", "doLogon")
            .setParameter("userid", Common.userid)
            .setParameter("passwd", Kit.getMD5(Common.passwd))
            .setParameter("userLogSign", "0")
            .setParameter("passWordLogSign", "0")
            .setParameter("screenHeight", "768")
            .setParameter("screenWidth", "1024")
            .setParameter("mode", "")
            .build();
    HttpPost login_post = new HttpPost(loginUri);
    // 创建默认的httpClient实例.
    CloseableHttpClient login_httpclient = HttpClients.createDefault();
    CloseableHttpResponse login_response = login_httpclient.execute(login_post);
    HttpEntity login_entity = login_response.getEntity();
    if (login_entity != null) {
      System.out.println("  ");
      System.out.println("是否成功接入社保数据库: " + EntityUtils.toString(login_entity, "UTF-8"));
    }
    System.out.println("3秒后开始抓取数据....");
    Thread.sleep(1000);
    System.out.println("2秒后开始抓取数据....");
    Thread.sleep(1000);
    System.out.println("1秒后开始抓取数据....");
    Thread.sleep(1000);
    System.out.println("开始抓取....");
    XSSFWorkbook workbookAfter = new XSSFWorkbook();
    XSSFSheet sheetAfter = workbookAfter.createSheet("sheet1");
    XSSFWorkbook workbookBefore = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
    XSSFSheet sheetBefore = workbookBefore.getSheetAt(0);
    XSSFRow rowAfter = sheetAfter.createRow(0);
    int count = sheetBefore.getRow(0).getPhysicalNumberOfCells();
    int total = sheetBefore.getLastRowNum();
    for (int j = 0; j < count; j++) {
      rowAfter.createCell(j).setCellValue(sheetBefore.getRow(0).getCell(j).toString());
    }
    rowAfter.createCell(count).setCellValue("个人姓名");//grxm
    rowAfter.createCell(count + 1).setCellValue("单位名称");//dwmc
    rowAfter.createCell(count + 2).setCellValue("单位编号");//dwsbjfbh
    rowAfter.createCell(count + 3).setCellValue("单位性质"); //dwxz
    rowAfter.createCell(count + 4).setCellValue("缴纳险种");
    rowAfter.createCell(count + 5).setCellValue("缴纳月份");
    for (int i = 1; i < total + 1; i++) {
      String personNumber = sheetBefore.getRow(i).getCell(0).getStringCellValue();
      System.out.println("正在抓取第" + i + "行人员，身份证号码为：" + personNumber);
      send(login_httpclient, sheetBefore, sheetAfter, count, i, personNumber);
    }
    login_response.close();
    login_httpclient.close();
    FileOutputStream os = new FileOutputStream("c:\\" + result + "_社保数据抓取后.xlsx");
    workbookAfter.write(os);
    os.close();
//            IPback();
//            System.out.println("切换回外网");
    System.out.println("  ");
    System.out.println("  ");
    System.out.println("社保数据抓取完成！");
    System.out.println("请查看文件--> c:\\" + result + "_社保数据抓取后.xlsx");
    System.out.println("  ");
    System.out.println("按回车键退出程序...");
    while (true) {
      if (System.in.read() == '\n')
        System.exit(0);
    }

  }
}
