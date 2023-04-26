package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class OrderEntry {
  private int oId;
  private String oDate;
  private int oQuantity;
  private long oAmount;
  private int shId;
  private int vId;
  private int payId;
  private int stId;
  private int miId;
  private int tktId;
  private int parkId;
}
