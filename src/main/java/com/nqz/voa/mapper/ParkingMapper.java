package com.nqz.voa.mapper;

import com.nqz.voa.entry.ParkingEntry;
import com.nqz.voa.entry.ParkingLotEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParkingMapper {

  @Select("SELECT * FROM nqz_parking")
  List<ParkingEntry> findAllParkings();

  @Select("SELECT * FROM nqz_parking WHERE park_id = #{parkId}")
  ParkingEntry findParkById(int parkId);

  @Insert("INSERT INTO nqz_parking (park_time_in, park_time_out, park_fee, park_spotno, pl_id)\n" +
          "VALUES (str_to_date( '#{parkTimeIn} , '%Y-%m-%d %H:%i:%s' ), str_to_date( #{parkTimeOut} , '%Y-%m-%d %H:%i:%s' ), #{parkFee}, #{parkSoptNo}, #{plId})")
  int addParking(String parkTimeIn, String parkTimeOut, int parkFee, int parkSoptNo, int plId);

  @Select("SELECT * FROM nqz_parking_lot")
  List<ParkingLotEntry> findAllParkingLots();

  @Insert("INSERT INTO nqz_parking_lot (pl_name) VALUES ('Parking Lot A')")
  int addParkingLot(String plName);

}
