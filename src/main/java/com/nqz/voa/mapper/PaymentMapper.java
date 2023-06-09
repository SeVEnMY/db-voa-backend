package com.nqz.voa.mapper;

import com.nqz.voa.entry.CashPayEntry;
import com.nqz.voa.entry.CreditDebitPayEntry;
import com.nqz.voa.entry.PaymentEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaymentMapper {

  @Select("SELECT * FROM nqz_payment")
  List<PaymentEntry> findAllPayments();

  @Select("SELECT * FROM nqz_payment NATURAL JOIN nqz_order WHERE o_id = #{oId}")
  List<PaymentEntry> findPaymentByOrderId(int oId);

  @Select("SELECT * FROM nqz_payment WHERE pay_id = #{payId}")
  PaymentEntry findPaymentById(int payId);

  @Insert("INSERT INTO nqz_payment (pay_time, pay_amount, pay_method) VALUES (str_to_date( #{payTime} , '%Y-%m-%d %H:%i:%s' ), #{payAmount}, #{payMethod} ) ")
  int addNewPayment(String payTime, long payAmount, String payMethod);

  @Insert("INSERT INTO nqz_cash (pay_id, ca_change) VALUES (#{payId}, #{caChange})")
  int addCashPay(int payId, int caChange);

  @Insert("INSERT INTO nqz_credit_debit (pay_id, cd_name, cd_num, cd_exdate, cd_cvv, cd_credit) " +
          "VALUES (#{payId}, #{cdName}, #{cdNum}, str_to_date( #{cdExDate} , '%Y-%m-%d' ), #{cdCvv}, #{cdCredit})")
  int addCreditDebitPay(int payId, String cdName, String cdNum, String cdExDate, String cdCvv, String cdCredit);

  @Select("SELECT * FROM nqz_cash WHERE pay_id = #{payId}")
  CashPayEntry getCashPayByPayId(int payId);

  @Select("SELECT * FROM nqz_credit_debit WHERE pay_id = #{payId}")
  CreditDebitPayEntry getCreditDebitPayByPayId(int payId);

  @Select("SELECT COUNT(*) FROM nqz_cash")
  int getCashCount();

  @Select("SELECT COUNT(*) FROM nqz_credit_debit")
  int getCreditDebitCount();

}
