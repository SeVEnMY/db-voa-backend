package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class CreditDebitPayEntry {
  private int payId;
  private String cdName;
  private String cdNum;
  private String cdExDate;
  private String cdCVV;
  private String cdCredit;
}
