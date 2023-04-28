package com.nqz.voa.entry;

import lombok.Data;

@Data
public class CreditDebitPayEntry {
  private int pay_id;
  private String cd_name;
  private String cd_num;
  private String cd_exdate;
  private String cd_cvv;
  private String cd_credit;
}
