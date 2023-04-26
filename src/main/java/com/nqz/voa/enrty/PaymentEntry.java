package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class PaymentEntry {
  private int patId;
  private String payTime;
  private long payAmount;
  private String payMethod;
}
