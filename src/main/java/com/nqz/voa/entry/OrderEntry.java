package com.nqz.voa.entry;

import lombok.Data;

@Data
public class OrderEntry {
  private int o_id;
  private String o_date;
  private int o_quantity;
  private long o_amount;
  private int sh_id;
  private int v_id;
  private int pay_id;
  private int st_id;
  private int mi_id;
  private int tkt_id;
  private int park_id;
}
