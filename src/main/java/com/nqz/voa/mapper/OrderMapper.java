package com.nqz.voa.mapper;

import com.nqz.voa.entry.OrderEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

  @Select("SELECT * FROM nqz_order")
  List<OrderEntry> findAllOrders();

  @Select("SELECT * FROM nqz_order WHERE o_id = #{oId}")
  OrderEntry findOrderById(int oId);

  @Select("SELECT * FROM nqz_order WHERE v_id = #{vId}")
  List<OrderEntry> findOrderByVID(int vId);

  @Insert("INSERT INTO nqz_order (o_date, o_quantity, o_amount, pay_id, v_id, sh_id, st_id, mi_id, park_id, tkt_id) " +
          "VALUES (str_to_date( #{oDate} , '%Y-%m-%d' ), #{oQuantity}, #{oAmount}, #{payId}, #{vId}, #{shId}, #{stId}, #{miId}, #{parkId}, #{tktId});")
  Integer createNewOrder(String oDate, int oQuantity, long oAmount, Integer shId, int vId, Integer payId, Integer stId, Integer miId, Integer tktId, Integer parkId);

  @Select("update nqz_order set o_amount = #{oAmount} where o_id = #{oId}")
  void updateOrderAmount(int oId, long oAmount);

  @Select("update nqz_order set pay_id = #{payId} where o_id = #{oId}")
  void updatePayId(int oId, Integer payId);

}
