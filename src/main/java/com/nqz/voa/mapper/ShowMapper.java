package com.nqz.voa.mapper;

import com.nqz.voa.entry.ShowEntry;
import com.nqz.voa.entry.ShowTypeEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShowMapper {

  @Select("SELECT * FROM nqz_show")
  List<ShowEntry> findAllShows();

  @Select("SELECT * FROM nqz_show WHERE sh_id = #{shId}")
  ShowEntry findShowById(int shId);

  @Insert("INSERT INTO nqz_show (sh_name, sh_description, sh_start_time, sh_end_time, sh_wheelchair_acc, sh_price, shtype_id)" +
          "VALUES (#{shName}, #{shDescription}, str_to_date( #{shStartTime} , '%Y-%m-%d %H:%i:%s' ), str_to_date( #{shEndTime} , '%Y-%m-%d %H:%i:%s' ), #{shWheelchairAcc}, #{shPrice}, #{shTypeId})")
  int addShow(String shName, String shDescription, String shStartTime, String shEndTime, String shWheelchairAcc, int shPrice, int shTypeId);

  @Select("SELECT * FROM nqz_sh_type")
  List<ShowTypeEntry> findAllShowTypes();

  @Insert("INSERT INTO nqz_sh_type (shtype_name) VALUES (#{shTypeName})")
  int addShowType(String shTypeName);

  @Select("SELECT COUNT(*) FROM nqz_show WHERE shtype_id = 1")
  int getDramaCount();

  @Select("SELECT COUNT(*) FROM nqz_show WHERE shtype_id = 2")
  int getMusicalCount();

  @Select("SELECT COUNT(*) FROM nqz_show WHERE shtype_id = 3")
  int getComedyCount();

  @Select("SELECT COUNT(*) FROM nqz_show WHERE shtype_id = 4")
  int getHorrorCount();

  @Select("SELECT COUNT(*) FROM nqz_show WHERE shtype_id = 5")
  int getAdventureCount();

}
