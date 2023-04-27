package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class IndiRegisterRequestEntry {
  private String accEmail;
  private String accPwd;

  private String vFName;
  private String vMName;
  private String vLName;
  private String vStAdd;
  private String vCity;
  private String vState;
  private String vCountry;
  private String vEmail;
  private String vTelNum;
  private String vType;
  private String vBDate;
  private int iTimesVisit;
}
