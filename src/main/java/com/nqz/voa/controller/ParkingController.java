package com.nqz.voa.controller;

import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.ParkingEntry;
import com.nqz.voa.entry.ParkingLotEntry;
import com.nqz.voa.model.Result;
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

  @RequestMapping(value = "/addpl", method = RequestMethod.POST)
  public int addParkingLot(@RequestParam String plName, HttpServletRequest request, HttpServletResponse response) {
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

    return parkingService.addParkingLot(plName);
  }

}
