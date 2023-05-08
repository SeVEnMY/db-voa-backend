package com.nqz.voa.service;

import com.nqz.voa.entry.AttractionEntry;
import com.nqz.voa.entry.AttractionTypeEntry;
import com.nqz.voa.entry.LocationSectionEntry;

import java.util.List;

public interface AttractionService {
  List<AttractionEntry> findAllAttractions();

  AttractionEntry findAttractionById(int attId);

  int addAttraction(String attName, String attDescription, String attStatus, int attCapacity, Integer attMinimumHeight, int attDurationTime, int lsId, int attTypeId);

  List<AttractionTypeEntry> findAllAttractionTypes();

  int addAttractionType(String attTypeName);

  List<LocationSectionEntry> findAllLocationSections();

  int addLocationSection(String lsName);

  List<AttractionEntry> findAttractionByTicketId(int tktId);

  int getRollerCoasterCount();

  int getWaterRideCount();

  int getDarkRideCount();

  int getKidRideCount();

  int getVrRideCount();
}
