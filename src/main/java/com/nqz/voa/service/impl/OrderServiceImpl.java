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
  public int createNewOrder(String oDate, int oQuantity, long oAmount, int shId, int vId, int payId, int stId, int miId, int tktId, int parkId) {
    return orderMapper.createNewOrder(oDate, oQuantity, oAmount, shId, vId, payId, stId, miId, tktId, parkId);
  }

  @Override
  public void updateOrderAmount(int oId, long oAmount) {
    orderMapper.updateOrderAmount(oId, oAmount);
  }

}
