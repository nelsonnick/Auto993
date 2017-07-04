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

import static com.wts.function.commerce.Commerce.getCommerce;
import static com.wts.function.security.Security.getSecurity;
import static com.wts.function.security.Security.getYanglao;
import static com.wts.function.security.Security.getYiliao;
import static com.wts.service.Common.*;
import static com.wts.service.Common.creatSubsidy;
import static com.wts.util.IDKit.checkID_B;
import static com.wts.util.IDKit.checkRetire;

public class LingHuo {

  /**
   * 获取机构信息
   */
  public static List<JG> getJG(CloseableHttpClient client) throws Exception {
    List<JG> jgs = new ArrayList<JG>();
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
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1));
      for (int i = 0; i < jsStrs.size(); i++) {
        JSONObject jsStr = jsStrs.getJSONObject(i);
        JG jg = new JG();
        jg.setJbjgbh(jsStr.getString("jbjgbh"));
        jg.setJgbh(jsStr.getString("jgbh"));
        jg.setJgmc(jsStr.getString("jgmc"));
        jgs.add(jg);
      }
    }
    return jgs;
  }

  /**
   * 获取全部灵活人员
   *
   * @param client 登陆后的client
   * @return
   */
  public static List<PersonLH> goPersonLHs(CloseableHttpClient client) throws Exception {
    List<PersonLH> goPersonLHs = new ArrayList<PersonLH>();
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      return goPersonLHs;
    }
    JG jg = getJG(client).get(0);
    Integer total = Integer.parseInt(getDataTotal(client, datawindow, 3, jg.getJbjgbh(), jg.getJgbh(), jg.getJgmc()));
    String mergePerson = getDataAll(client, datawindow, total);
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
   * @param sfyxyq      是否有效XX
   * @param sfyxffylbt  是否有效发放养老补贴
   * @param sfyxffyilbt 是否有效发放医疗补贴
   * @return
   */
  public static String saveSubsidy(CloseableHttpClient client, String gmsfhm, String grbh, String djlsh, String qsny, String zzny, String syys, String yanglaobz, String yiliaobz, String sfyxyq, String sfyxffylbt, String sfyxffyilbt) throws Exception {
    String grxm = "";
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "saveBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s grbh=\"" + grbh + "\" msg=\"\" grxm=\"" + grxm + "\" gmsfhm=\"" + gmsfhm + "\" djlsh=\"" + djlsh + "\" btrylb=\"03\" syys=\"" + syys + "\" qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" /><d k=\"dw_xzbt\"><r qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" sfyxyq=\"" + sfyxyq + "\" sfyxffylbt=\"" + sfyxffylbt + "\" sfyxffyilbt=\"" + sfyxffyilbt + "\" yanglaobz=\"" + yanglaobz + "\" yiliaobz=\"" + yiliaobz + "\" /></d></p>")
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
   * @param personLH PersonLH的实例
   * @param qsny     起始年月
   * @param zzny     终止年月
   * @return 提示字符串
   */
  public static String check(CloseableHttpClient client, PersonLH personLH, String qsny, String zzny) throws Exception {
    if (!checkID_B(personLH.getGmsfhm())) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (!checkRetire(personLH.getGmsfhm(), qsny)) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, personLH.getGmsfhm()) || getCommerce(client, personLH.getGmsfhm().substring(0, 6) + personLH.getGmsfhm().substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    if (!getJG(client).get(0).getJgmc().equals(personLH.getCjjgmc())) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 1, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh());
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), qsny, zzny, syys);
    if (creat.equals("[]")) {
      return "无法录入：" + qsny + "-" + zzny + "的补贴，请人工核查";
    }
    return personLH.getGmsfhm() + personLH.getGrxm() + "--" + qsny + "-" + zzny + "补贴未录入";
  }

  /**
   * 检查补贴录入情况
   *
   * @param client   登陆后的client
   * @param personLH PersonLH的实例
   * @param month    要检查的月份
   * @return 提示字符串
   */
  public static String check(CloseableHttpClient client, PersonLH personLH, String month) throws Exception {
    if (!checkID_B(personLH.getGmsfhm())) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--" + month + "身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (!checkRetire(personLH.getGmsfhm(), month)) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, personLH.getGmsfhm()) || getCommerce(client, personLH.getGmsfhm().substring(0, 6) + personLH.getGmsfhm().substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    if (!getJG(client).get(0).getJgmc().equals(personLH.getCjjgmc())) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 1, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh());
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), month, month, syys);
    if (creat.equals("[]")) {
      return "无法录入：" + month + "的补贴，请人工核查";
    }
    return personLH.getGmsfhm() + personLH.getGrxm() + "--" + month + "补贴未录入";
  }

  /**
   * 检查补贴录入情况
   *
   * @param client 登陆后的client
   * @param grxm   个人姓名
   * @param gmsfhm 公民身份号码
   * @param qsny   起始年月
   * @param zzny   终止年月
   * @return 提示字符串
   */
  public static String check(CloseableHttpClient client, String grxm, String gmsfhm, String qsny, String zzny) throws Exception {
    if (!checkID_B(gmsfhm)) {
      System.out.println(gmsfhm + grxm + "--身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (!checkRetire(gmsfhm, qsny)) {
      System.out.println(gmsfhm + grxm + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      return "无法录入：无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 3, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      return "无法录入：无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 3, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      return "无法录入：无法获取登记流水号！";
    }
    String cjjgmc = jsonObject.getString("cjjgmc");
    if (!getJG(client).get(0).getJgmc().equals(cjjgmc)) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 1, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 3, gmsfhm, grbh, djlsh, qsny, zzny, syys);
    if (!creat.substring(0, 1).equals("[")) {
      return qsny + "-" + zzny + "的补贴生成错误，请人工核查！原因为：" + creat;
    }

    return gmsfhm + grxm + "--" + qsny + "-" + zzny + "补贴未录入";
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
    if (!checkID_B(gmsfhm)) {
      System.out.println(gmsfhm + grxm + "--" + month + "身份证号码错误！");
      return "无法录入：身份证号码错误！";
    }
    if (!checkRetire(gmsfhm, month)) {
      System.out.println(gmsfhm + grxm + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      return "无法录入：存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      return "无法录入：无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 3, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      return "无法录入：无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 3, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      return "无法录入：无法获取登记流水号！";
    }
    String cjjgmc = jsonObject.getString("cjjgmc");
    if (!getJG(client).get(0).getJgmc().equals(cjjgmc)) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 1, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      return "无法录入：剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 3, gmsfhm, grbh, djlsh, month, month, syys);
    if (!creat.substring(0, 1).equals("[")) {
      return month + "的补贴生成错误，请人工核查！原因为：" + creat;
    }

    return gmsfhm + grxm + "--" + month + "补贴未录入";
  }

  /**
   * 保存
   *
   * @param client   登陆后的client
   * @param personLH PersonLH的实例
   * @param month    要保存的月份
   * @return 提示字符串
   */
  public static String save(CloseableHttpClient client, PersonLH personLH, String month) throws Exception {

    if (!checkID_B(personLH.getGmsfhm())) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--" + month + "身份证号码错误！");
      return "身份证号码错误！";
    }
    if (!checkRetire(personLH.getGmsfhm(), month)) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, personLH.getGmsfhm()) || getCommerce(client, personLH.getGmsfhm().substring(0, 6) + personLH.getGmsfhm().substring(8, 17))) {
      return "存在未注销的工商信息！";
    }
    if (!getJG(client).get(0).getJgmc().equals(personLH.getCjjgmc())) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh());
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), month, month, syys);
    if (!creat.substring(0, 1).equals("[")) {
      return month + "的补贴生成错误，请人工核查！原因为：" + creat;
    }

    JSONArray jsStrs = JSONArray.fromObject(creat);
    String yiliaobz = "", yanglaobz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";

    JSONObject jsStr = jsStrs.getJSONObject(0);
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");


    if (!yanglaobz.equals(getYanglao(getSecurity(personLH.getGmsfhm(), month, "A")))) {
      return "养老补贴金额错误！";
    }
    if (!yiliaobz.equals(getYiliao(getSecurity(personLH.getGmsfhm(), month, "C")))) {
      return "医疗补贴金额错误！";
    }


    String save = saveSubsidy(client, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), month, month, syys, yanglaobz, yiliaobz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      return "保存错误，提示信息为：" + save;
    }
    System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--" + month + "补贴录入成功！");
    return personLH.getGmsfhm() + personLH.getGrxm() + "--" + month + "补贴录入成功！";
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

    if (!checkID_B(gmsfhm)) {
      System.out.println(gmsfhm + grxm + "--" + month + "身份证号码错误！");
      return "身份证号码错误！";
    }
    if (!checkRetire(gmsfhm, month)) {
      System.out.println(gmsfhm + grxm + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      System.out.println(gmsfhm + grxm + "--" + month + "存在未注销的工商信息！");
      return "存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      System.out.println(gmsfhm + grxm + "--" + month + "无法打开窗口！");
      return "无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 3, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      System.out.println(gmsfhm + grxm + "--" + month + "无法获取个人编号！");
      return "无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 3, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      System.out.println(gmsfhm + grxm + "--" + month + "无法获取登记流水号！");
      return "无法获取登记流水号！";
    }
    //System.out.println(djlsh);
    String cjjgmc = jsonObject.getString("cjjgmc");
    if (!getJG(client).get(0).getJgmc().equals(cjjgmc)) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 3, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      System.out.println(gmsfhm + grxm + "--" + month + "剩余补贴月数为零！");
      return "剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 3, gmsfhm, grbh, djlsh, month, month, syys);
    if (!creat.substring(0, 1).equals("[")) {
      System.out.println(gmsfhm + grxm + "--" + month + "的补贴生成错误，请人工核查！原因为：" + creat);
      return month + "的补贴生成错误，请人工核查！原因为：" + creat;
    }
    //System.out.println(creat);
    JSONArray jsStrs = JSONArray.fromObject(creat);
    String yiliaobz = "", yanglaobz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";

    JSONObject jsStr = jsStrs.getJSONObject(0);
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");

    if (!yanglaobz.equals(getYanglao(getSecurity(gmsfhm, month, "A")))) {
      return "养老补贴金额错误！";
    }
    if (!yiliaobz.equals(getYiliao(getSecurity(gmsfhm, month, "C")))) {
      return "医疗补贴金额错误！";
    }


    String save = saveSubsidy(client, gmsfhm, grbh, djlsh, month, month, syys, yanglaobz, yiliaobz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      System.out.println(gmsfhm + grxm + "--" + month + save);
      return save;
    }
    System.out.println(gmsfhm + grxm + "--" + month + "补贴录入成功！");
    return month + "补贴录入成功！";
  }

  /**
   * 保存
   *
   * @param client   登陆后的client
   * @param personLH PersonLH的实例
   * @param qsny     起始年月
   * @param zzny     终止年月
   * @return 提示字符串
   */
  public static String save(CloseableHttpClient client, PersonLH personLH, String qsny, String zzny) throws Exception {

    if (!checkID_B(personLH.getGmsfhm())) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--身份证号码错误！");
      return "身份证号码错误！";
    }
    if (!checkRetire(personLH.getGmsfhm(), qsny)) {
      System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, personLH.getGmsfhm()) || getCommerce(client, personLH.getGmsfhm().substring(0, 6) + personLH.getGmsfhm().substring(8, 17))) {
      return "存在未注销的工商信息！";
    }
    if (!getJG(client).get(0).getJgmc().equals(personLH.getCjjgmc())) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh());
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 3, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), qsny, zzny, syys);
    if (!creat.substring(0, 1).equals("[")) {
      return qsny + "-" + zzny + "的补贴生成错误，请人工核查！原因为：" + creat;
    }

    JSONArray jsStrs = JSONArray.fromObject(creat);
    String yiliaobz = "", yanglaobz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";

    JSONObject jsStr = jsStrs.getJSONObject(0);
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");


    if (!yanglaobz.equals(getYanglao(getSecurity(personLH.getGmsfhm(), qsny, "A")))) {
      return "养老补贴金额错误！";
    }
    if (!yiliaobz.equals(getYiliao(getSecurity(personLH.getGmsfhm(), qsny, "C")))) {
      return "医疗补贴金额错误！";
    }

    String save = saveSubsidy(client, personLH.getGmsfhm(), personLH.getGrbh(), personLH.getDjlsh(), qsny, zzny, syys, yanglaobz, yiliaobz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      return "保存错误，提示信息为：" + save;
    }
    System.out.println(personLH.getGmsfhm() + personLH.getGrxm() + "--" + qsny + "-" + zzny + "补贴录入成功！");
    return personLH.getGmsfhm() + personLH.getGrxm() + "--" + qsny + "-" + zzny + "补贴录入成功！";
  }

  /**
   * 保存
   *
   * @param client 登陆后的client
   * @param grxm   个人姓名
   * @param gmsfhm 公民身份号码
   * @param qsny   起始年月
   * @param zzny   终止年月
   * @return 提示字符串
   */
  public static String save(CloseableHttpClient client, String grxm, String gmsfhm, String qsny, String zzny) throws Exception {

    if (!checkID_B(gmsfhm)) {
      System.out.println(gmsfhm + grxm + "--" + "身份证号码错误！");
      return "身份证号码错误！";
    }
    if (!checkRetire(gmsfhm, qsny)) {
      System.out.println(gmsfhm + grxm + "--已超龄！");
      return "无法录入：已超龄！";
    }
    if (getCommerce(client, gmsfhm) || getCommerce(client, gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
      System.out.println(gmsfhm + grxm + "--存在未注销的工商信息！");
      return "存在未注销的工商信息！";
    }
    String datawindow = getTableMark(client, 3);
    if (datawindow.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法打开窗口！");
      return "无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 3, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法获取个人编号！");
      return "无法获取个人编号！";
    }
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 3, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法获取登记流水号！");
      return "无法获取登记流水号！";
    }
    //System.out.println(djlsh);
    String cjjgmc = jsonObject.getString("cjjgmc");
    if (!getJG(client).get(0).getJgmc().equals(cjjgmc)) {
      return "无法录入：当前用户所属机构与指定人员创建机构不一致！";
    }
    String syys = getSyys(client, 3, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      System.out.println(gmsfhm + grxm + "--剩余补贴月数为零！");
      return "剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 3, gmsfhm, grbh, djlsh, qsny, zzny, syys);
    if (!creat.substring(0, 1).equals("[")) {
      System.out.println(gmsfhm + grxm + "--" + qsny + "-" + zzny + "的补贴生成错误，请人工核查！原因为：" + creat);
      return qsny + "-" + zzny + "的补贴生成错误，请人工核查！原因为：" + creat;
    }
    //System.out.println(creat);
    JSONArray jsStrs = JSONArray.fromObject(creat);
    String yiliaobz = "", yanglaobz = "", sfyxyq = "", sfyxffylbt = "", sfyxffyilbt = "";
    JSONObject jsStr = jsStrs.getJSONObject(0);
    yiliaobz = jsStr.getString("yiliaobz");
    yanglaobz = jsStr.getString("yanglaobz");
    sfyxyq = jsStr.getString("sfyxyq");
    sfyxffylbt = jsStr.getString("sfyxffylbt");
    sfyxffyilbt = jsStr.getString("sfyxffyilbt");

    if (!yanglaobz.equals(getYanglao(getSecurity(gmsfhm, qsny, "A")))) {
      System.out.println(gmsfhm + grxm + "--养老补贴金额错误！");
      return "养老补贴金额错误！";
    }
    if (!yiliaobz.equals(getYiliao(getSecurity(gmsfhm, qsny, "C")))) {
      System.out.println(gmsfhm + grxm + "--医疗补贴金额错误！");
      return "医疗补贴金额错误！";
    }

    String save = saveSubsidy(client, gmsfhm, grbh, djlsh, qsny, zzny, syys, yanglaobz, yiliaobz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      System.out.println(gmsfhm + grxm + "--" + qsny + "-" + zzny + save);
      return save;
    }
    System.out.println(gmsfhm + grxm + "--" + qsny + "-" + zzny + "补贴录入成功！");
    return qsny + "-" + zzny + "补贴录入成功！";
  }

}
