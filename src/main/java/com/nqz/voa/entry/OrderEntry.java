package com.nqz.voa.entry;

import lombok.Data;

@Data
public class OrderEntry {
  private int o_id;
  private String o_date;
  private int o_quantity;
  private long o_amount;
  private Integer sh_id;
  private int v_id;
  private Integer pay_id;
  private Integer st_id;
  private Integer mi_id;
  private Integer tkt_id;
  private Integer park_id;
}
