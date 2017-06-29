package com.wts.service;

import com.wts.entity.JG;
import com.wts.entity.PersonJS;
import com.wts.entity.PersonKN;
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

import static com.wts.service.Common.getDataAll;
import static com.wts.service.Common.getDataTotal;
import static com.wts.service.Common.getTableMark;
import static com.wts.service.LingHuo.getJG;

public class KunNan {
    /**
     * 获取家属数据
     *
     * @param client 登陆后的client
     * @param gmsfhm 公民身份号码
     * @param grxm   个人姓名
     * @param hzgrbh 户主个人编号
     * @param knrybh 困难人员编号
     * @param knrylb 困难人员类别 50零就业家庭
     * @param xb     性别
     * @return 家属数据的JSON字符串
     */
    public static String getFamily(CloseableHttpClient client, String gmsfhm, String grxm, String hzgrbh, String knrybh, String knrylb, String xb) throws Exception {
        if (!knrybh.equals("50")) {
            return "[]";
        }
        URI u = new URIBuilder()
                .setScheme("http")
                .setHost("10.153.50.108:7001")
                .setPath("/lemis3/lemis3SuccorAndDifficties.do")
                .setParameter("method", "queryFamilyInfo")
                .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s xg=\"1\"/><s gmsfhm=\"" + gmsfhm + "\"/><s grxm=\"" + grxm + "\"/><s hzgrbh=\"" + hzgrbh + "\"/><s knrybh=\"" + knrybh + "\"/><s knrylb=\"" + knrylb + "\"/><s xb=\"" + xb + "\"/></p>")
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
        return res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1);
    }

    /**
     * 获取全部困难人员
     *
     * @param client 登陆后的client
     * @return
     */
    public static List<PersonKN> goPersonKNs(CloseableHttpClient client) throws Exception {
        List<PersonKN> PersonKNs = new ArrayList<PersonKN>();
        String datawindow = getTableMark(client, 4);
        if (datawindow.equals("")) {
            return PersonKNs;
        }
        JG jg = getJG(client).get(0);
        Integer total = Integer.parseInt(getDataTotal(client, datawindow, 4, jg.getJbjgbh(), jg.getJgbh(), jg.getJgmc()));
        String mergePerson = getDataAll(client, datawindow, total);
        JSONArray jsStrs = JSONArray.fromObject(mergePerson);
        if (jsStrs.size() > 0) {
            for (int j = 0; j < jsStrs.size() + 1; j++) {
                JSONObject jsStr = jsStrs.getJSONObject(j);
                PersonKN p = new PersonKN();
                p.setCjrmc(jsStr.getString("cjrmc"));
                p.setYxbz(jsStr.getString("yxbz"));
                p.setRdrq(jsStr.getString("rdrq"));
                p.setTcjyyzdxyy(jsStr.getString("tcjyyzdxyy"));
                p.setSpzt(jsStr.getString("spzt"));
                p.setKnrybh(jsStr.getString("knrybh"));
                p.setSfyzcg(jsStr.getString("sfyzcg"));
                p.setKnrylb(jsStr.getString("knrylb"));
                p.setYzdxlx(jsStr.getString("yzdxlx"));
                p.setCjjgmc(jsStr.getString("cjjgmc"));
                p.setGmsfhm(jsStr.getString("gmsfhm"));
                p.setGrxm(jsStr.getString("grxm"));
                p.setLxdh(jsStr.getString("lxdh"));
                p.setPxyy(jsStr.getString("pxyy"));
                p.setXb(jsStr.getString("xb"));
                p.setCsrq(jsStr.getString("csrq"));
                p.setJyyy(jsStr.getString("jyyy"));
                p.setGrbh(jsStr.getString("grbh"));
                PersonKNs.add(p);
            }
        }
        return PersonKNs;
    }

    /**
     * 获取全部家庭成员
     *
     * @param client    登陆后的client
     * @param PersonKNs 困难人员列表
     * @return
     */
    public static List<PersonJS> goPersonJSs(CloseableHttpClient client, List<PersonKN> PersonKNs) throws Exception {
        List<PersonJS> PersonJSs = new ArrayList<PersonJS>();
        for (PersonKN personkn : PersonKNs) {
            String mergePerson = getFamily(client, personkn.getGmsfhm(), personkn.getGrxm(), personkn.getGrbh(), personkn.getKnrybh(), personkn.getKnrylb(), personkn.getXb());
            JSONArray jsStrs = JSONArray.fromObject(mergePerson);
            if (jsStrs.size() > 0) {
                for (int j = 0; j < jsStrs.size() + 1; j++) {
                    JSONObject jsStr = jsStrs.getJSONObject(j);
                    PersonJS p = new PersonJS();
                    p.setLdnl(jsStr.getString("ldnl"));
                    p.setJsgmsfhm(jsStr.getString("jsgmsfhm"));
                    p.setBz(jsStr.getString("bz"));
                    p.setHzgx(jsStr.getString("hzgx"));
                    p.setJslxdh(jsStr.getString("jslxdh"));
                    p.setJsjkzk(jsStr.getString("jsjkzk"));
                    p.setJscsrq(jsStr.getString("jscsrq"));
                    p.setJsxm(jsStr.getString("jsxm"));
                    p.setJsryzt(jsStr.getString("jsryzt"));
                    p.setJswhcd(jsStr.getString("jswhcd"));
                    p.setTxdz(jsStr.getString("txdz"));
                    p.setJsxh(jsStr.getString("jsxh"));
                    p.setJsysr(jsStr.getString("jsysr"));
                    p.setJsgzdw(jsStr.getString("jsgzdw"));
                    p.setJyyy(jsStr.getString("jyyy"));
                    p.setJsxb(jsStr.getString("jsxb"));
                    p.setHzgmsfhm(personkn.getGmsfhm());
                    p.setHzgrxm(personkn.getGrxm());
                    PersonJSs.add(p);
                }
            }
        }
        return PersonJSs;
    }
}
