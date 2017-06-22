package com.wts.check;

import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.wts.util.SecurityKit.getSecurityType;

public class security {

    /**
     * @param type 1:正常缴费 2:补缴 3:补缴时间
     * @param id   身份证号码
     * @return 拼接好的XML字符串
     */
    public static String getXML(Integer type, String id) {
        StringBuffer sb = new StringBuffer();
        String XML_HEADER = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
        sb.append(XML_HEADER);
        sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
        sb.append("<soap:Header>");
        sb.append("<in:system xmlns:in=\"http://www.molss.gov.cn/\">");
        sb.append("<para usr=\"\"/>");
        sb.append("<para pwd=\"\"/>");
        sb.append("<para funid=\"F00.00.0C\"/>");
        sb.append("</in:system>");
        sb.append("</soap:Header>");
        sb.append("<soap:Body>");
        sb.append("<in:business xmlns:in=\"http://www.molss.gov.cn/\">");
        if (type == 1) {
            sb.append("<para sqlstr=\"select '01' sblb,xzbz,jfjs,dwjfjs,jfrylb,si.orgn_natl.dwbh dwbh,dwmc,substr(qsny,1,4)||'.'||substr(qsny,5) qsny,substr(zzny,1,4)||'.'||substr(zzny,5) zzny,'计划' zdlsh from si.emp_plan,si.orgn_natl where grbh='" + id + "' and si.emp_plan.dwbh=si.orgn_natl.dwbh\"/>");
        } else if (type == 2) {
            sb.append("<para sqlstr=\"select sblb,xzbz,jfjs,dwjfjs,si.orgn_natl.dwbh dwbh,dwmc,substr(qsny,1,4)||'.'||substr(qsny,5) qsny,substr(zzny,1,4)||'.'||substr(zzny,5) zzny,nvl(zdlsh,'未填单据') zdlsh from si.emp_add,si.orgn_natl where grbh='" + id + "' and si.emp_add.dwbh=si.orgn_natl.dwbh order by qsny,xzbz\"/>");
        } else if (type == 3) {
            sb.append("<para sqlstr=\"select zdlsh,qrsj from si.bill_genl   where djzt='2' and zdlsh in         (select zdlsh from si.emp_add where grbh='" + id + "' and zdlsh is not null)\"/>");
        } else {
            sb.append("");
        }
        sb.append("<para g_com_gzrybh=\"cxyh\"/>");
        sb.append("<para g_com_gzryxm=\"通用查询\"/>");
        sb.append("<para g_com_sbjgbh=\"370100\"/>");
        sb.append("<para g_com_xzbz=\"A\"/>");
        sb.append("<para g_com_passwd=\"246325262611261127792835\"/>");
        sb.append("<para g_com_passwd_md5=\"be91ffca1d2a70fc3c3880851dba5903\"/>");
        sb.append("<para g_com_app=\"公共业务子系统\"/>");
        sb.append("<para g_com_xtlb=\"001\"/>");
        sb.append("<para g_com_mac=\"00-00-00-00-00-00\"/>");
        sb.append("</in:business>");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        // 返回String格式
        return sb.toString();
    }

    /**
     * @param id    身份证号码
     * @param month 6位年月
     * @return
     */
    public static Element getSecurity(String id, String month) throws Exception {
        Element element = null;

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/xml;charset=GBK");
        RequestBody body1 = RequestBody.create(mediaType, getXML(1, id));
        RequestBody body2 = RequestBody.create(mediaType, getXML(2, id));
        RequestBody body3 = RequestBody.create(mediaType, getXML(3, id));
        Request request1 = new Request.Builder()
                .url("http://10.153.50.123:80/lbs/MainServlet")
                .post(body1)
                .addHeader("content-type", "text/xml;charset=GBK")
                .addHeader("accept", "*.*;")
                .addHeader("host", "dareway")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "71a5c099-7230-b17c-83cd-8a87479aae09")
                .build();
        Request request2 = new Request.Builder()
                .url("http://10.153.50.123:80/lbs/MainServlet")
                .post(body2)
                .addHeader("content-type", "text/xml;charset=GBK")
                .addHeader("accept", "*.*;")
                .addHeader("host", "dareway")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "71a5c099-7230-b17c-83cd-8a87479aae09")
                .build();
        Request request3 = new Request.Builder()
                .url("http://10.153.50.123:80/lbs/MainServlet")
                .post(body3)
                .addHeader("content-type", "text/xml;charset=GBK")
                .addHeader("accept", "*.*;")
                .addHeader("host", "dareway")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "71a5c099-7230-b17c-83cd-8a87479aae09")
                .build();

        Response response1 = client.newCall(request1).execute();
        Response response2 = client.newCall(request2).execute();
        Response response3 = client.newCall(request3).execute();
        Document document1 = DocumentHelper.parseText(response1.body().string());
        Document document2 = DocumentHelper.parseText(response2.body().string());
        Document document3 = DocumentHelper.parseText(response3.body().string());
        //获取根节点对象
        Element rootElement1 = document1.getRootElement();
        Element rootElement2 = document2.getRootElement();
        Element rootElement3 = document3.getRootElement();
        //获取子节点
        Element resultset1 = rootElement1.element("Body").element("business").element("resultset");
        Element resultset2 = rootElement2.element("Body").element("business").element("resultset");
        Element resultset3 = rootElement3.element("Body").element("business").element("resultset");

        List<Element> elements2 = new ArrayList<Element>();
        List<Element> elements3 = new ArrayList<Element>();

        for (Iterator it = resultset2.elementIterator(); it.hasNext(); ) {
            elements2.add((Element) it.next());
        }
        for (Iterator it = resultset3.elementIterator(); it.hasNext(); ) {
            elements3.add((Element) it.next());
        }
        for (Iterator it = resultset1.elementIterator(); it.hasNext(); ) {
            Element e = (Element) it.next();
            String qsny = e.attributeValue("qsny").substring(0, 4) + e.attributeValue("qsny").substring(5, 7);
            String zzny = e.attributeValue("zzny").substring(0, 4) + e.attributeValue("zzny").substring(5, 7);
            if (Integer.parseInt(qsny) <= Integer.parseInt(month) && Integer.parseInt(zzny) >= Integer.parseInt(month)) {
                element = e;
                break;
            }
        }
        if (element == null) {
            for (int m = 0; m < elements2.size(); m++) {
                String qsny = elements2.get(m).attributeValue("qsny").substring(0, 4) + elements2.get(m).attributeValue("qsny").substring(5, 7);
                String zzny = elements2.get(m).attributeValue("zzny").substring(0, 4) + elements2.get(m).attributeValue("zzny").substring(5, 7);
                if (Integer.parseInt(qsny) <= Integer.parseInt(month) && Integer.parseInt(zzny) >= Integer.parseInt(month)) {
                    for (int n = 0; n < elements3.size(); n++) {
                        if (elements2.get(m).attributeValue("zdlsh").equals(elements3.get(n).attributeValue("zdlsh"))) {
                            if (elements3.get(n).attributeValue("qrsj").substring(0, 6).equals(month)) {
                                element = elements2.get(m);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return element;
    }

    /**
     * @param id    身份证号码
     * @param month 6位年月
     * @return 单位名称
     */
    public static String getDWMC(String id, String month) throws Exception {
        Element element = getSecurity(id, month);
        if (element == null) {
            return "指定月份无缴费单位";
        } else {
            return element.attributeValue("dwmc");
        }
    }

    /**
     * @param id    身份证号码
     * @param month 6位年月
     * @return 交费类型
     */
    public static String getJFLX(String id, String month) throws Exception {
        Element element = getSecurity(id, month);
        if (element == null) {
            return "未交费";
        } else {
            return getSecurityType(element.attributeValue("dwbh"));
        }
    }
}
