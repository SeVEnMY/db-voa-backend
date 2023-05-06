package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.*;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.HelperService;
import com.nqz.voa.service.VisitorService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@CrossOrigin
@RestController
@RequestMapping("/account")
@Api(tags = "Account")
public class AccountController {

  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private VisitorService visitorService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private HelperService helperService;

  @RequestMapping(value = "/type", method = RequestMethod.GET)
  public Result<String> getVisitType(@RequestParam(value = "accEmail")String accEmail) {
    Result<String> result = new Result<>();
    if (accountService.findAccountByAccEmail(accEmail) == null) {
      result.setResultFailed("Account does not exist!");
      return result;
    }
    String vType = accountService.findVisitTypeByAccEmail(accEmail);
    result.setResultSuccess("Success!", vType);
    return result;
  }

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public Result<ProfileEntry> getAccountProfile(HttpServletRequest request, HttpServletResponse response) {
    Result<ProfileEntry> result = new Result<>();

    if (!this.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in!");
      return result;
    }

    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();

    AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
    String vType = accountService.findVisitTypeByAccEmail(accEmail);
    ProfileEntry profileEntry = new ProfileEntry();
    VisitorEntry visitorEntry = visitorService.findVisitorById(accountEntry.getV_id());

    profileEntry.setAccEmail(accEmail);
    profileEntry.setVFName(visitorEntry.getV_fname());
    profileEntry.setVMName(visitorEntry.getV_mname());
    profileEntry.setVLName(visitorEntry.getV_lname());
    profileEntry.setVStAdd(visitorEntry.getV_stadd());
    profileEntry.setVCity(visitorEntry.getV_city());
    profileEntry.setVState(visitorEntry.getV_state());
    profileEntry.setVCountry(visitorEntry.getV_country());
    profileEntry.setVEmail(visitorEntry.getV_email());
    profileEntry.setVTelNum(visitorEntry.getV_telnum());
    profileEntry.setVType(vType);
    profileEntry.setVBDate(visitorEntry.getV_bdate());

    if (vType.equals("G")) {
      GroupVisitEntry groupVisitEntry = visitorService.findGroupVisitById(accountEntry.getV_id());
      profileEntry.setGSize(groupVisitEntry.getG_size());
    } else if (vType.equals("M")) {
      MemberVisitEntry memberVisitEntry = visitorService.findMemberVisitById(accountEntry.getV_id());
      profileEntry.setMStartDate(memberVisitEntry.getM_startdate());
      profileEntry.setMEndDate(memberVisitEntry.getM_enddate());
      profileEntry.setMNumPurchased(memberVisitEntry.getM_num_purchased());
    } else if (vType.equals("S")) {
      StudentVisitEntry studentVisitEntry = visitorService.findStuVisitById(accountEntry.getV_id());
      profileEntry.setStuSchool(studentVisitEntry.getStu_school());
    } else if (vType.equals("I")) {
      IndividualVisitEntry individualVisitEntry = visitorService.findIndiVisitById(accountEntry.getV_id());
      profileEntry.setITimesVisit(individualVisitEntry.getI_times_visit());
    }
    result.setResultSuccess("Success!", profileEntry);
    return result;
  }

  @RequestMapping(value = "/getage", method = RequestMethod.GET)
  public Result<Integer> getAge(HttpServletRequest request, HttpServletResponse response) {
    Result<Integer> result = new Result<>();

    if (!this.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in!");
      return result;
    }

    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();

    AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
    VisitorEntry visitorEntry = visitorService.findVisitorById(accountEntry.getV_id());
    String vBirthDate = visitorEntry.getV_bdate();
    String[] dateParts = vBirthDate.split(" ");
    LocalDate date = LocalDate.parse(dateParts[0]);
    LocalDate today = LocalDate.now();

    result.setResultSuccess("Success!", Period.between(date, today).getYears());
    return result;
  }

  @RequestMapping(value = "/gettype", method = RequestMethod.GET)
  public Result<String> getType(HttpServletRequest request, HttpServletResponse response) {
    Result<String> result = new Result<>();

    if (!this.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in!");
      return result;
    }

    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();

    AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
    VisitorEntry visitorEntry = visitorService.findVisitorById(accountEntry.getV_id());

    result.setResultSuccess("Success!", visitorEntry.getV_type());
    return result;
  }

  @RequestMapping(value = "/group/register", method = RequestMethod.POST)
  public Object groupRegister(@RequestBody GroupRegisterRequestEntry request) {
    String accEmail = request.getAccEmail();
    String accPwd = DigestUtils.md5Hex(request.getAccPwd());
    JSONObject json = new JSONObject();

    if (accEmail == null || accEmail.equals("") || accPwd.equals("")) {
      json.put("message","Account Name or password should not be empty. ");
    } else {
      if (accountService.findAccountByAccEmail(accEmail) != null) {
        json.put("status", "fail");
        json.put("message", "accemail");
        return json;
      }
      String vFName = request.getVfName();
      String vMName = request.getVmName();
      String vLName = request.getVlName();
      String vStAdd = request.getVstAdd();
      String vCity = request.getVcity();
      String vState = request.getVstate();
      String vCountry = request.getVcountry();
      String vEmail = request.getVemail();
      String vTelNum = request.getVtelNum();
      String vType = request.getVtype();
      String vBDate = request.getVbDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = helperService.getLastInsertedId();
      int gSize = request.getGsize();
      visitorService.addGroupVisitor(vId, gSize);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAcc_id();
      accountService.addAccRole(accId);

      json.put("status", "success");
      json.put("message", "Account registered successfully");
    }
    return json;
  }

  @RequestMapping(value = "/member/register", method = RequestMethod.POST)
  public Object memberRegister(@RequestBody MemberRegisterRequestEntry request) {
    String accEmail = request.getAccEmail();
    String accPwd = DigestUtils.md5Hex(request.getAccPwd());
    JSONObject json = new JSONObject();

    if (accEmail == null || accEmail.equals("") || accPwd.equals("")) {
      json.put("message","Account Name or password should not be empty. ");
    } else {
      if (accountService.findAccountByAccEmail(accEmail) != null) {
        json.put("status", "fail");
        json.put("message", "accemail");
        return json;
      }
      String vFName = request.getVfName();
      String vMName = request.getVmName();
      String vLName = request.getVlName();
      String vStAdd = request.getVstAdd();
      String vCity = request.getVcity();
      String vState = request.getVstate();
      String vCountry = request.getVcountry();
      String vEmail = request.getVemail();
      String vTelNum = request.getVtelNum();
      String vType = request.getVtype();
      String vBDate = request.getVbDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = helperService.getLastInsertedId();
      String mStartDate = request.getMstartDate();
      String mEndDate = request.getMendDate();
      int mNumPurchased = request.getMnumPurchased();
      visitorService.addMemberVisitor(vId, mStartDate, mEndDate, mNumPurchased);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAcc_id();
      accountService.addAccRole(accId);

      json.put("status", "success");
      json.put("message", "Account registered successfully");
    }
    return json;
  }

  @RequestMapping(value = "/stu/register", method = RequestMethod.POST)
  public Object studentRegister(@RequestBody StuRegisterRequestEntry request) {
    String accEmail = request.getAccEmail();
    String accPwd = DigestUtils.md5Hex(request.getAccPwd());
    JSONObject json = new JSONObject();

    if (accEmail == null || accEmail.equals("") || accPwd.equals("")) {
      json.put("message","Account Name or password should not be empty. ");
    } else {
      if (accountService.findAccountByAccEmail(accEmail) != null) {
        json.put("status", "fail");
        json.put("message", "accemail");
        return json;
      }
      String vFName = request.getVfName();
      String vMName = request.getVmName();
      String vLName = request.getVlName();
      String vStAdd = request.getVstAdd();
      String vCity = request.getVcity();
      String vState = request.getVstate();
      String vCountry = request.getVcountry();
      String vEmail = request.getVemail();
      String vTelNum = request.getVtelNum();
      String vType = request.getVtype();
      String vBDate = request.getVbDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = helperService.getLastInsertedId();
      String stuSchool = request.getStuSchool();
      visitorService.addStudentVisitor(vId, stuSchool);
      accountService.Register(accEmail, accPwd, vId);
      System.out.println(accEmail);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      System.out.println(accountEntry);
      int accId = accountEntry.getAcc_id();
      accountService.addAccRole(accId);

      json.put("status", "success");
      json.put("message", "Account registered successfully");
    }
    return json;
  }

  @RequestMapping(value = "/indi/register", method = RequestMethod.POST)
  public Object indiRegister(@RequestBody IndiRegisterRequestEntry request) {
    String accEmail = request.getAccEmail();
    String accPwd = DigestUtils.md5Hex(request.getAccPwd());
    JSONObject json = new JSONObject();

    if (accEmail == null || accEmail.equals("") || accPwd.equals("")) {
      json.put("message","Account Name or password should not be empty. ");
    } else {
      if (accountService.findAccountByAccEmail(accEmail) != null) {
        json.put("status", "fail");
        json.put("message", "accemail");
        return json;
      }
      String vFName = request.getVfName();
      String vMName = request.getVmName();
      String vLName = request.getVlName();
      String vStAdd = request.getVstAdd();
      String vCity = request.getVcity();
      String vState = request.getVstate();
      String vCountry = request.getVcountry();
      String vEmail = request.getVemail();
      String vTelNum = request.getVtelNum();
      String vType = request.getVtype();
      String vBDate = request.getVbDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = helperService.getLastInsertedId();
      int iTimesVisit = request.getItimesVisit();
      visitorService.addIndividualVisitor(vId, iTimesVisit);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAcc_id();
      accountService.addAccRole(accId);

      json.put("status", "success");
      json.put("message", "Account registered successfully");
    }
    return json;
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Object login(@RequestBody LoginRequestEntry loginRequestEntry, HttpServletRequest request, HttpServletResponse response) {
    Result<AccountEntry> result;

    result = accountService.login(loginRequestEntry);

    if (result.isSuccess()) {
      request.getSession().setAttribute(SESSION_NAME, result.getData());
    }
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    result.setData(null);
    return result;
  }

  @RequestMapping(value = "/islogin", method = RequestMethod.GET)
  public Result<AccountEntry> isLogin(HttpServletRequest request, HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Credentials", "ture");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    HttpSession session = request.getSession();

    Result<AccountEntry> result = new Result<>();

    AccountEntry sessionUser = (AccountEntry) session.getAttribute(SESSION_NAME);

    if (sessionUser == null) {
      result.setResultFailed("Not logged in!");
      return result;
    }

    AccountEntry getUser = null;
    try {
      getUser = accountService.findAccountByAccEmail(sessionUser.getAcc_email());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getUser == null || !getUser.getAcc_pwd().equals(sessionUser.getAcc_pwd())) {
      result.setResultFailed("Account info mismatch!");
      return result;
    }
    result.setResultSuccess("Logged In!", null);
    return result;
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public Object logout(HttpServletRequest request, HttpServletResponse response) {
    Result result = new Result();
    request.getSession().setAttribute(SESSION_NAME, null);
    result.setResultSuccess("Successfully logged out!", null);
    response.setHeader("Access-Control-Allow-Credentials", "ture");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    return result;
  }

  @RequestMapping(value = "getaccemail", method = RequestMethod.GET)
  public Result<String> getCookieAccEmail(HttpServletRequest request) {
    Result<String> result = new Result<>();
    HttpSession session = request.getSession();
    AccountEntry sessionUser = (AccountEntry) session.getAttribute(SESSION_NAME);

    if (sessionUser == null) {
      result.setResultFailed("Not logged in!");
      return result;
    }

    AccountEntry getUser = null;

    try {
      getUser = accountService.findAccountByAccEmail(sessionUser.getAcc_email());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getUser != null) {
      result.setResultSuccess("Success!", getUser.getAcc_email());
      return result;
    }
    result.setResultFailed("Failed");
    return result;
  }

  @RequestMapping(value = "/getrole", method = RequestMethod.GET)
  public Result<String> getCookieRole(HttpServletRequest request, HttpServletResponse response) {
    Result<String> result = new Result<>();
    HttpSession session = request.getSession();

    if (!this.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    String role = accountService.getRoleByAccEmail(accEmail);
    result.setResultSuccess("Success!", role);
    return result;
  }

}
