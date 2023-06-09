package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.*;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.AttractionService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/attraction")
@Api(tags = "Attraction")
public class AttractionController {
  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private AttractionService attractionService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<AttractionEntry>> findAllAttractions(HttpServletRequest request, HttpServletResponse response) {
    Result<List<AttractionEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", attractionService.findAllAttractions());
    return result;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public int addAttraction(@RequestParam String attName,
                           @RequestParam String attDescription,
                           @RequestParam String attStatus,
                           @RequestParam int attCapacity,
                           @RequestParam(required = false) Integer attMinimumHeight,
                           @RequestParam int attDurationTime,
                           @RequestParam int lsId,
                           @RequestParam int attTypeId) {

    return attractionService.addAttraction(attName, attDescription, attStatus, attCapacity, attMinimumHeight, attDurationTime, lsId, attTypeId);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<AttractionEntry> findAttractionById(@RequestParam int attId, HttpServletRequest request, HttpServletResponse response) {
    Result<AttractionEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    AttractionEntry attractionEntry = attractionService.findAttractionById(attId);
    if (attractionEntry == null) {
      result.setResultSuccess("Not found!", null);
      return result;
    }
    result.setResultSuccess("Success!", attractionEntry);
    return result;
  }

  @RequestMapping(value = "/listatttype", method = RequestMethod.GET)
  public Result<List<AttractionTypeEntry>> findAllAttractionTypes(HttpServletRequest request, HttpServletResponse response) {
    Result<List<AttractionTypeEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", attractionService.findAllAttractionTypes());
    return result;
  }

  @RequestMapping(value = "/addatttype", method = RequestMethod.POST)
  public int addAttractionType(@RequestParam String attTypeName, HttpServletRequest request, HttpServletResponse response) {
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

    return attractionService.addAttractionType(attTypeName);
  }

  @RequestMapping(value = "/listls", method = RequestMethod.GET)
  public Result<List<LocationSectionEntry>> findAllLocationSections(HttpServletRequest request, HttpServletResponse response) {
    Result<List<LocationSectionEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", attractionService.findAllLocationSections());
    return result;
  }

  @RequestMapping(value = "/addls", method = RequestMethod.POST)
  public int addLocationSection(@RequestParam String lsName, HttpServletRequest request, HttpServletResponse response) {
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

    return attractionService.addLocationSection(lsName);
  }

  @RequestMapping(value = "/getatt", method = RequestMethod.GET)
  public Result<List<AttractionEntry>> findAttractionByTicketId(@RequestParam int tktId, HttpServletRequest request, HttpServletResponse response) {
    Result<List<AttractionEntry>> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    List<AttractionEntry> attractionEntryList = attractionService.findAttractionByTicketId(tktId);
    if (attractionEntryList == null) {
      result.setResultSuccess("Empty!", null);
      return result;
    }
    result.setResultSuccess("Success!", attractionEntryList);
    return result;
  }

  @RequestMapping(value = "/count", method = RequestMethod.GET)
  public Object getAttractionCount(HttpServletRequest request, HttpServletResponse response) {
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

    json.put("rollercoaster", attractionService.getRollerCoasterCount());
    json.put("waterride", attractionService.getWaterRideCount());
    json.put("darkride", attractionService.getDarkRideCount());
    json.put("kidride", attractionService.getKidRideCount());
    json.put("vrride", attractionService.getVrRideCount());
    return json;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public Object deleteAttractionById(@RequestParam int attId, HttpServletRequest request, HttpServletResponse response) {
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

    AttractionEntry attractionEntry = attractionService.findAttractionById(attId);

    if (attractionEntry == null) {
      json.put("message", "Attraction not found!");
      json.put("success", false);
      json.put("data", null);
      return json;
    }

    attractionService.deleteTktAttByAttId(attId);
    attractionService.deleteAttractionById(attId);
    json.put("message", "Attraction deleted!");
    json.put("success", true);
    json.put("data", null);

    return json;
  }

}
