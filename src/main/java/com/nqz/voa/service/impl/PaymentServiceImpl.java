package com.nqz.voa.service.impl;

import com.nqz.voa.entry.CashPayEntry;
import com.nqz.voa.entry.CreditDebitPayEntry;
import com.nqz.voa.entry.PaymentEntry;
import com.nqz.voa.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
  @Override
  public List<PaymentEntry> findPaymentByOrderId(int oId) {
    return null;
  }

  @Override
  public PaymentEntry findPaymentById(int payId) {
    return null;
  }

  @Override
  public int addNewPayment(String payTime, long payAmount, String payMethod) {
    return 0;
  }

  @Override
  public Integer findPaymentByInfo(String payTime, long payAmount, String payMethod) {
    return null;
  }

  @Override
  public int addCashPay(int payId, int caChange) {
    return 0;
  }

  @Override
  public int addCreditDebitPay(int payId, String cdName, String cdNum, String cdExDate, String cdCvv, String cdCredit) {
    return 0;
  }

  @Override
  public CashPayEntry findCashPayById(int payId) {
    return null;
  }

  @Override
  public CreditDebitPayEntry findCreditDebitPayById(int payId) {
    return null;
  }
}
