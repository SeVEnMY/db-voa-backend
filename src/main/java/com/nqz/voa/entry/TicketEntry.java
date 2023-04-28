package com.nqz.voa.entry;

import lombok.Data;

@Data
public class TicketEntry {
  private int tkt_id;
  private String tkt_online;
  private String tkt_visit_date;
  private int tkt_price;
  private int tkt_discount;
  private String tkt_ispaid;
  private int tkttype_id;
}
