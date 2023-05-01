package com.nqz.voa.service;

import com.nqz.voa.entry.ShowEntry;
import com.nqz.voa.entry.ShowTypeEntry;

import java.util.List;

public interface ShowService {
  List<ShowEntry> findAllShows();

  ShowEntry findShowById(int shId);

  int addShow(String shName, String shDescription, String shStartTime, String shEndTime, String shWheelchairAcc, int shPrice, int shTypeId);

  List<ShowTypeEntry> findAllShowTypes();

  int addShowType(String shTypeName);
}
