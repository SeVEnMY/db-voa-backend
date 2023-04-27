package com.nqz.voa.controller;

import com.nqz.voa.enrty.VisitorEntry;
import com.nqz.voa.model.Result;
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

    @Autowired
    private VisitorService visitorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<VisitorEntry>> findAllVisitors(HttpServletRequest request, HttpServletResponse response) {
        Result<List<VisitorEntry>> result = new Result<>();

        /*check admin*/

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
        int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
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
        int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
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
        int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
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
        int vId = visitorService.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
        return visitorService.addIndividualVisitor(vId, iTimesVisit);
    }
}
