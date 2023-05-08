package com.nqz.voa.service.impl;

import com.nqz.voa.entry.AttractionEntry;
import com.nqz.voa.entry.AttractionTypeEntry;
import com.nqz.voa.entry.LocationSectionEntry;
import com.nqz.voa.mapper.AttractionMapper;
import com.nqz.voa.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

  @Autowired
  protected AttractionMapper attractionMapper;

  @Override
  public List<AttractionEntry> findAllAttractions() {
    return attractionMapper.findAllAttractions();
  }

  @Override
  public AttractionEntry findAttractionById(int attId) {
    return attractionMapper.findAttractionById(attId);
  }

  @Override
  public int addAttraction(String attName, String attDescription, String attStatus, int attCapacity, Integer attMinimumHeight, int attDurationTime, int lsId, int attTypeId) {
    return attractionMapper.addAttraction(attName, attDescription, attStatus, attCapacity, attMinimumHeight, attDurationTime, lsId, attTypeId);
  }

  @Override
  public List<AttractionTypeEntry> findAllAttractionTypes() {
    return attractionMapper.findAllAttractionTypes();
  }

  @Override
  public int addAttractionType(String attTypeName) {
    return attractionMapper.addAttractionType(attTypeName);
  }

  @Override
  public List<LocationSectionEntry> findAllLocationSections() {
    return attractionMapper.findAllLocationSections();
  }

  @Override
  public int addLocationSection(String lsName) {
    return attractionMapper.addLocationSection(lsName);
  }

  @Override
  public List<AttractionEntry> findAttractionByTicketId(int tktId) {
    return attractionMapper.findAttractionByTicketId(tktId);
  }

  @Override
  public int getRollerCoasterCount() {
    return attractionMapper.getRollerCoasterCount();
  }

  @Override
  public int getWaterRideCount() {
    return attractionMapper.getWaterRideCount();
  }

  @Override
  public int getDarkRideCount() {
    return attractionMapper.getDarkRideCount();
  }

  @Override
  public int deleteTktAttByAttId(int attId) {
    return attractionMapper.deleteTktAttByAttId(attId);
  }

  @Override
  public int getKidRideCount() {
    return attractionMapper.getKidRideCount();
  }

  @Override
  public int getVrRideCount() {
    return attractionMapper.getVrRideCount();
  }

  @Override
  public int deleteAttractionById(int attId) {
    return attractionMapper.deleteAttractionById(attId);
  }

}
