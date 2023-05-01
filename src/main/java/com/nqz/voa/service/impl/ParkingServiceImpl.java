package com.nqz.voa.service.impl;

import com.nqz.voa.entry.ParkingEntry;
import com.nqz.voa.entry.ParkingLotEntry;
import com.nqz.voa.mapper.ParkingMapper;
import com.nqz.voa.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
  @Autowired
  ParkingMapper parkingMapper;

  @Override
  public List<ParkingEntry> findAllParkings() {
    return parkingMapper.findAllParkings();
  }

  @Override
  public ParkingEntry findParkingById(int parkId) {
    return parkingMapper.findParkById(parkId);
  }

  @Override
  public int addParking(String parkTimeIn, String parkTimeOut, int parkFee, int parkSoptNo, int plId) {
    return parkingMapper.addParking(parkTimeIn, parkTimeOut, parkFee, parkSoptNo, plId);
  }

  @Override
  public List<ParkingLotEntry> findAllParkingLots() {
    return parkingMapper.findAllParkingLots();
  }

  @Override
  public int addParkingLot(String plName) {
    return parkingMapper.addParkingLot(plName);
  }

}
