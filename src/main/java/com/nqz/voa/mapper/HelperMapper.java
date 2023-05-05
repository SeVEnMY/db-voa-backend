package com.nqz.voa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HelperMapper {
  @Select("SELECT LAST_INSERT_ID()")
  int getLastInsertedId();
}
