package com.nqz.voa.service;

import com.nqz.voa.entry.CashPayEntry;
import com.nqz.voa.entry.CreditDebitPayEntry;
import com.nqz.voa.entry.PaymentEntry;
import java.util.List;

public interface PaymentService {

  List<PaymentEntry> findAllPayments();

  List<PaymentEntry> findPaymentByOrderId(int oId);

  PaymentEntry findPaymentById(int payId);

  int addNewPayment(String payTime, long payAmount, String payMethod);

  Integer findPaymentByInfo(String payTime, long payAmount, String payMethod);

  int addCashPay(int payId, int caChange);

  int addCreditDebitPay(int payId, String cdName, String cdNum, String cdExDate, String cdCvv, String cdCredit);

}
