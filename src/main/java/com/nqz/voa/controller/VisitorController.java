package com.nqz.voa.controller;

import com.nqz.voa.enrty.VisitorEntry;
import com.nqz.voa.model.Result;
import com.nqz.voa.service.VisitorService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
