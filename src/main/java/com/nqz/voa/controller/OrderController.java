package com.nqz.voa.controller;


import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.OrderEntry;
import com.nqz.voa.entry.PaymentEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.HelperService;
import com.nqz.voa.service.OrderService;
import com.nqz.voa.service.PaymentService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/order")
@Api(tags = "Order")
public class OrderController {

  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private OrderService orderService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private HelperService helperService;

  @Autowired
  private PaymentService paymentService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<OrderEntry>> findAllOrders(HttpServletRequest request, HttpServletResponse response) {
    Result<List<OrderEntry>> result = new Result<>();

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

    result.setResultSuccess("Success!", orderService.findAllOrders());
    return result;
  }

  @RequestMapping(value = "/visitlist", method = RequestMethod.GET)
  public Result<List<OrderEntry>> findOrderByAccEmail(HttpServletRequest request, HttpServletResponse response) {
    Result<List<OrderEntry>> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
    if (accountEntry == null) {
      result.setResultFailed("Account does not exist!");
      return result;
    }
    int vId = accountEntry.getV_id();
    List<OrderEntry> orderList = orderService.findOrderByVID(vId);
    if (orderList == null) {
      result.setResultSuccess("Empty!", null);
      return result;
    }
    result.setResultSuccess("Success!", orderList);
    return result;
  }

  @RequestMapping(value = "/createorder", method = RequestMethod.POST)
  public Result<Integer> createOrder(HttpServletRequest request,
                                        @RequestParam String oDate,
                                        @RequestParam int oQuantity,
                                        @RequestParam long oAmount,
                                        @RequestParam(required = false) Integer shId,
                                        @RequestParam int vId,
                                        @RequestParam(required = false) Integer payId,
                                        @RequestParam(required = false) Integer stId,
                                        @RequestParam(required = false) Integer miId,
                                        @RequestParam(required = false) Integer tktId,
                                        @RequestParam(required = false) Integer parkId) {

    Result<Integer> result = new Result<>();
    HttpSession session = request.getSession();
    // get user info from session
    AccountEntry sessionUser = (AccountEntry) session.getAttribute(SESSION_NAME);

    if (sessionUser == null) {
      result.setResultFailed("Not logged in！");
      return result;
    }
    // if logged, verify password using db info.
    AccountEntry getUser = null;
    try {
      getUser = accountService.findAccountByAccEmail(sessionUser.getAcc_email());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (getUser == null || !getUser.getAcc_pwd().equals(sessionUser.getAcc_pwd())) {
      result.setResultFailed("Account info not match！");
      return result;
    }

    orderService.createNewOrder(oDate, oQuantity, oAmount, shId, vId, payId, stId, miId, tktId, parkId);
    result.setResultSuccess("Success!", helperService.getLastInsertedId());

    return result;
  }

  @RequestMapping(value = "/updateoamount", method = RequestMethod.PUT)
  public Object updateOrderAmount(@RequestParam int oId, @RequestParam long oAmount,
                             HttpServletRequest request, HttpServletResponse response) {
    JSONObject json = new JSONObject();

    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      json.put("message", "Not logged in!");
      json.put("success", false);
      json.put("data", null);
      return json;
    }

    if (oId < 0 || oAmount < 0) {
      json.put("message", "Error invalid variables");
      return json;
    }

    OrderEntry orderEntry = orderService.findOrderById(oId);
    if (orderEntry == null) {
      json.put("message", String.format("Error no such order, orderId=%s", oId));
      return json;
    }

    orderService.updateOrderAmount(oId, oAmount);
    json.put("message", "Success!");
    return json;
  }

  @RequestMapping(value = "/updatepayment", method = RequestMethod.PUT)
  public Object updatePayId(@RequestParam int oId, @RequestParam Integer payId,
                                  HttpServletRequest request, HttpServletResponse response) {
    JSONObject json = new JSONObject();

    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      json.put("message", "Not logged in!");
      json.put("success", false);
      json.put("data", null);
      return json;
    }

    if (oId < 0 || payId < 0) {
      json.put("message", "Error invalid variables");
      return json;
    }

    OrderEntry orderEntry = orderService.findOrderById(oId);
    if (orderEntry == null) {
      json.put("message", String.format("Error no such order, orderId=%s", oId));
      return json;
    }

    PaymentEntry paymentEntry = paymentService.findPaymentById(payId);
    if (paymentEntry == null) {
      json.put("message", String.format("Error no such payment, payId=%s", payId));
      return json;
    }

    orderService.updatePayId(oId, payId);
    json.put("message", "Success!");
    return json;
  }

  @RequestMapping(value = "/count", method = RequestMethod.GET)
  public Object getOrderCount(HttpServletRequest request, HttpServletResponse response) {
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

    json.put("show", orderService.getShowOrderCount());
    json.put("store", orderService.getStoreOrderCount());
    json.put("ticket", orderService.getTicketOrderCount());
    json.put("parking", orderService.getParkOrderCount());
    return json;
  }

}
