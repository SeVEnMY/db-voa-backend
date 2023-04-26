package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class ParkingEntry {
  private int parkId;
  private String parkTimeIn;
  private String parkTimeOut;
  private int parkFee;
  private int parkSpotNo;
  private int plId;
}
