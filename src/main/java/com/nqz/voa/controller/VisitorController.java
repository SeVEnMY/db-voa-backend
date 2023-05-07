package com.nqz.voa.controller;

import com.alibaba.fastjson.JSONObject;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.MemberVisitEntry;
import com.nqz.voa.entry.VisitorEntry;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.HelperService;
import com.nqz.voa.service.VisitorService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/visitor")
@Api(tags = "Visitor")
public class VisitorController {

    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<VisitorEntry>> findAllVisitors(HttpServletRequest request, HttpServletResponse response) {
        Result<List<VisitorEntry>> result = new Result<>();

        /*check admin*/
        if (!accountController.isLogin(request, response).isSuccess()) {
            result.setResultFailed("Not logged in！");
            return result;
        }

        AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
        String accEmail = sessionUser.getAcc_email();
        if (!accountService.isAdmin(accEmail)) {
            result.setResultFailed("No Permission!");
            return result;
        }


        result.setResultSuccess("Success!", visitorService.findAllVisitors());
        return result;
    }

    @RequestMapping(value = "/addgroup", method = RequestMethod.POST)
    public int addGroupVisitor(@RequestParam String vFName,
                               @RequestParam String vMName,
                               @RequestParam String vLName,
                               @RequestParam String vStAdd,
                               @RequestParam String vCity,
                               @RequestParam String vState,
                               @RequestParam String vCountry,
                               @RequestParam String vEmail,
                               @RequestParam String vTelNum,
                               @RequestParam String vType,
                               @RequestParam String vBDate,
                               @RequestParam int gSize) {

        visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
        int vId = helperService.getLastInsertedId();
        return visitorService.addGroupVisitor(vId, gSize);
    }

    @RequestMapping(value = "/addmember", method = RequestMethod.POST)
    public int addMemberVisitor(@RequestParam String vFName,
                                @RequestParam String vMName,
                                @RequestParam String vLName,
                                @RequestParam String vStAdd,
                                @RequestParam String vCity,
                                @RequestParam String vState,
                                @RequestParam String vCountry,
                                @RequestParam String vEmail,
                                @RequestParam String vTelNum,
                                @RequestParam String vType,
                                @RequestParam String vBDate,
                                @RequestParam String mStartDate,
                                @RequestParam String mEndDate,
                                @RequestParam int mNumPurchased) {

        visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
        int vId = helperService.getLastInsertedId();
        return visitorService.addMemberVisitor(vId, mStartDate, mEndDate, mNumPurchased);
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public int addStudentVisitor(@RequestParam String vFName,
                                 @RequestParam String vMName,
                                 @RequestParam String vLName,
                                 @RequestParam String vStAdd,
                                 @RequestParam String vCity,
                                 @RequestParam String vState,
                                 @RequestParam String vCountry,
                                 @RequestParam String vEmail,
                                 @RequestParam String vTelNum,
                                 @RequestParam String vType,
                                 @RequestParam String vBDate,
                                 @RequestParam String stuSchool) {

        visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
        int vId = helperService.getLastInsertedId();
        return visitorService.addStudentVisitor(vId, stuSchool);
    }

    @RequestMapping(value = "/addindi", method = RequestMethod.POST)
    public int addIndividualVisitor(@RequestParam String vFName,
                                    @RequestParam String vMName,
                                    @RequestParam String vLName,
                                    @RequestParam String vStAdd,
                                    @RequestParam String vCity,
                                    @RequestParam String vState,
                                    @RequestParam String vCountry,
                                    @RequestParam String vEmail,
                                    @RequestParam String vTelNum,
                                    @RequestParam String vType,
                                    @RequestParam String vBDate,
                                    @RequestParam int iTimesVisit) {

        visitorService.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
        int vId = helperService.getLastInsertedId();
        return visitorService.addIndividualVisitor(vId, iTimesVisit);
    }

    @RequestMapping(value = "/updatenumberp", method = RequestMethod.PUT)
    public Object payTicket(@RequestParam int vId, @RequestParam int mNumPurchased,
                            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();

        // is login?
        if (!accountController.isLogin(request, response).isSuccess()) {
            json.put("message", "Not logged in!");
            json.put("success", false);
            json.put("data", null);
            return json;
        }

        if (vId < 0 || mNumPurchased < 0) {
            json.put("message", "Error invalid variables");
            return json;
        }

        MemberVisitEntry memberVisitEntry = visitorService.findMemberVisitById(vId);
        if (memberVisitEntry == null) {
            json.put("message", String.format("Error no such member visitor, vId=%s", vId));
            return json;
        }

        visitorService.updateNumPurchased(vId, mNumPurchased);
        json.put("message", "Success!");
        return json;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object getVisitorCount(HttpServletRequest request, HttpServletResponse response) {
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

        json.put("group", visitorService.getGroupCount());
        json.put("member", visitorService.getMemberCount());
        json.put("student", visitorService.getStudentCount());
        json.put("individual", visitorService.getIndividualCount());
        return json;
    }

}
