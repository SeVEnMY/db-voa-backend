package com.nqz.voa.service.impl;

import com.nqz.voa.mapper.HelperMapper;
import com.nqz.voa.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelperServiceImpl implements HelperService {
  @Autowired
  HelperMapper helperMapper;

  @Override
  public int getLastInsertedId() {
    return helperMapper.getLastInsertedId();
  }
}
