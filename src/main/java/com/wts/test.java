package com.wts;


import com.wts.entity.PersonQY;
import com.wts.util.PropKit;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.List;
import java.util.Properties;

import static com.wts.service.common.login;
import static com.wts.util.Export.ExportQYResult;
import static com.wts.util.Import.ImportQY;
import static com.wts.util.PropKit.getString;
import static com.wts.util.PropKit.loadProps;

public class test {
  public static void main(String[] args) throws Exception{
    Properties properties=loadProps(PropKit.class.getClassLoader().getResource("info.properties").getPath());
    CloseableHttpClient client= login(getString(properties,"userid"),getString(properties,"passwd"));
    List<PersonQY> persons =ImportQY(getString(properties,"result"));
    ExportQYResult(client,persons,"201706");
  }
}
