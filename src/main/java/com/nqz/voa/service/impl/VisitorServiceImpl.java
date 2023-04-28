package com.nqz.voa.service.impl;

import com.nqz.voa.mapper.VisitorMapper;
import com.nqz.voa.entry.*;
import com.nqz.voa.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    protected VisitorMapper visitorMapper;

    @Override
    public List<VisitorEntry> findAllVisitors() {
        return visitorMapper.findAllVisitors();
    }

    @Override
    public VisitorEntry findVisitorById(int vId) {
        return visitorMapper.findVisitorById(vId);
    }

    @Override
    public int addVisitor(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType, String vBDate) {
        return visitorMapper.addVisitor(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType, vBDate);
    }

    @Override
    public VisitorEntry findVisitorByEmail(String vEmail) {
        return visitorMapper.findVisitorByEmail(vEmail);
    }

    @Override
    public int findVisitorByInfo(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType) {
        return visitorMapper.findVisitorByInfo(vFName, vMName, vLName, vStAdd, vCity, vState, vCountry, vEmail, vTelNum, vType);
    }

    @Override
    public int addGroupVisitor(int vId, int gSize) {
        return visitorMapper.addGroupVisitor(vId, gSize);
    }

    @Override
    public int addMemberVisitor(int vId, String mStartDate, String mEndDate, int mNumPurchased) {
        return visitorMapper.addMemberVisitor(vId, mStartDate, mEndDate, mNumPurchased);
    }

    @Override
    public int addStudentVisitor(int vId, String stuSchool) {
        return visitorMapper.addStudentVisitor(vId, stuSchool);
    }

    @Override
    public int addIndividualVisitor(int vId, int iTimesVisit) {
        return visitorMapper.addIndividualVisitor(vId, iTimesVisit);
    }

    @Override
    public GroupVisitEntry findGroupVisitById(int vId) {
        return visitorMapper.findGroupVisitById(vId);
    }

    @Override
    public MemberVisitEntry findMemberVisitById(int vId) {
        return visitorMapper.findMemberVisitById(vId);
    }

    @Override
    public StudentVisitEntry findStuVisitById(int vId) {
        return visitorMapper.findStuVisitById(vId);
    }

    @Override
    public IndividualVisitEntry findIndiVisitById(int vId) {
        return visitorMapper.findIndiVisitById(vId);
    }
}
