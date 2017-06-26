package com.wts;


import com.wts.entity.PersonLH;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.List;

import static com.wts.service.common.login;
import static com.wts.util.Export.ExportLHResult;
import static com.wts.util.Import.ImportLH;

public class test {
  public static void main(String[] args) throws Exception{
    CloseableHttpClient client= login("hyddj","888888");
    List<PersonLH> persons =ImportLH("ddj");
    ExportLHResult(client,persons,"201706");
  }
}
