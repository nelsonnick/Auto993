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

import static com.wts.service.common.getDataAll;
import static com.wts.service.common.getDataTotal;
import static com.wts.service.common.getTableMark;

public class lh {

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
   * 获取全部灵活人员
   * @param client 登陆后的client
   * @return
   */
  public static List<PersonLH> goPersonLHs(CloseableHttpClient client) throws Exception {
    List<PersonLH> goPersonLHs = new ArrayList<PersonLH>();
    String datawindow = getTableMark(client,2);
    if (datawindow.equals("")) {
      return goPersonLHs;
    }
    JG jg= getJG(client);
    Integer total = Integer.parseInt(getDataTotal(client,datawindow, 2,jg.getJbjgbh(),jg.getJgbh(),jg.getJgmc()));
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





}
