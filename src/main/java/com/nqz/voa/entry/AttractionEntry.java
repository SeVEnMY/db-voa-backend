package com.nqz.voa.entry;

import lombok.Data;

@Data
public class AttractionEntry {
  private int att_id;
  private String att_name;
  private String att_description;
  private String att_status;
  private int att_capacity;
  private int att_minimum_height;
  private int att_duration_time;
  private int ls_id;
  private int atttype_id;
}
