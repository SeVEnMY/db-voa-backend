package com.nqz.voa.service;

import com.nqz.voa.enrty.*;

import java.util.List;

public interface VisitorService {

    List<VisitorEntry> findAllVisitors();

    VisitorEntry findVisitorById(int vId);

    int addVisitor(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType, String vBDate);

    VisitorEntry findVisitorByEmail(String vEmail);

    int findVisitorByInfo(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType);

    int addGroupVisitor(int vId, int gSize);

    int addMemberVisitor(int vId, String mStartDate, String mEndDate, int mNumPurchased);

    int addStudentVisitor(int vId, String stuSchool);

    int addIndividualVisitor(int vId, int iTimesVisit);

    GroupVisitEntry findGroupVisitById(int vId);

    MemberVisitEntry findMemberVisitById(int vId);

    StudentVisitEntry findStuVisitById(int vId);

    IndividualVisitEntry findIndiVisitById(int vId);

}
