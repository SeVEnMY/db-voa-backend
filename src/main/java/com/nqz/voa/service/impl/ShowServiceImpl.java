package com.nqz.voa.service.impl;

import com.nqz.voa.entry.ShowEntry;
import com.nqz.voa.entry.ShowTypeEntry;
import com.nqz.voa.mapper.ShowMapper;
import com.nqz.voa.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

  @Autowired
  ShowMapper showMapper;

  @Override
  public List<ShowEntry> findAllShows() {
    return showMapper.findAllShows();
  }

  @Override
  public ShowEntry findShowById(int shId) {
    return showMapper.findShowById(shId);
  }

  @Override
  public int addShow(String shName, String shDescription, String shStartTime, String shEndTime, String shWheelchairAcc, int shPrice, int shTypeId) {
    return showMapper.addShow(shName, shDescription, shStartTime, shEndTime, shWheelchairAcc, shPrice, shTypeId);
  }

  @Override
  public List<ShowTypeEntry> findAllShowTypes() {
    return showMapper.findAllShowTypes();
  }

  @Override
  public int addShowType(String shTypeName) {
    return showMapper.addShowType(shTypeName);
  }

  @Override
  public int getDramaCount() {
    return showMapper.getDramaCount();
  }

  @Override
  public int getMusicalCount() {
    return showMapper.getMusicalCount();
  }

  @Override
  public int getComedyCount() {
    return showMapper.getComedyCount();
  }

  @Override
  public int getHorrorCount() {
    return showMapper.getHorrorCount();
  }

  @Override
  public int getAdventureCount() {
    return showMapper.getAdventureCount();
  }

  @Override
  public int deleteShowById(int shId) {
    return showMapper.deleteShowById(shId);
  }
}
