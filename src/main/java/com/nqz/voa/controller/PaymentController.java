package com.nqz.voa.controller;

import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.PaymentEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
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
    int payId = paymentService.findPaymentByInfo(payTime, payAmount, payMethod);
    return paymentService.addCashPay(payId, caChange);
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
    int payId = paymentService.findPaymentByInfo(payTime, payAmount, payMethod);
    return paymentService.addCreditDebitPay(payId, cdName, cdNum, cdExDate, cdCvv, cdCredit);
  }
}
