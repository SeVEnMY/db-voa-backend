package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.enrty.*;
import com.nqz.voa.model.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.VisitorService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Api(tags = "Account")
public class AccountCountroller {

  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private AccountService accountService;

  @Autowired
  private VisitorService visitorService;

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
    String accEmail = sessionUser.getAccEmail();

    AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
    String vType = accountService.findVisitTypeByAccEmail(accEmail);
    ProfileEntry profileEntry = new ProfileEntry();
    VisitorEntry visitorEntry = visitorService.findVisitorById(accountEntry.getVId());

    profileEntry.setAccEmail(accEmail);
    profileEntry.setVFName(visitorEntry.getVFName());
    profileEntry.setVMName(visitorEntry.getVMName());
    profileEntry.setVLName(visitorEntry.getVLName());
    profileEntry.setVStAdd(visitorEntry.getVStAdd());
    profileEntry.setVCity(visitorEntry.getVCity());
    profileEntry.setVState(visitorEntry.getVState());
    profileEntry.setVCountry(visitorEntry.getVCountry());
    profileEntry.setVEmail(visitorEntry.getVEmail());
    profileEntry.setVTelNum(visitorEntry.getVTelNum());
    profileEntry.setVType(vType);
    profileEntry.setVBDate(visitorEntry.getVBDate());

    if (vType.equals("G")) {
      GroupVisitEntry groupVisitEntry = visitorService.findGroupVisitById(accountEntry.getVId());
      profileEntry.setGSize(groupVisitEntry.getGSize());
    } else if (vType.equals("M")) {
      MemberVisitEntry memberVisitEntry = visitorService.findMemberVisitById(accountEntry.getVId());
      profileEntry.setMStartDate(memberVisitEntry.getMStartDate());
      profileEntry.setMEndDate(memberVisitEntry.getMEndDate());
      profileEntry.setMNumPurchased(memberVisitEntry.getMNumPurchased());
    } else if (vType.equals("S")) {
      StudentVisitEntry studentVisitEntry = visitorService.findStuVisitById(accountEntry.getVId());
      profileEntry.setStuSchool(studentVisitEntry.getStuSchool());
    } else if (vType.equals("I")) {
      IndividualVisitEntry individualVisitEntry = visitorService.findIndiVisitById(accountEntry.getVId());
      profileEntry.setITimesVisit(individualVisitEntry.getITimesVisit());
    }
    result.setResultSuccess("Success!", profileEntry);
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
      String vFName = request.getVFName();
      String vMName = request.getVMName();
      String vLName = request.getVLName();
      String vStAdd = request.getVStAdd();
      String vCity = request.getVCity();
      String vState = request.getVState();
      String vCountry = request.getVCountry();
      String vEmail = request.getVEmail();
      String vTelNum = request.getVTelNum();
      String vType = request.getVType();
      String vBDate = request.getVBDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
      int gSize = request.getGSize();
      visitorService.addGroupVisitor(vId, gSize);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAccId();
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
      String vFName = request.getVFName();
      String vMName = request.getVMName();
      String vLName = request.getVLName();
      String vStAdd = request.getVStAdd();
      String vCity = request.getVCity();
      String vState = request.getVState();
      String vCountry = request.getVCountry();
      String vEmail = request.getVEmail();
      String vTelNum = request.getVTelNum();
      String vType = request.getVType();
      String vBDate = request.getVBDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
      String mStartDate = request.getMStartDate();
      String mEndDate = request.getMEndDate();
      int mNumPurchased = request.getMNumPurchased();
      visitorService.addMemberVisitor(vId, mStartDate, mEndDate, mNumPurchased);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAccId();
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
      String vFName = request.getVFName();
      String vMName = request.getVMName();
      String vLName = request.getVLName();
      String vStAdd = request.getVStAdd();
      String vCity = request.getVCity();
      String vState = request.getVState();
      String vCountry = request.getVCountry();
      String vEmail = request.getVEmail();
      String vTelNum = request.getVTelNum();
      String vType = request.getVType();
      String vBDate = request.getVBDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
      String stuSchool = request.getStuSchool();
      visitorService.addStudentVisitor(vId, stuSchool);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAccId();
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
      String vFName = request.getVFName();
      String vMName = request.getVMName();
      String vLName = request.getVLName();
      String vStAdd = request.getVStAdd();
      String vCity = request.getVCity();
      String vState = request.getVState();
      String vCountry = request.getVCountry();
      String vEmail = request.getVEmail();
      String vTelNum = request.getVTelNum();
      String vType = request.getVType();
      String vBDate = request.getVBDate();

      visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
      int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
      int iTimesVisit = request.getITimesVisit();
      visitorService.addIndividualVisitor(vId, iTimesVisit);
      accountService.Register(accEmail, accPwd, vId);
      AccountEntry accountEntry = accountService.findAccountByAccEmail(accEmail);
      int accId = accountEntry.getAccId();
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
      getUser = accountService.findAccountByAccEmail(sessionUser.getAccEmail());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getUser == null || !getUser.getAccPWD().equals(sessionUser.getAccPWD())) {
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
      getUser = accountService.findAccountByAccEmail(sessionUser.getAccEmail());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getUser != null) {
      result.setResultSuccess("Success!", getUser.getAccEmail());
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
    String accEmail = sessionUser.getAccEmail();
    String role = accountService.getRoleByAccEmail(accEmail);
    result.setResultSuccess("Success!", role);
    return result;
  }

}
