package com.nqz.voa.mapper;

import com.nqz.voa.entry.AttractionEntry;
import com.nqz.voa.entry.AttractionTypeEntry;
import com.nqz.voa.entry.LocationSectionEntry;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttractionMapper {
  @Select("SELECT * FROM nqz_attraction")
  List<AttractionEntry> findAllAttractions();

  @Select("SELECT * FROM nqz_attraction WHERE att_id = #{attId}")
  AttractionEntry findAttractionById(int attId);

  @Insert("INSERT INTO nqz_attraction (att_name, att_description, att_status, att_capacity, att_minimum_height, att_duration_time, atttype_id, ls_id) " +
          "VALUES (#{attName}, #{attDescription}, #{attStatus}, #{attCapacity}, #{attMinimumHeight}, #{attDurationTime}, #{lsId}, #{attTypeId});  ")
  int addAttraction(String attName, String attDescription, String attStatus, int attCapacity, Integer attMinimumHeight, int attDurationTime, int lsId, int attTypeId);

  @Select("SELECT * FROM nqz_att_type")
  List<AttractionTypeEntry> findAllAttractionTypes();

  @Insert("INSERT INTO nqz_att_type (atttype_name) " +
          "VALUES (#{attTypeName})")
  int addAttractionType(String attTypeName);

  @Select("SELECT * FROM nqz_location_section")
  List<LocationSectionEntry> findAllLocationSections();

  @Insert("INSERT INTO nqz_location_section (ls_name) " +
          "VALUES ('Lot A')")
  int addLocationSection(String lsName);

  @Select("SELECT * FROM nqz_attraction NATURAL JOIN nqz_tkt_att WHERE tkt_id = #{tktId}")
  List<AttractionEntry> findAttractionByTicketId(int tktId);


  @Select("SELECT COUNT(*) FROM nqz_tkt_att NATURAL JOIN nqz_attraction WHERE atttype_id = 1")
  int getRollerCoasterCount();

  @Select("SELECT COUNT(*) FROM nqz_tkt_att NATURAL JOIN nqz_attraction WHERE atttype_id = 2")
  int getWaterRideCount();

  @Select("SELECT COUNT(*) FROM nqz_tkt_att NATURAL JOIN nqz_attraction WHERE atttype_id = 3")
  int getDarkRideCount();

  @Select("SELECT COUNT(*) FROM nqz_tkt_att NATURAL JOIN nqz_attraction WHERE atttype_id = 4")
  int getKidRideCount();

  @Select("SELECT COUNT(*) FROM nqz_tkt_att NATURAL JOIN nqz_attraction WHERE atttype_id = 5")
  int getVrRideCount();

  @Delete("DELETE FROM nqz_tkt_att WHERE att_id = #{attId};")
  int deleteTktAttByAttId(int attId);

  @Delete("DELETE FROM nqz_attraction WHERE att_id = #{attId};")
  int deleteAttractionById(int attId);

}
