package com.wts;

import com.wts.service.Common;
import net.sf.json.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import static com.wts.service.Common.*;
import static com.wts.service.Common.getSyys;
import static com.wts.service.GongGang.saveSubsidy;

public class hack {
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

    String datawindow = getTableMark(client, 2);
    if (datawindow.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法打开窗口！");
      return "无法打开窗口！";
    }
    System.out.println("窗口编号为："+datawindow);
    //System.out.println(datawindow);
    String grbh = getDataInfo(client, 2, gmsfhm).getString("grbh");
    if (grbh.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法获取个人编号！");
      return "无法获取个人编号！";
    }
    System.out.println("个人编号为："+grbh);
    //System.out.println(grbh);
    JSONObject jsonObject = getDataDetail(client, 2, gmsfhm, grbh, datawindow);
    String djlsh = jsonObject.getString("djlsh");
    if (djlsh.equals("")) {
      System.out.println(gmsfhm + grxm + "--无法获取登记流水号！");
      return "无法获取登记流水号！";
    }
    System.out.println("登记流水号为："+djlsh);

    String syys = getSyys(client, 2, gmsfhm, grbh, djlsh);
    if (syys.equals("0")) {
      System.out.println(gmsfhm + grxm + "--剩余补贴月数为零！");
      return "剩余补贴月数为零！";
    }
    System.out.println("剩余补贴月数为："+syys);

    String save = saveSubsidy(client, gmsfhm, grbh, djlsh, qsny, zzny, syys, "576", "288", "22.4", "1810", "", "", "");
    if (!save.equals("保存成功！")) {
      System.out.println(gmsfhm + grxm + "--" + qsny + "-" + zzny + save);
      return save;
    }
    System.out.println(gmsfhm + grxm + "--" + qsny + "-" + zzny + "补贴录入成功！");
    return qsny + "-" + zzny + "补贴录入成功！";
  }

  public static void main(String[] args) throws Exception{
    CloseableHttpClient client = login("hywlg", "1360");
    save(client,"孙立生","370104195912290010","201709","201709");
  }
}
