package com.nqz.voa.controller;


import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.OrderEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.OrderService;
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
  public Result<OrderEntry> createOrder(HttpServletRequest request,
                                        @RequestParam String oDate,
                                        @RequestParam int oQuantity,
                                        @RequestParam long oAmount,
                                        @RequestParam int shId,
                                        @RequestParam int vId,
                                        @RequestParam int payId,
                                        @RequestParam int stId,
                                        @RequestParam int miId,
                                        @RequestParam int tktId,
                                        @RequestParam int parkId) {

    Result<OrderEntry> result = new Result<>();
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
    result.setResultSuccess("Success!", null);

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

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();

    if (!accountService.isAdmin(accEmail)) {
      json.put("message", "No Permission!");
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

}
