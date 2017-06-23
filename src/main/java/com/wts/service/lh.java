package com.wts.service;


import com.wts.entity.JG;
import com.wts.entity.PersonLH;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class lh {
  /**
   * 打开窗口
   * 返回tableMark
   */
  public static String openWindow(CloseableHttpClient client) throws Exception {
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3SuccorFlexibleEmp.do")
            .setParameter("method", "enterFlexEmpPerManage")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "tableMark='";
    String end = "' oncontextmenu=showMenu(";
    if (res.contains(start) && res.contains(end)) {
      return res.substring(res.indexOf(start) + 11, res.indexOf(end));
    } else {
      return "";
    }
  }

  /**
   * 获取机构信息
   */
  public static JG getJG(CloseableHttpClient client) throws Exception {
    JG jg=null;
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3Lov.do")
            .setParameter("method", "queryGovInfo")
            .setParameter("containerName", "formPerInfoQuery")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s contentName=\"jgmc\"/><s paraName=\"jgbh\"/></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "init('true','true','[";
    String end = "]');</script>";
    if (res.contains(start) && res.contains(end)) {
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(start) + 20, res.indexOf(end)+1));
      if (jsStrs.size() > 0) {
        JSONObject jsStr = jsStrs.getJSONObject(0);
        jg.setJbjgbh(jsStr.getString("jbjgbh"));
        jg.setJgbh(jsStr.getString("jgbh"));
        jg.setJgmc(jsStr.getString("jgmc"));
      }
    }
    return jg;
  }

  /**
   * 查找人员第一页
   * 返回值为总页数。页面上是每页16条记录，但API返回的是每页12条记录，所以总页面有出入
   */
  public static String getTotal(CloseableHttpClient client,JG jg, String tableMark) throws Exception {
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3SuccorFlexibleEmp.do")
            .setParameter("method", "queryFlexEmp")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" spzt=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" jbjgbh=\""+jg.getJbjgbh()+"\" jgbh=\""+jg.getJgbh()+"\" jgmc=\""+jg.getJgmc()+"\" sfbl=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" sfyba=\"\" /></p>")
            .setParameter("tableMark", tableMark)
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "init('true','true','[";
    String end = "]');columninput";
    String pageStart = "记录&nbsp;&nbsp;&nbsp;&nbsp;1/";
    String pageEnd = "页</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    //return res.substring(res.indexOf(start) + 20, res.indexOf(end)) +":"+ res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));
    return res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));

  }

  /**
   * 获取第N页的人员
   */
  public static String getPersonPage(CloseableHttpClient client, String tableMark, String page, String total) throws Exception {
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/pilot.do")
            .setParameter("method", "view")
            .setParameter("nextPage", page)
            .setParameter("tableMark", tableMark)
            .setParameter("maxPage", total) //没有这个参数应该也没问题
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "init('true','true','[";
    String end = "]');columninput";
    return res.substring(res.indexOf(start) + 21, res.indexOf(end));
  }

  /**
   * 获取全部人员
   */
  public static String getPersonAll(CloseableHttpClient client, String tableMark, Integer page) throws Exception {
    String str = "";
    for (int i = 1; i < page + 1; i++) {
      str = str + getPersonPage(client, tableMark, i + "",page.toString()) + ",";
    }
    return "[" + str.substring(0, str.length() - 1) + "]";
  }

  /**
   * 获取人员列表
   */
  public static List<PersonLH> goPersonLHs(CloseableHttpClient client) throws Exception {
    List<PersonLH> goPersonLHs = new ArrayList<PersonLH>();
    String datawindow = openWindow(client);
    if (datawindow.equals("")) {
      return goPersonLHs;
    }
    Integer total = Integer.parseInt(getTotal(client, getJG(client),datawindow));
    String mergePerson = getPersonAll(client, datawindow, total);
    JSONArray jsStrs = JSONArray.fromObject(mergePerson);
    if (jsStrs.size() > 0) {
      for (int j = 0; j < jsStrs.size() + 1; j++) {
        JSONObject jsStr = jsStrs.getJSONObject(j);
        PersonLH p = new PersonLH();
        p.setWhcd(jsStr.getString("whcd"));
        p.setCjrmc(jsStr.getString("cjrmc"));
        p.setSqmc(jsStr.getString("sqmc"));
        p.setDjlsh(jsStr.getString("djlsh"));
        p.setYanglaobt(jsStr.getString("yanglaobt"));
        p.setWtgyy(jsStr.getString("wtgyy"));
        p.setZzny(jsStr.getString("zzny"));
        p.setSpzt(jsStr.getString("spzt"));
        p.setKnrybh(jsStr.getString("knrybh"));
        p.setTcrq(jsStr.getString("tcrq"));
        p.setBtje(jsStr.getString("btje"));
        p.setQsny(jsStr.getString("qsny"));
        p.setCjrq(jsStr.getString("cjrq"));
        p.setYiliaobt(jsStr.getString("yiliaobt"));
        p.setCjjgmc(jsStr.getString("cjjgmc"));
        p.setJydjbh(jsStr.getString("jydjbh"));
        p.setGmsfhm(jsStr.getString("gmsfhm"));
        p.setGrxm(jsStr.getString("grxm"));
        p.setLxdh(jsStr.getString("lxdh"));
        p.setLhjycszy(jsStr.getString("lhjycszy"));
        p.setXb(jsStr.getString("xb"));
        p.setSfyba(jsStr.getString("sfyba"));
        p.setGrbh(jsStr.getString("grbh"));
        goPersonLHs.add(p);
      }
    }
    return goPersonLHs;
  }

  /**
   * 获取剩余月数
   */
  public static String getSyys(CloseableHttpClient client, String gmsfhm, String grbh, String djlsh) throws Exception {
    String grxm = "";
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "enterBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s djlsh=\"" + djlsh + "\"/><s grbh=\"" + grbh + "\"/><s gmsfhm=\"" + gmsfhm + "\"/><s grxm=\"" + grxm + "\"/><s btrylb=\"\"/></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "dataType=\"number\" style=\"text-align:right;display:none;color:\" value=\"";
    String end = "\" defaultZero=\"true\" required=\"false\" /><input type='text' readonly=true  class=\"label\" tabindex=\"-1\" id=\"syys_label\" name=\"syys_label\" style=\"TEXT-ALIGN: right;color: \" title=\"";
    if (res.contains(start) && res.contains(end)) {
      return res.substring(res.indexOf(start) + 70, res.indexOf(end));
    } else {
      return "";
    }
  }

  /**
   * 生成补贴
   * btrylb 补贴人员类别 01企业吸纳02公益性03灵活就业
   */
  public static String creatMoney(CloseableHttpClient client, String gmsfhm, String grbh, String djlsh, String qsny, String zzny, String syys) throws Exception {
    String grxm = "";
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "createBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s grbh=\"" + grbh + "\" msg=\"\" grxm=\"" + grxm + "\" gmsfhm=\"" + gmsfhm + "\" djlsh=\"" + djlsh + "\" btrylb=\"03\" syys=\""+syys+"\" qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" /><d k=\"dw_ylbt\"></d></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    if (res.indexOf("月的补贴已录入") > 0
            || res.indexOf("补贴享受起始年月早于审批日期，请检查！") > 0
            || res.indexOf("人员【"+qsny+"】到【"+zzny+"】的补贴预算，请检查！") > 0
            || res.indexOf("的资金报表已经上报，不允许维护补贴，请检查！") > 0
            ) {
      return "[]";
    } else {
      String start = "init('true','true','[";
      String end = "]');</script>";
      return res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1);
    }
  }


}
