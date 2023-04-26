package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class ShowEntry {
  private int shId;
  private String shName;
  private String shDescription;
  private String shStartTime;
  private String shEndTime;
  private String shWheelchairAcc;
  private int shPrice;
  private int shTypeId;

}
