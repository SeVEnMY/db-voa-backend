package com.nqz.voa.controller;

import com.nqz.voa.entry.*;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.AttractionService;
import com.nqz.voa.service.HelperService;
import com.nqz.voa.service.TicketService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ticket")
@Api(tags = "Ticket")
public class TicketController {
  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private TicketService ticketService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private AccountService accountService;

  @Autowired
  private AttractionService attractionService;

  @Autowired
  private HelperService helperService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<TicketEntry>> findAllTickets(HttpServletRequest request, HttpServletResponse response) {
    Result<List<TicketEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", ticketService.findAllTickets());
    return result;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public int addTicket(@RequestParam String tktOnline,
                       @RequestParam String tktVisitDate,
                       @RequestParam int tktPrice,
                       @RequestParam int tktDiscount,
                       @RequestParam String tktIspaid,
                       @RequestParam int tktTypeId) {

    ticketService.addTicket(tktOnline, tktVisitDate, tktPrice, tktDiscount, tktIspaid, tktTypeId);
    return helperService.getLastInsertedId();
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<TicketEntry> findTicketById(@RequestParam int tktId, HttpServletRequest request, HttpServletResponse response) {
    Result<TicketEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    TicketEntry ticketEntry = ticketService.findTicketById(tktId);
    if (ticketEntry == null) {
      result.setResultSuccess("Not found!", null);
      return result;
    }
    result.setResultSuccess("Success!", ticketEntry);
    return result;
  }

  @RequestMapping(value = "/listtkttype", method = RequestMethod.GET)
  public Result<List<TicketTypeEntry>> findAllTicketTypes(HttpServletRequest request, HttpServletResponse response) {
    Result<List<TicketTypeEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", ticketService.findAllTicketTypes());
    return result;
  }

  @RequestMapping(value = "/addtkttype", method = RequestMethod.POST)
  public int addTicketType(@RequestParam String tktTypeName, HttpServletRequest request, HttpServletResponse response) {
    if (!accountController.isLogin(request, response).isSuccess()) {
      response.setStatus(403);
      return -1;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      response.setStatus(403);
      return -1;
    }

    return ticketService.addTicketType(tktTypeName);
  }

  @RequestMapping(value = "/addatttkt", method = RequestMethod.POST)
  public int addAttractionToTicket(@RequestParam int tktId, @RequestParam int attId, @RequestParam String tktAttTime, HttpServletRequest request, HttpServletResponse response) {
    if (!accountController.isLogin(request, response).isSuccess()) {
      response.setStatus(403);
      return -1;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      response.setStatus(403);
      return -1;
    }

    TicketEntry ticketEntry = ticketService.findTicketById(tktId);
    AttractionEntry attractionEntry = attractionService.findAttractionById(attId);

    if (ticketEntry == null || attractionEntry == null) {
      response.setStatus(404);
      return -1;
    }

    return ticketService.addAttractionToTicket(tktId, attId, tktAttTime);
  }
}
