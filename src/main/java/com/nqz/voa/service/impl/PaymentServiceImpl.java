package com.nqz.voa.service.impl;

import com.nqz.voa.entry.PaymentEntry;
import com.nqz.voa.mapper.PaymentMapper;
import com.nqz.voa.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  protected PaymentMapper paymentMapper;

  @Override
  public List<PaymentEntry> findAllPayments() {
    return paymentMapper.findAllPayments();
  }

  @Override
  public List<PaymentEntry> findPaymentByOrderId(int oId) {
    return paymentMapper.findPaymentByOrderId(oId);
  }

  @Override
  public PaymentEntry findPaymentById(int payId) {
    return paymentMapper.findPaymentById(payId);
  }

  @Override
  public int addNewPayment(String payTime, long payAmount, String payMethod) {
    return paymentMapper.addNewPayment(payTime, payAmount, payMethod);
  }

  @Override
  public int addCashPay(int payId, int caChange) {
    return paymentMapper.addCashPay(payId, caChange);
  }

  @Override
  public int addCreditDebitPay(int payId, String cdName, String cdNum, String cdExDate, String cdCvv, String cdCredit) {
    return paymentMapper.addCreditDebitPay(payId, cdName, cdNum, cdExDate, cdCvv, cdCredit);
  }

}
