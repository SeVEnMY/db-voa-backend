package com.nqz.voa.service;

import com.nqz.voa.entry.OrderEntry;

import java.util.List;

public interface OrderService {
  List<OrderEntry> findAllOrders();

  OrderEntry findOrderById(int oId);

  List<OrderEntry> findOrderByVID(int vId);

  int createNewOrder(String oDate, int oQuantity, long oAmount, Integer shId, int vId, Integer payId, Integer stId, Integer miId, Integer tktId, Integer parkId);

  void updateOrderAmount(int oId, long oAmount);

  void updatePayId(int oId, Integer payId);

}
