package com.nqz.voa.mapper;

import com.nqz.voa.entry.TicketEntry;
import com.nqz.voa.entry.TicketTypeEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TicketMapper {
  @Select("SELECT * FROM nqz_ticket")
  List<TicketEntry> findAllTickets();

  @Select("SELECT * FROM nqz_ticket WHERE tkt_id = #{tktId}")
  TicketEntry findTicketById(int tktId);

  @Insert("INSERT INTO nqz_ticket (tkt_online, tkt_visit_date, tkt_price, tkt_discount, tkt_ispaid, tkttype_id) " +
          "VALUES (#{tktOnline}, str_to_date( #{tktVisitDate} , '%Y-%m-%d' ), #{tktPrice}, #{tktDiscount}, #{tktIspaid}, #{tktTypeId});")
  int addTicket(String tktOnline, String tktVisitDate, int tktPrice, int tktDiscount, String tktIspaid, int tktTypeId);

  @Select("SELECT * FROM nqz_tkt_type")
  List<TicketTypeEntry> findAllTicketTypes();

  @Insert("INSERT INTO nqz_tkt_type (tkttype_name) VALUES (#{tktTypeName})")
  int addTicketType(String tktTypeName);

  @Insert("INSERT INTO nqz_tkt_att  (tkt_id, att_id, tkt_att_time) " +
          "VALUES (#{tktId}, #{attId}, str_to_date( #{tktAttTime} , '%Y-%m-%d %H:%i:%s' ))")
  int addAttractionToTicket(int tktId, int attId, String tktAttTime);

  @Select("update nqz_ticket set tkt_ispaid = '1' where tkt_id = #{tktId}")
  void payTicket(int tktId);

  @Select("SELECT COUNT(*) FROM nqz_ticket WHERE tkttype_id = 1")
  int getChildCount();

  @Select("SELECT COUNT(*) FROM nqz_ticket WHERE tkttype_id = 2")
  int getAdultCount();

  @Select("SELECT COUNT(*) FROM nqz_ticket WHERE tkttype_id = 3")
  int getSeniorCount();

}
