package com.nqz.voa.controller;

import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.ShowEntry;
import com.nqz.voa.entry.ShowTypeEntry;
import com.nqz.voa.model.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.ShowService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/show")
@Api(tags = "Show")
public class ShowController {
  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private ShowService showService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<ShowEntry>> findAllShows(HttpServletRequest request, HttpServletResponse response) {
    Result<List<ShowEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", showService.findAllShows());
    return result;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public int addShow(@RequestParam String shName,
                     @RequestParam String shDescription,
                     @RequestParam String shStartTime,
                     @RequestParam String shEndTime,
                     @RequestParam String shWheelchairAcc,
                     @RequestParam int shPrice,
                     @RequestParam int shTypeId) {

    return showService.addShow(shName, shDescription, shStartTime, shEndTime, shWheelchairAcc, shPrice, shTypeId);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<ShowEntry> findShowById(@RequestParam int shId, HttpServletRequest request, HttpServletResponse response) {
    Result<ShowEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    ShowEntry showEntry = showService.findShowById(shId);
    if (showEntry == null) {
      result.setResultSuccess("Not found!", null);
      return result;
    }
    result.setResultSuccess("Success!", showEntry);
    return result;
  }

  @RequestMapping(value = "/listshtype", method = RequestMethod.GET)
  public Result<List<ShowTypeEntry>> findAllShowTypes(HttpServletRequest request, HttpServletResponse response) {
    Result<List<ShowTypeEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", showService.findAllShowTypes());
    return result;
  }

  @RequestMapping(value = "/addshtype", method = RequestMethod.POST)
  public int addShowType(@RequestParam String shTypeName, HttpServletRequest request, HttpServletResponse response) {
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

    return showService.addShowType(shTypeName);
  }

}