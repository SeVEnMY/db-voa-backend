package com.nqz.voa.service;

import com.nqz.voa.enrty.VisitorEntry;

import java.util.List;

public interface VisitorService {
    List<VisitorEntry> findAllVisitors();
    VisitorEntry findVisitorById(int vId);
    int addVisitor(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType, String vBDate);
    Integer findVisitorByEmail(String vEmail);
}
