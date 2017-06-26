package com.wts.check;

import com.wts.util.util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import static com.wts.service.common.login;

public class commerce {
  /**
   * 根据是否存在注销日期或吊销日期判断是否有工商信息
   *
   * @param jsStrs Json字符串
   * @return  存在返回true
   */
  public static Boolean checkJson(JSONArray jsStrs) {
    Boolean result = false;
    if (jsStrs.size() > 0) {
      for (int k = 0; k < jsStrs.size(); k++) {
        JSONObject jsStr = jsStrs.getJSONObject(k);
        if (jsStr.getString("zxrq").equals("") || jsStr.getString("dxrq").equals("")) {
          result = true;
          break;
        }
      }
    }
    return result;
  }


  /**
   * @param client   登陆后的client
   * @param id 身份证号码 String personNumber15 =personNumber.substring(0,6)+personNumber.substring(8,17);
   * @return
   */
  public static Boolean getCommerce(CloseableHttpClient client,String id) throws Exception {
    URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("10.153.50.108:7001")
            .setPath("/lemis3/lemis3Person.do")
            .setParameter("method", "queryGsjPersonInfo")
            .setParameter("_xmlString", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p><s gmsfhm=\"" + id + "\" zch=\"\" xshs=\"200\" ESTDATE=\"\" clzzrq=\"\" REVDATE=\"\" CANDATE=\"\" zxzzrq=\"\" dxzzrq=\"\" REGORG=\"\" ENTCAT=\"\" /></p>")
            .setParameter("_jbjgqxfw", "undefined")
            .setParameter("_sbjbjg", "undefined")
            .setParameter("_dwqxfw", "undefined")
            .build();
    HttpPost post = new HttpPost(uri);

    CloseableHttpResponse response = client.execute(post);
    HttpEntity entity = response.getEntity();
    String res = EntityUtils.toString(entity, "UTF-8");

    if (util.getCount(res, "init('true','true','[") == 1) {
      JSONArray jsStrs = JSONArray.fromObject(res.substring(res.lastIndexOf("init('true','true','[") + 20, res.lastIndexOf("]');</script>") + 1));
      return checkJson(jsStrs);
    } else if (util.getCount(res, "init('true','true','[") == 1) {
      JSONArray jsStrs1 = JSONArray.fromObject(res.substring(res.lastIndexOf("init('true','true','[") + 20, res.lastIndexOf("]');</script>") + 1));
      JSONArray jsStrs2 = JSONArray.fromObject(res.substring(res.indexOf("init('true','true','[") + 20, res.indexOf("]');</script>") + 1));
      return checkJson(jsStrs1) || checkJson(jsStrs2);
    }else{
      return false;
    }
  }
}
