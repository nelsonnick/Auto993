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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Element;

import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.wts.check.commerce.getCommerce;
import static com.wts.check.security.getDWMC;
import static com.wts.check.security.getSecurity;
import static com.wts.service.common.*;
import static com.wts.util.Import.ImportQY;

public class qy {

    /**
     * 保存补贴
     *
     * @param client 登陆后的client
     * @param gmsfhm 公民身份号码
     * @param grbh 个人编号
     * @param djlsh 登记流水号
     * @param qsny 起始年月
     * @param zzny 终止年月
     * @param syys 剩余月数
     * @param yanglaobz 养老补助
     * @param yiliaobz 医疗补助
     * @param shiyebz 失业补助
     * @param gangweibz 岗位补助
     * @param sfyxyq 是否有效XX
     * @param sfyxffylbt 是否有效发放养老补贴
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
     * 获取全部企业人员
     *
     * @param client 登陆后的client
     * @return
     */
    public static List<PersonQY> goPersonQYs(CloseableHttpClient client) throws Exception {
        List<PersonQY> personQYs = new ArrayList<PersonQY>();
        String datawindow = getTableMark(client, 1);
        if (datawindow.equals("")) {
            return personQYs;
        }
        Integer total = Integer.parseInt(getDataTotal(client, datawindow, 1));
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
     * 检查补贴录入情况
     *
     * @param client   登陆后的client
     * @param personQY PersonQY的实例
     * @param month    要检查的月份
     * @return 提示字符串
     */
    public static String check(CloseableHttpClient client, PersonQY personQY, String month) throws Exception {
        if (getCommerce(client,personQY.getGmsfhm()) || getCommerce(client,personQY.getGmsfhm().substring(0, 6) + personQY.getGmsfhm().substring(8, 17))) {
            return "无法录入：存在未注销的工商信息！";
        }
        Element element = getSecurity(personQY.getGmsfhm(), month);
        if (!personQY.getDwmc().equals(getDWMC(element))) {
            return "无法录入：单位名称不一致！";
        }
        String syys = getSyys(client, 1,personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh());
        if (syys.equals("0")) {
            return "无法录入：剩余补贴月数为零！";
        }
        String creat = creatSubsidy(client, 1, personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh(), month, month, syys);
        if (creat.equals("[]")) {
            return "无法录入：" + month + "的补贴已录入";
        }
        return personQY.getGmsfhm() + personQY.getGrxm() + "--" + month + "补贴未录入";
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

        if (getCommerce(client,gmsfhm) || getCommerce(client,gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
            return "无法录入：存在未注销的工商信息！";
        }
        String datawindow = getTableMark(client, 1);
        if (datawindow.equals("")) {
            return "无法录入：无法打开窗口！";
        }
        //System.out.println(datawindow);
        String grbh = getDataInfo(client, 1, gmsfhm).getString("grbh");
        if (grbh.equals("")) {
            return "无法录入：无法获取个人编号！";
        }
        //System.out.println(grbh);
        JSONObject jsonObject = getDataDetail(client, 1, gmsfhm, grbh, datawindow);
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
        String syys = getSyys(client, 1,gmsfhm, grbh, djlsh);
        if (syys.equals("0")) {
            return "无法录入：剩余补贴月数为零！";
        }
        //System.out.println(syys);
        String creat = creatSubsidy(client, 1, gmsfhm, grbh, djlsh, month, month, syys);
        if (creat.equals("[]")) {
            return "无法录入：" + month + "的补贴已录入";
        }

        return gmsfhm + grxm + "--" + month + "补贴未录入";
    }

    /**
     * 保存
     *
     * @param client   登陆后的client
     * @param personQY PersonQY的实例
     * @param month    要保存的月份
     * @return 提示字符串
     */
    public static String save(CloseableHttpClient client, PersonQY personQY, String month) throws Exception {
        if (getCommerce(client,personQY.getGmsfhm()) || getCommerce(client,personQY.getGmsfhm().substring(0, 6) + personQY.getGmsfhm().substring(8, 17))) {
            return "存在未注销的工商信息！";
        }
        Element element = getSecurity(personQY.getGmsfhm(), month);
        if (!personQY.getDwmc().equals(getDWMC(element))) {
            return "单位名称不一致！";
        }
        String syys = getSyys(client, 1,personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh());
        if (syys.equals("0")) {
            return "剩余补贴月数为零！";
        }
        String creat = creatSubsidy(client, 1, personQY.getGmsfhm(), personQY.getGrbh(), personQY.getDjlsh(), month, month, syys);
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
        System.out.println(personQY.getGmsfhm() + personQY.getGrxm() + "--" + month + "补贴已保存");
        return personQY.getGmsfhm() + personQY.getGrxm() + "--" + month + "补贴已保存";
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

        if (getCommerce(client,gmsfhm) || getCommerce(client,gmsfhm.substring(0, 6) + gmsfhm.substring(8, 17))) {
            return "存在未注销的工商信息！";
        }
        String datawindow = getTableMark(client, 1);
        if (datawindow.equals("")) {
            return "无法打开窗口！";
        }
        //System.out.println(datawindow);
        String grbh = getDataInfo(client, 1, gmsfhm).getString("grbh");
        if (grbh.equals("")) {
            return "无法获取个人编号！";
        }
        //System.out.println(grbh);
        JSONObject jsonObject = getDataDetail(client, 1, gmsfhm, grbh, datawindow);
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
        String syys = getSyys(client, 1,gmsfhm, grbh, djlsh);
        if (syys.equals("0")) {
            return "剩余补贴月数为零！";
        }
        //System.out.println(syys);
        String creat = creatSubsidy(client, 1, gmsfhm, grbh, djlsh, month, month, syys);
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
        System.out.println(gmsfhm + grxm + "--" + month + "补贴已保存");
        return gmsfhm + grxm + "--" + month + "补贴已保存";
    }

}
