package com.wts.service;

import com.wts.entity.DW;
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

import java.net.URI;


public class Common {

  public static String userid = "";
  public static String passwd = "";

  /**
   * 登录系统
   *
   * @param userid 用户名
   * @param passwd 密码
   * @return 登陆后的client实例
   */
  public static CloseableHttpClient login(String userid, String passwd) throws Exception {
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/logon.do")
            .setParameter("method", "doLogon")
            .setParameter("userid", userid)
            .setParameter("passwd", Kit.getMD5(passwd))
            .setParameter("userLogSign", "0")
            .setParameter("passWordLogSign", "0")
            .setParameter("screenHeight", "768")
            .setParameter("screenWidth", "1024")
            .setParameter("mode", "")
            .build();
    HttpPost post = new HttpPost(u);
    // 创建默认的httpClient实例.
    CloseableHttpClient client = HttpClients.createDefault();
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String str = EntityUtils.toString(entity, "UTF-8");
    Thread.sleep(1000);
    if (str.contains("您输入的密码不正确")) {
      System.out.println("登录失败：" + str);
      return null;
    } else {
      System.out.println("登录成功");
      return client;
    }
  }

  /**
   * 打开窗体
   *
   * @param client 登陆后的client
   * @param type   1企业吸纳2公益岗位3灵活就业4困难人员
   * @return 窗体的tableMark值
   */
  public static String getTableMark(CloseableHttpClient client, int type) throws Exception {
    String path, method;
    switch (type) {
      case 1:  // 就业援助子系统-社补岗补管理-企业吸纳-企业吸纳人员管理
        path = "/lemis3/lemis3SuccorOrgAdmit.do";
        method = "enterOrgAdmitPerManage";
        break;
      case 2:  // 就业援助子系统-社补岗补管理-公益性岗位-公岗安置管理
        path = "/lemis3/lemis3MeritStation.do";
        method = "enterMeritResetManage";
        break;
      case 3:  // 就业援助子系统-社补岗补管理-灵活就业-灵活就业登记管理
        path = "/lemis3/lemis3SuccorFlexibleEmp.do";
        method = "enterFlexEmpPerManage";
        break;
      case 4:  // 就业援助子系统-困难人员-困难人员管理
        path = "/lemis3/lemis3SuccorAndDifficties.do";
        method = "enterDiffManage";
        break;
      default:
        return "";
    }

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
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
   * 关闭窗体
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   */
  public static void closeWindow(CloseableHttpClient client, String tableMark) throws Exception {
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/cleanSession.do")
            .setParameter("tableMark", tableMark)
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
  }

  /**
   * 获取页码总数
   * 注：网页上是每页16条记录，但API返回的是每页12条记录，所以页码总数并不相同
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param type      1企业吸纳2公益岗位3灵活就业4困难人员
   * @return 页码总数
   */
  public static String getDataTotal(CloseableHttpClient client, String tableMark, int type) throws Exception {
    String path, method, _xmlString;
    switch (type) {
      case 1:  // 企业吸纳
        path = "/lemis3/lemis3SuccorOrgAdmit.do";
        method = "queryAdmitPer";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s dwbh=\"\" dwmc=\"\" sftc=\"0\" gmsfhm=\"\" grxm=\"\" grbh=\"\" sfbl=\"\" sfyba=\"\" /></p>";
        break;
      case 2:  // 公益岗位
        path = "/lemis3/lemis3MeritStation.do";
        method = "queryMeritStaResettlement";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" sfyba=\"\" dwbh=\"\" dwmc=\"\" gwmc=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" sfbl=\"\" tcqsrq=\"\" tczzrq=\"\" spzt=\"\" /></p>";
        break;
      case 3:  // 灵活就业
        path = "/lemis3/lemis3SuccorFlexibleEmp.do";
        method = "queryFlexEmp";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" spzt=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" jbjgbh=\"\" jgbh=\"\" jgmc=\"\" sfbl=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" sfyba=\"\" /></p>";
        break;
      case 4:  // 困难人员
        path = "/lemis3/lemis3SuccorAndDifficties.do";
        method = "queryDiffInfo";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" xb=\"\" qsrq=\"\" zzrq=\"\" spzt=\"\" jbjgbh=\"\" jgbh=\"\" jgmc=\"\" knrylb=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" /></p>";
        break;
      default:
        return "";
    }
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", tableMark)
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String pageStart = "记录&nbsp;&nbsp;&nbsp;&nbsp;1/";
    String pageEnd = "页</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    return res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));

  }

  /**
   * 获取页码总数--公益岗位
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param dwbh      单位编号
   * @param dwmc      单位名称
   * @return 页码总数
   */
  public static String getDataTotal(CloseableHttpClient client, String tableMark, String dwbh, String dwmc) throws Exception {
    String path = "/lemis3/lemis3MeritStation.do";
    String method = "queryMeritStaResettlement";
    String _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" sfyba=\"\" dwbh=\"" + dwbh + "\" dwmc=\"" + dwmc + "\" gwmc=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" sfbl=\"\" tcqsrq=\"\" tczzrq=\"\" spzt=\"\" /></p>";

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", tableMark)
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    System.out.println(res);
    String pageStart = "记录&nbsp;&nbsp;&nbsp;&nbsp;1/";
    String pageEnd = "页</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    return res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));

  }

  /**
   * 获取页码总数--公益岗位
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param dw        单位
   * @return 页码总数
   */
  public static String getDataTotal(CloseableHttpClient client, String tableMark, DW dw) throws Exception {
    String path = "/lemis3/lemis3MeritStation.do";
    String method = "queryMeritStaResettlement";
    String _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" sfyba=\"\" dwbh=\"" + dw.getDwbh() + "\" dwmc=\"" + dw.getDwmc() + "\" gwmc=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" sfbl=\"\" tcqsrq=\"\" tczzrq=\"\" spzt=\"\" /></p>";

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", tableMark)
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String pageStart = "记录&nbsp;&nbsp;&nbsp;&nbsp;1/";
    String pageEnd = "页</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    return res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));

  }

  /**
   * 获取页码总数
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param type      3灵活就业
   * @param jbjgbh    经办机构编号
   * @param jgbh      机构编号
   * @param jgmc      机构名称
   * @return 页码总数
   */
  public static String getDataTotal(CloseableHttpClient client, String tableMark, int type, String jbjgbh, String jgbh, String jgmc) throws Exception {
    String path, method, _xmlString;
    switch (type) {
      case 3:  // 灵活就业
        path = "/lemis3/lemis3SuccorFlexibleEmp.do";
        method = "queryFlexEmp";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"\" grxm=\"\" grbh=\"\" spzt=\"\" qsrq=\"\" zzrq=\"\" sftc=\"0\" jbjgbh=\"" + jbjgbh + "\" jgbh=\"" + jgbh + "\" jgmc=\"" + jgmc + "\" sfbl=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" sfyba=\"\" ></s></p>";
        break;
      default:
        return "";
    }
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", tableMark)
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    String pageStart = "记录&nbsp;&nbsp;&nbsp;&nbsp;1/";
    String pageEnd = "页</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    System.out.println(res);
    return res.substring(res.indexOf(pageStart) + 28, res.indexOf(pageEnd));

  }

  /**
   * 根据页码获取数据
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param page      页码
   * @return 所查数据的JSON字符串
   */
  public static String getDataByPage(CloseableHttpClient client, String tableMark, String page, String total) throws Exception {
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
   * 获取全部数据
   *
   * @param client    登陆后的client
   * @param tableMark 当前窗体的tableMark值
   * @param total     页码总数
   * @return 全部数据的JSON字符串
   */
  public static String getDataAll(CloseableHttpClient client, String tableMark, Integer total) throws Exception {
    String str = "";
    for (int i = 1; i < total + 1; i++) {
      str = str + getDataByPage(client, tableMark, i + "", total.toString()) + ",";
    }
    return "[" + str.substring(0, str.length() - 1) + "]";
  }

  /**
   * 获取剩余月数
   *
   * @param client 登陆后的client
   * @param gmsfhm 公民身份号码
   * @param grbh   个人编号
   * @param djlsh  登记流水号
   * @return 剩余月数。无法获取则返回空
   */
  public static String getSyys(CloseableHttpClient client, Integer type, String gmsfhm, String grbh, String djlsh) throws Exception {
    String grxm = "", btrylb;
    switch (type) {
      case 1:  // 企业吸纳
        btrylb = "01";
        break;
      case 2:  // 公益岗位
        btrylb = "02";
        break;
      case 3:  // 灵活就业
        btrylb = "03";
        break;
      default:
        return "[]";
    }
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "enterBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s djlsh=\"" + djlsh + "\"/><s grbh=\"" + grbh + "\"/><s gmsfhm=\"" + gmsfhm + "\"/><s grxm=\"" + grxm + "\"/><s btrylb=\"" + btrylb + "\"/></p>")
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
   *
   * @param client 登陆后的client
   * @param type   补贴人员类别 01企业吸纳02公益性03灵活就业
   * @param gmsfhm 公民身份号码
   * @param grbh   个人编号
   * @param djlsh  登记流水号
   * @param qsny   起始年月
   * @param zzny   终止年月
   * @param syys   剩余月份
   * @return 生成结果的JSON字符串
   */
  public static String creatSubsidy(CloseableHttpClient client, Integer type, String gmsfhm, String grbh, String djlsh, String qsny, String zzny, String syys) throws Exception {
    String grxm = "", btrylb;
    switch (type) {
      case 1:  // 企业吸纳
        btrylb = "01";
        break;
      case 2:  // 公益岗位
        btrylb = "02";
        break;
      case 3:  // 灵活就业
        btrylb = "03";
        break;
      default:
        return "[]";
    }
    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3MeritStation.do")
            .setParameter("method", "createBonusAddForSinglePer")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s grbh=\"" + grbh + "\" msg=\"\" grxm=\"" + grxm + "\" gmsfhm=\"" + gmsfhm + "\" djlsh=\"" + djlsh + "\" btrylb=\"" + btrylb + "\" syys=\"" + syys + "\" qsny=\"" + qsny + "\" zzny=\"" + zzny + "\" /><d k=\"dw_ylbt\"></d></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(u);
    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");
    if (res.indexOf("月的补贴已录入") > 0) {
      return "指定月份补贴已录入！";
    } else if (res.indexOf("补贴享受起始年月早于审批日期，请检查！") > 0) {
      return "补贴享受起始年月早于审批日期，请检查！";
    } else if (res.indexOf("人员【" + qsny + "】到【" + zzny + "】的补贴预算，请检查！") > 0) {
      return "补贴预算异常，请检查！";
    } else if (res.indexOf("的资金报表已经上报，不允许维护补贴，请检查！") > 0) {
      return "资金报表已经上报，不允许维护补贴，请检查！";
    } else if (res.indexOf("剩余月数不足，当前剩余") > 0) {
      return "剩余月数不足，请检查！";
    } else if (res.indexOf("的低保待遇的记录") > 0) {
      return "缺少低保待遇的记录，请检查！";
    } else if (res.indexOf("不存在社保缴费信息") > 0) {
      return "不存在社保缴费信息，请检查！";
    } else if (res.indexOf("该人员的家庭成员") > 0) {
      return "该人员的家庭成员情况异常，请检查！";
    } else if (res.indexOf("存在失业") > 0) {
      return "该人员存在失业等保险情况，请检查！";
    } else if (res.indexOf("调用接口出错") > 0) {
      return "调用接口出错，请尝试手动录入！";
    } else {
      String start = "init('true','true','[";
      String end = "]');</script>";
      return res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1);
    }
  }

  /**
   * 获取数据信息--->点击搜索后弹出的那个对话框信息
   *
   * @param client    登陆后的client
   * @param paraValue 搜索参数
   * @param type      1企业吸纳2公益岗位3灵活就业4困难人员
   * @return 第一个人员的json字符串 企业吸纳和灵活就业的个人编号grbh似乎不一样，需要进一步确认
   */
  public static JSONObject getDataInfo(CloseableHttpClient client, Integer type, String paraValue) throws Exception {
    String method, containerName, _xmlString;
    JSONObject dataInfo = null;
    switch (type) {
      case 1:  // 企业吸纳
        method = "queryBonusPersonInfo";
        containerName = "formAdmitPerQuery";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s paraValue=\"" + paraValue + "\"/><s btrylb=\"01\"/></p>";
        break;
      case 2:  // 公益岗位
        method = "queryPerMeritStationInfo";
        containerName = "queryMeritStaInfo";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s para=\"" + paraValue + "\"/></p>";
        break;
      case 3:  // 灵活就业
        method = "queryBonusPersonInfo";
        containerName = "formPerInfoQuery";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s paraValue=\"" + paraValue + "\"/><s btrylb=\"03\"/></p>";
        break;
      case 4:  // 困难人员
        method = "queryDiffPersonInfo";
        containerName = "queryDiffPerInfo";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s paraValue=\"" + paraValue + "\"/></p>";
        break;
      default:
        return dataInfo;
    }

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3Lov.do")
            .setParameter("method", method)
            .setParameter("containerName", containerName)
            .setParameter("_xmlString", _xmlString)
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
      if (jsStrs.size() > 0) {
        dataInfo = jsStrs.getJSONObject(0);
      }
    }
    return dataInfo;
  }

  /**
   * 获取数据详情--->完成搜索后在原窗体出现的信息
   *
   * @param client     登陆后的client
   * @param type       1企业吸纳2公益岗位3灵活就业
   * @param gmsfhm     公民身份号码
   * @param grbh       个人编号
   * @param datawindow 窗体的tableMark值
   * @return 第一个人员的json字符串
   */
  public static JSONObject getDataDetail(CloseableHttpClient client, Integer type, String gmsfhm, String grbh, String datawindow) throws Exception {
    String grxm = "";
    JSONObject dataDetail = null;
    String path, method, _xmlString;
    switch (type) {
      case 1:  // 企业吸纳
        path = "/lemis3/lemis3SuccorOrgAdmit.do";
        method = "queryAdmitPer";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s dwbh=\"\" dwmc=\"\" sftc=\"\" gmsfhm=\"" + gmsfhm + "\" grxm=\"" + grxm + "\" grbh=\"" + grbh + "\" sfbl=\"\" sfyba=\"\" /></p>";
        break;
      case 2:  // 公益岗位
        path = "/lemis3/lemis3MeritStation.do";
        method = "queryMeritStaResettlement";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + gmsfhm + "\" grxm=\"\" grbh=\"\" sfyba=\"\" dwbh=\"\" dwmc=\"\" gwmc=\"\" qsrq=\"\" zzrq=\"\" sftc=\"\" sfbl=\"\" tcqsrq=\"\" tczzrq=\"\" spzt=\"\" /></p>";
        break;
      case 3:  // 灵活就业
        path = "/lemis3/lemis3SuccorFlexibleEmp.do";
        method = "queryFlexEmp";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + gmsfhm + "\" grxm=\"" + grxm + "\" grbh=\"" + grbh + "\" spzt=\"\" qsrq=\"\" zzrq=\"\" sftc=\"\" jbjgbh=\"\" jgbh=\"\" jgmc=\"\" sfbl=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" sfyba=\"\" /></p>";
        break;
      default:
        return dataDetail;
    }

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", datawindow)
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
    if (res.contains(start) && res.contains(end)) {
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1));
      if (jsStrs.size() > 0) {
        dataDetail = jsStrs.getJSONObject(0);
      }
    }
    return dataDetail;
  }

  /**
   * 获取数据详情--->直接在原窗体出现的信息
   *
   * @param client     登陆后的client
   * @param type       1企业吸纳2公益岗位3灵活就业
   * @param gmsfhm     公民身份号码
   * @param datawindow 窗体的tableMark值
   * @return 第一个人员的json字符串
   */
  public static JSONObject getDataDetail(CloseableHttpClient client, Integer type, String gmsfhm, String datawindow) throws Exception {
    String grxm = "";
    JSONObject dataDetail = null;
    String path, method, _xmlString;
    switch (type) {
      case 1:  // 企业吸纳
        path = "/lemis3/lemis3SuccorOrgAdmit.do";
        method = "queryAdmitPer";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s dwbh=\"\" dwmc=\"\" sftc=\"\" gmsfhm=\"" + gmsfhm + "\" grxm=\"" + grxm + "\" grbh=\"\" sfbl=\"\" sfyba=\"\" /></p>";
        break;
      case 2:  // 公益岗位
        path = "/lemis3/lemis3MeritStation.do";
        method = "queryMeritStaResettlement";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + gmsfhm + "\" grxm=\"\" grbh=\"\" sfyba=\"\" dwbh=\"\" dwmc=\"\" gwmc=\"\" qsrq=\"\" zzrq=\"\" sftc=\"\" sfbl=\"\" tcqsrq=\"\" tczzrq=\"\" spzt=\"\" /></p>";
        break;
      case 3:  // 灵活就业
        path = "/lemis3/lemis3SuccorFlexibleEmp.do";
        method = "queryFlexEmp";
        _xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + gmsfhm + "\" grxm=\"" + grxm + "\" grbh=\"\" spzt=\"\" qsrq=\"\" zzrq=\"\" sftc=\"\" jbjgbh=\"\" jgbh=\"\" jgmc=\"\" sfbl=\"\" jsid=\"\" jsbh=\"\" jsmc=\"\" sfyba=\"\" /></p>";
        break;
      default:
        return dataDetail;
    }

    URI u = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath(path)
            .setParameter("method", method)
            .setParameter("_xmlString", _xmlString)
            .setParameter("tableMark", datawindow)
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
    if (res.contains(start) && res.contains(end)) {
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.indexOf(start) + 20, res.indexOf(end) + 1));
      if (jsStrs.size() > 0) {
        dataDetail = jsStrs.getJSONObject(0);
      }
    }
    return dataDetail;
  }

}
