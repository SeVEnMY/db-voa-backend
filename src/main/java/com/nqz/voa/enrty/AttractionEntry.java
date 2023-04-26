package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class AttractionEntry {
  private int attId;
  private String attName;
  private String attDescription;
  private String attStatus;
  private int attCapacity;
  private int attMinimumHeight;
  private int attDurationTime;
  private int lsId;
  private int attTypeId;
}
