package com.nqz.voa.enrty;

import lombok.Data;

@Data
public class MemberVisitEntry {
  private int vId;
  private String mStartDate;
  private String mEndDate;
  private int mNumPurchased;
}
