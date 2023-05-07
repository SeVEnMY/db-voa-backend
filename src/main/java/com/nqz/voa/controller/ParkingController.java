package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.ParkingEntry;
import com.nqz.voa.entry.ParkingLotEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.ParkingService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/parking")
@Api(tags = "Parking")
public class ParkingController {
  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private ParkingService parkingService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<ParkingEntry>> findAllParkings(HttpServletRequest request, HttpServletResponse response) {
    Result<List<ParkingEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", parkingService.findAllParkings());
    return result;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public int addParking(@RequestParam String parkTimeIn,
                        @RequestParam String parkTimeOut,
                        @RequestParam int parkFee,
                        @RequestParam int parkSoptNo,
                        @RequestParam int plId) {

    return parkingService.addParking(parkTimeIn, parkTimeOut, parkFee, parkSoptNo, plId);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<ParkingEntry> findParkingById(@RequestParam int parkId, HttpServletRequest request, HttpServletResponse response) {
    Result<ParkingEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    ParkingEntry parkingEntry = parkingService.findParkingById(parkId);
    if (parkingEntry == null) {
      result.setResultSuccess("Not found!", null);
      return result;
    }
    result.setResultSuccess("Success!", parkingEntry);
    return result;
  }

  @RequestMapping(value = "/listpl", method = RequestMethod.GET)
  public Result<List<ParkingLotEntry>> findAllParkingLots(HttpServletRequest request, HttpServletResponse response) {
    Result<List<ParkingLotEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", parkingService.findAllParkingLots());
    return result;
  }

  @RequestMapping(value = "/count", method = RequestMethod.GET)
  public Object getParkingCount(HttpServletRequest request, HttpServletResponse response) {
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

    json.put("parkinglota", parkingService.getParkingLotACount());
    json.put("parkinglotb", parkingService.getParkingLotBCount());
    json.put("parkinglotc", parkingService.getParkingLotCCount());
    json.put("premiumlot", parkingService.getPremiumLotCount());
    return json;
  }

}
