package com.wts.service;

import com.wts.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.wts.check.commerce.getCommerce;
import static com.wts.check.security.getDWMC;
import static com.wts.check.security.getSecurity;
import static com.wts.service.common.*;
import static com.wts.service.common.creatSubsidy;
import static com.wts.util.IDKit.checkID_B;

public class gg {

  /**
   * 获取单位信息
   */
  public static List<DW> getDW(CloseableHttpClient client) throws Exception {
    List<DW> dws = null;
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3Lov.do")
            .setParameter("method", "queryOrgMeritStationInfo")
            .setParameter("containerName", "queryMeritStaInfo")
            .setParameter("_xmlString", "")
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
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1));
      for (int i = 0; i < jsStrs.size(); i++) {
        JSONObject jsStr = jsStrs.getJSONObject(i);
        DW dw = new DW();
        dw.setXzqhmc(jsStr.getString("xzqhmc"));
        dw.setDwmc(jsStr.getString("dwmc"));
        dw.setDwbh(jsStr.getString("dwbh"));
        dw.setDwzt(jsStr.getString("dwzt"));
        dw.setJbjgmc(jsStr.getString("jbjgmc"));
        dws.add(dw);
      }
    }
    return dws;
  }

  /**
   * 获取全部公岗人员
   *
   * @param client 登陆后的client
   * @return
   */
  public static List<PersonGG> goPersonGGs(CloseableHttpClient client) throws Exception {
    List<PersonGG> goPersonGGs = new ArrayList<PersonGG>();
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      return goPersonGGs;
    }
    DW dw = getDW(client).get(0);
    Integer total = Integer.parseInt(getDataTotal(client, datawindow, 2, dw.getDwbh(), dw.getDwmc()));
    String mergePerson = getDataAll(client, datawindow, total);
    JSONArray jsStrs = JSONArray.fromObject(mergePerson);
    if (jsStrs.size() > 0) {
      for (int j = 0; j < jsStrs.size() + 1; j++) {
        JSONObject jsStr = jsStrs.getJSONObject(j);
        PersonGG p = new PersonGG();
        p.setDwmc(jsStr.getString("dwmc"));
        p.setCjrmc(jsStr.getString("cjrmc"));
        p.setGyxgwmc(jsStr.getString("gyxgwmc"));
        p.setJtzz(jsStr.getString("jtzz"));
        p.setDjlsh(jsStr.getString("djlsh"));
        p.setWtgyy(jsStr.getString("wtgyy"));
        p.setKnrybh(jsStr.getString("knrybh"));
        p.setTcrq(jsStr.getString("tcrq"));
        p.setSpzt(jsStr.getString("spzt"));
        p.setKnrylb(jsStr.getString("knrylb"));
        p.setCjrq(jsStr.getString("cjrq"));
        p.setCjjgmc(jsStr.getString("cjjgmc"));
        p.setGrxm(jsStr.getString("grxm"));
        p.setGmsfhm(jsStr.getString("gmsfhm"));
        p.setLxdh(jsStr.getString("lxdh"));
        p.setXb(jsStr.getString("xb"));
        p.setCsrq(jsStr.getString("csrq"));
        p.setGwlx(jsStr.getString("gwlx"));
        p.setSfyba(jsStr.getString("sfyba"));
        p.setGrbh(jsStr.getString("grbh"));
        goPersonGGs.add(p);
      }
    }
    return goPersonGGs;
  }

  /**
   * 保存补贴
   *
   * @param client      登陆后的client
   * @param gmsfhm      公民身份号码
   * @param grbh        个人编号
   * @param djlsh       登记流水号
   * @param qsny        起始年月
   * @param zzny        终止年月
   * @param syys        剩余月数
   * @param yanglaobz   养老补助
   * @param yiliaobz    医疗补助
   * @param shiyebz     失业补助
   * @param gangweibz   岗位补助
   * @param sfyxyq      是否有效XX
   * @param sfyxffylbt  是否有效发放养老补贴
   * @param sfyxffyilbt 是否有效发放医疗补贴
   * @return
   */
  public static String saveSubsidy(CloseableHttpClient client, String gmsfhm, String grbh, String djlsh, String qsny, String zzny, String syys, String yanglaobz, String yiliaobz, String shiyebz, String gangweibz, String sfyxyq, String sfyxffylbt, String sfyxffyilbt) throws Exception {
    String grxm = "";
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "saveBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s grbh=\"" + grbh + "\" msg=\"\" grxm=\"" + grxm + "\" gmsfhm=\"" + gmsfhm + "\" djlsh=\"" + djlsh + "\" btrylb=\"01\" syys=\"" + syys + "\" qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" /><d k=\"dw_xzbt\"><r qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" sfyxyq=\"" + sfyxyq + "\" sfyxffylbt=\"" + sfyxffylbt + "\" sfyxffyilbt=\"" + sfyxffyilbt + "\" yanglaobz=\"" + yanglaobz + "\" yiliaobz=\"" + yiliaobz + "\" shiyebz=\"" + shiyebz + "\" gangweibz=\"" + gangweibz + "\" /></d></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String start = "alert('";
    String end = "');";
    return res.substring(res.indexOf(start) + 7, res.indexOf(end));
  }

  /**
   * 检查补贴录入情况
   *
   * @param client   登陆后的client
   * @param personGG PersonGG的实例
   * @param month    要检查的月份
   * @return 提示字符串
   */
  public static String check(CloseableHttpClient client, PersonGG personGG, String month) throws Exception {
    if(!checkID_B(personGG.getGmsfhm())){
      System.out.println(personGG.getGmsfhm() + personGG.getGrxm()+ "--" + month + "身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (getCommerce(client, personGG.getGmsfhm()) || getCommerce(client, personGG.getGmsfhm().substring(0, 6) + personGG.getGmsfhm().substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    Element element = getSecurity(personGG.getGmsfhm(), month);
    if (!personGG.getDwmc().equals(getDWMC(element))) {
      return "无法录入：单位名称不一致！";
    }
    String syys = getSyys(client, 2, personGG.getGmsfhm(), personGG.getGrbh(), personGG.getDjlsh());
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 2, personGG.getGmsfhm(), personGG.getGrbh(), personGG.getDjlsh(), month, month, syys);
    if (!creat.substring(0,1).equals("[")) {
      return month + "的补贴生成错误，请人工核查！原因为："+creat;
    }
    return personGG.getGmsfhm() + personGG.getGrxm() + "--" + month + "补贴未录入";
  }

  /**
   * 检查补贴录入情况
   *
   * @param client 登陆后的client
   * @param grxm   个人姓名
   * @param gmsfhm 公民身份号码
   * @param month  要检查的月份
   * @return 提示字符串
   */
  public static String check(CloseableHttpClient client, String grxm, String gmsfhm, String month) throws Exception {
    if(!checkID_B(gmsfhm)){
      System.out.println(gmsfhm + grxm + "--" + month + "身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 2);
    if (datawindow.equals("")) {
      return "无法录入：无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 2, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      return "无法录入：无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 2, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      return "无法录入：无法获取登记流水号！";
    }
    //System.out.println(djlsh);
    String dwmc = jsonObject.getString("dwmc");
    Element element = getSecurity(gmsfhm, month);
    if (!dwmc.equals(getDWMC(element))) {
      return "无法录入：单位名称不一致！";
    }
    //System.out.println(dwmc);
    String syys = getSyys(client, 2, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 2, gmsfhm, grbh, djlsh, month, month, syys);
    if (!creat.substring(0,1).equals("[")) {
      return month + "的补贴生成错误，请人工核查！原因为："+creat;
    }

    return gmsfhm + grxm + "--" + month + "补贴未录入";
  }

  /**
   * 保存
   *
   * @param client   登陆后的client
   * @param personGG PersonQY的实例
   * @param month    要保存的月份
   * @return 提示字符串
   */
  public static String save(CloseableHttpClient client, PersonGG personGG, String month) throws Exception {
    if(!checkID_B(personGG.getGmsfhm())){
      System.out.println(personGG.getGmsfhm() + personGG.getGrxm()+ "--" + month + "身份证号码错误！");
      return "身份证号码错误！";
    }
    if (getCommerce(client, personGG.getGmsfhm()) || getCommerce(client, personGG.getGmsfhm().substring(0, 6) + personGG.getGmsfhm().substring(8, 17))) {
      return "存在未注销的工商信息！";
    }
    Element element = getSecurity(personGG.getGmsfhm(), month);
    if (!personGG.getDwmc().equals(getDWMC(element))) {
      return "单位名称不一致！";
    }
    String syys = getSyys(client, 2, personGG.getGmsfhm(), personGG.getGrbh(), personGG.getDjlsh());
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 2, personGG.getGmsfhm(), personGG.getGrbh(), personGG.getDjlsh(), month, month, syys);
    if (!creat.substring(0,1).equals("[")) {
      return month + "的补贴生成错误，请人工核查！原因为："+creat;
    }

    JSONArray jsStrs = JSONArray.fromObject(creat);
    String shiyebz = "", yiliaobz = "", yanglaobz = "", gangweibz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";

    JSONObject jsStr = jsStrs.getJSONObject(0);
    shiyebz = jsStr.getString("shiyebz");
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    gangweibz = jsStr.getString("gangweibz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");
    String save = saveSubsidy(client, personGG.getGmsfhm(), personGG.getGrbh(), personGG.getDjlsh(), month, month, syys, yanglaobz, yiliaobz, shiyebz, gangweibz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      return "保存错误，提示信息为：" + save;
    }
    System.out.println(personGG.getGmsfhm() + personGG.getGrxm() + "--" + month + "补贴录入成功");
    return personGG.getGmsfhm() + personGG.getGrxm() + "--" + month + "补贴录入成功";
  }

  /**
   * 保存
   *
   * @param client 登陆后的client
   * @param grxm   个人姓名
   * @param gmsfhm 公民身份号码
   * @param month  要保存的月份
   * @return 提示字符串
   */
  public static String save(CloseableHttpClient client, String grxm, String gmsfhm, String month) throws Exception {
    if(!checkID_B(gmsfhm)){
      System.out.println(gmsfhm + grxm + "--" + month + "身份证号码错误！");
      return "身份证号码错误！";
    }

    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      return "存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 2);
    if (datawindow.equals("")) {
      return "无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 2, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      return "无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 2, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      return "无法获取登记流水号！";
    }
    //System.out.println(djlsh);
    String dwmc = jsonObject.getString("dwmc");
    Element element = getSecurity(gmsfhm, month);
    if (!dwmc.equals(getDWMC(element))) {
      return "单位名称不一致！";
    }
    //System.out.println(dwmc);
    String syys = getSyys(client, 2, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 2, gmsfhm, grbh, djlsh, month, month, syys);
    if (creat.equals("[]")) {
      return month + "的补贴已录入";
    }
    //System.out.println(creat);
    JSONArray jsStrs = JSONArray.fromObject(creat);
    String shiyebz = "", yiliaobz = "", yanglaobz = "", gangweibz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";

    JSONObject jsStr = jsStrs.getJSONObject(0);
    shiyebz = jsStr.getString("shiyebz");
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    gangweibz = jsStr.getString("gangweibz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");
    String save = saveSubsidy(client, gmsfhm, grbh, djlsh, month, month, syys, yanglaobz, yiliaobz, shiyebz, gangweibz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      System.out.println(gmsfhm + grxm + "--" + month + save);
      return save;
    }
    System.out.println(gmsfhm + grxm + "--" + month + "补贴录入成功！");
    return month + "补贴录入成功！";
  }

}
