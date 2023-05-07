package com.nqz.voa.service;

import com.nqz.voa.entry.ParkingEntry;
import com.nqz.voa.entry.ParkingLotEntry;

import java.util.List;

public interface ParkingService {

  List<ParkingEntry> findAllParkings();

  ParkingEntry findParkingById(int parkId);

  int addParking(String parkTimeIn, String parkTimeOut, int parkFee, int parkSoptNo, int plId);

  List<ParkingLotEntry> findAllParkingLots();

  int addParkingLot(String plName);

  int getParkingLotACount();

  int getParkingLotBCount();

  int getParkingLotCCount();

  int getPremiumLotCount();
}
