package com.wts;

import com.wts.entity.PersonGG;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.List;

import static com.wts.service.Common.*;
import static com.wts.service.GongGang.goPersonGGs;
import static com.wts.util.Export.ExportGGResult;
import static com.wts.util.Import.ImportGG;
// 370104197409214124
public class run {
  public static void main(String[] args) throws Exception{
    CloseableHttpClient client = login("hymlh", "888888");
    List<PersonGG> persons = goPersonGGs(client);
    for (PersonGG p :persons){
      System.out.println(p.getGrxm());
    }



//    ExportGGResult(client, persons, "201709", "201709");
    //getDataInfo(client,2,"370111196810154868");
    //getDataDetail(client,3,"370104197409214124","",getTableMark(client,3));
  }
}
