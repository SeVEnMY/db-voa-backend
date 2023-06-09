package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.CashPayEntry;
import com.nqz.voa.entry.CreditDebitPayEntry;
import com.nqz.voa.entry.PaymentEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.HelperService;
import com.nqz.voa.service.PaymentService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/payment")
@Api(tags = "Payment")
public class PaymentController {

  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private HelperService helperService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<PaymentEntry>> findAllPayments(HttpServletRequest request, HttpServletResponse response) {
    Result<List<PaymentEntry>> result = new Result<>();

    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      result.setResultFailed("No Permission!");
      return result;
    }

    result.setResultSuccess("Success!", paymentService.findAllPayments());
    return result;
  }
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<PaymentEntry> findPaymentByPayId(@RequestParam int payId, HttpServletRequest request, HttpServletResponse response) {
    Result<PaymentEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    PaymentEntry paymentEntry = paymentService.findPaymentById(payId);
    if (paymentEntry == null) {
      result.setResultSuccess("Not Found!", null);
      return result;
    }
    result.setResultSuccess("Success!", paymentEntry);
    return result;
  }

  @RequestMapping(value = "/getbyorder", method = RequestMethod.GET)
  public Result<List<PaymentEntry>> findPaymentByOrderId(@RequestParam int oId, HttpServletRequest request, HttpServletResponse response) {
    Result<List<PaymentEntry>> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    List<PaymentEntry> paymentList = paymentService.findPaymentByOrderId(oId);
    if (paymentList == null) {
      result.setResultSuccess("Empty!", null);
      return result;
    }
    result.setResultSuccess("Success!", paymentList);
    return result;
  }

  @RequestMapping(value = "/addcash", method = RequestMethod.POST)
  public int addCashPayment(@RequestParam String payTime,
                            @RequestParam long payAmount,
                            @RequestParam String payMethod,
                            @RequestParam int caChange) {

    paymentService.addNewPayment(payTime, payAmount, payMethod);
    int payId = helperService.getLastInsertedId();
    paymentService.addCashPay(payId, caChange);
    return helperService.getLastInsertedId();
  }

  @RequestMapping(value = "/addcd", method = RequestMethod.POST)
  public int addCreditDebitPay(@RequestParam String payTime,
                               @RequestParam long payAmount,
                               @RequestParam String payMethod,
                               @RequestParam String cdName,
                               @RequestParam String cdNum,
                               @RequestParam String cdExDate,
                               @RequestParam String cdCvv,
                               @RequestParam String cdCredit) {

    paymentService.addNewPayment(payTime, payAmount, payMethod);
    int payId = helperService.getLastInsertedId();
    paymentService.addCreditDebitPay(payId, cdName, cdNum, cdExDate, cdCvv, cdCredit);
    return helperService.getLastInsertedId();
  }

  @RequestMapping(value = "/getcash", method = RequestMethod.GET)
  public Result<CashPayEntry> getCashPayByPayId(@RequestParam int payId, HttpServletRequest request, HttpServletResponse response) {
    Result<CashPayEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    PaymentEntry paymentEntry = paymentService.findPaymentById(payId);
    CashPayEntry cashPayEntry = paymentService.getCashPayByPayId(payId);
    if (paymentEntry == null || cashPayEntry == null) {
      result.setResultSuccess("Not Found!", null);
      return result;
    }
    result.setResultSuccess("Success!", cashPayEntry);
    return result;
  }

  @RequestMapping(value = "/getcd", method = RequestMethod.GET)
  public Result<CreditDebitPayEntry> getCreditDebitPayByPayId(@RequestParam int payId, HttpServletRequest request, HttpServletResponse response) {
    Result<CreditDebitPayEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    PaymentEntry paymentEntry = paymentService.findPaymentById(payId);
    CreditDebitPayEntry creditDebitPayEntry = paymentService.getCreditDebitPayByPayId(payId);
    if (paymentEntry == null || creditDebitPayEntry == null) {
      result.setResultSuccess("Not Found!", null);
      return result;
    }
    result.setResultSuccess("Success!", creditDebitPayEntry);
    return result;
  }

  @RequestMapping(value = "/count", method = RequestMethod.GET)
  public Object getPaymentCount(HttpServletRequest request, HttpServletResponse response) {
    JSONObject json = new JSONObject();

    if (!accountController.isLogin(request, response).isSuccess()) {
      json.put("message", "Not logged in!");
      json.put("success", false);
      json.put("data", null);
      return json;
    }

    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      json.put("message", "No permission!");
      json.put("success", false);
      json.put("data", null);
      return json;
    }

    json.put("cash", paymentService.getCashCount());
    json.put("creditdebit", paymentService.getCreditDebitCount());
    return json;
  }
}
