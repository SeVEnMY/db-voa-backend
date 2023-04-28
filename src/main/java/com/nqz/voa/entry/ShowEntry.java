package com.nqz.voa.entry;

import lombok.Data;

@Data
public class ShowEntry {
  private int sh_id;
  private String sh_name;
  private String sh_description;
  private String sh_start_time;
  private String sh_end_time;
  private String sh_wheelchair_acc;
  private int sh_price;
  private int shtype_id;

}
