package com.nqz.voa.entry;

import lombok.Data;

@Data
public class ParkingEntry {
  private int park_id;
  private String park_time_in;
  private String park_time_out;
  private int park_fee;
  private int park_spotno;
  private int pl_id;
}
