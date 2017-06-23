package com.wts.service;

import com.wts.entity.PersonQY;
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

import static com.wts.service.common.*;

public class qy {

  /**
   * 保存补贴
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
   * 获取全部企业人员
   * @param client 登陆后的client
   * @return
   */
  public static List<PersonQY> goPersonQYs(CloseableHttpClient client) throws Exception {
    List<PersonQY> personQYs = new ArrayList<PersonQY>();
    String datawindow = getTableMark(client,1);
    if (datawindow.equals("")) {
      return personQYs;
    }
    Integer total = Integer.parseInt(getDataTotal(client, datawindow,1));
    String mergePerson = getDataAll(client, datawindow, total);
    JSONArray jsStrs = JSONArray.fromObject(mergePerson);
    if (jsStrs.size() > 0) {
      for (int j = 0; j < jsStrs.size() + 1; j++) {
        JSONObject jsStr = jsStrs.getJSONObject(j);
        PersonQY p = new PersonQY();
        p.setDwmc(jsStr.getString("dwmc"));
        p.setQyrdbh(jsStr.getString("qyrdbh"));
        p.setDwbh(jsStr.getString("dwbh"));
        p.setDjlsh(jsStr.getString("djlsh"));
        p.setWtgyy(jsStr.getString("wtgyy"));
        p.setKnrybh(jsStr.getString("knrybh"));
        p.setTcrq(jsStr.getString("tcrq"));
        p.setSpzt(jsStr.getString("spzt"));
        p.setCjjg(jsStr.getString("cjjg"));
        p.setCjrq(jsStr.getString("cjrq"));
        p.setGmsfhm(jsStr.getString("gmsfhm"));
        p.setGrxm(jsStr.getString("grxm"));
        p.setXb(jsStr.getString("xb"));
        p.setCsrq(jsStr.getString("csrq"));
        p.setCjr(jsStr.getString("cjr"));
        p.setSfyba(jsStr.getString("sfyba"));
        p.setGrbh(jsStr.getString("grbh"));
        personQYs.add(p);
      }
    }
    return personQYs;
  }

  /**
   * 保存
   */
  public static String goSave(CloseableHttpClient client, PersonQY personQY, String month) throws Exception {
    String syys = getSyys(client, personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh());
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    String creat = creatSubsidy(client, 1,personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh(), month, month,syys);
    if (creat.equals("[]")) {
      return month + "的补贴已录入";
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
    String save = saveSubsidy(client, personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh(), month, month, syys, yanglaobz, yiliaobz, shiyebz, gangweibz, sfyxyq, sfyxffylbt, sfyxffyilbt);
    if (!save.equals("保存成功！")) {
      return "保存错误，提示信息为：" + save;
    }
    return personQY.getGmsfhm() + personQY.getGrxm() + "--" + month + "补贴已保存";
  }

  /**
   * 保存
   */
  public static String goSave(CloseableHttpClient client, String grxm, String gmsfhm, String month) throws Exception {

    String datawindow = getTableMark(client,1);
    if (datawindow.equals("")) {
      return "无法打开窗口！";
    }
    //System.out.println(datawindow);
    String grbh = getGrbh(client,1, gmsfhm);
    if (grbh.equals("")) {
      return "无法获取个人编号！";
    }
    //System.out.println(grbh);
    String djlsh = getDjlsh(client, 1,gmsfhm, grbh, datawindow);
    if (djlsh.equals("")) {
      return "无法获取登记流水号！";
    }
    //System.out.println(djlsh);
    String syys = getSyys(client, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      return "剩余补贴月数为零！";
    }
    //System.out.println(syys);
    String creat = creatSubsidy(client, 1,gmsfhm, grbh, djlsh, month, month,syys);
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
      return save;
    }
    return gmsfhm + grxm + "--" + month + "补贴已保存";
  }

}
