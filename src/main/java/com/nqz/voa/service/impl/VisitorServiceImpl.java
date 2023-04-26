package com.nqz.voa.service.impl;

import com.nqz.voa.dao.VisitorMapper;
import com.nqz.voa.enrty.VisitorEntry;
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
    public Integer findVisitorByEmail(String vEmail) {
        return visitorMapper.findVisitorByEmail(vEmail);
    }
}
