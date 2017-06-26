package com.wts.entity;

/**
 * 单位
 */
public class DW {
  private String xzqhmc;//行政区划名称
  private String dwmc;//单位名称
  private String dwbh;//单位编号
  private String dwzt;//单位状态 10登记在册
  private String jbjgmc;//经办机构名称

  public String getDwbh() {
    return dwbh;
  }

  public void setDwbh(String dwbh) {
    this.dwbh = dwbh;
  }

  public String getXzqhmc() {
    return xzqhmc;
  }

  public void setXzqhmc(String xzqhmc) {
    this.xzqhmc = xzqhmc;
  }

  public String getDwmc() {
    return dwmc;
  }

  public void setDwmc(String dwmc) {
    this.dwmc = dwmc;
  }

  public String getDwzt() {
    return dwzt;
  }

  public void setDwzt(String dwzt) {
    this.dwzt = dwzt;
  }

  public String getJbjgmc() {
    return jbjgmc;
  }

  public void setJbjgmc(String jbjgmc) {
    this.jbjgmc = jbjgmc;
  }
}
