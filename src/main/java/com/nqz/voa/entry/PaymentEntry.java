package com.nqz.voa.entry;

import lombok.Data;

@Data
public class PaymentEntry {
  private int pay_id;
  private String pay_time;
  private long pay_amount;
  private String pay_method;
}
