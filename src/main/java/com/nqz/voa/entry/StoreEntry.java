package com.nqz.voa.entry;

import lombok.Data;

@Data
public class StoreEntry {
  private int st_id;
  private String st_name;
  private String st_description;
  private int ctg_id;
}
