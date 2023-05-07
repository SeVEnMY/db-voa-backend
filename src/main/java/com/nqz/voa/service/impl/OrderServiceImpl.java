package com.nqz.voa.service.impl;

import com.nqz.voa.entry.OrderEntry;
import com.nqz.voa.mapper.OrderMapper;
import com.nqz.voa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  protected OrderMapper orderMapper;

  @Override
  public List<OrderEntry> findAllOrders() {
    return orderMapper.findAllOrders();
  }

  @Override
  public OrderEntry findOrderById(int oId) {
    return orderMapper.findOrderById(oId);
  }

  @Override
  public List<OrderEntry> findOrderByVID(int vId) {
    return orderMapper.findOrderByVID(vId);
  }

  @Override
  public int createNewOrder(String oDate, int oQuantity, long oAmount, Integer shId, int vId, Integer payId, Integer stId, Integer miId, Integer tktId, Integer parkId) {
    return orderMapper.createNewOrder(oDate, oQuantity, oAmount, shId, vId, payId, stId, miId, tktId, parkId);
  }

  @Override
  public void updateOrderAmount(int oId, long oAmount) {
    orderMapper.updateOrderAmount(oId, oAmount);
  }

  @Override
  public void updatePayId(int oId, Integer payId) {
    orderMapper.updatePayId(oId, payId);
  }

  @Override
  public int getShowOrderCount() {
    return orderMapper.getShowOrderCount();
  }

  @Override
  public int getStoreOrderCount() {
    return orderMapper.getStoreOrderCount();
  }

  @Override
  public int getTicketOrderCount() {
    return orderMapper.getTicketOrderCount();
  }

  @Override
  public int getParkOrderCount() {
    return orderMapper.getParkOrderCount();
  }

}
