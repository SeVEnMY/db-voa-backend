package com.nqz.voa.service;

import com.nqz.voa.entry.OrderEntry;

import java.util.List;

public interface OrderService {
  List<OrderEntry> findAllOrders();

  OrderEntry findOrderById(int oId);

  List<OrderEntry> findOrderByVID(int vId);

  int createNewOrder(String oDate, int oQuantity, long oAmount, int shId, int vId, int payId, int stId, int miId, int tktId, int parkId);

  void updateOrderAmount(int oId, long oAmount);

}
