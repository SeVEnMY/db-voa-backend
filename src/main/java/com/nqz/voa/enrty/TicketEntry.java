package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class TicketEntry {
  private int tktId;
  private String tktOnline;
  private String tktVisitDate;
  private int tktPrice;
  private int tktDiscount;
  private String tktIsPaid;
  private int tktTypeId;
}
