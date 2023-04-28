package com.nqz.voa.entry;

import lombok.Data;

@Data
public class MemberVisitEntry {
  private int v_id;
  private String m_startdate;
  private String m_enddate;
  private int m_num_purchased;
}
