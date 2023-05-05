package com.nqz.voa.service.impl;

import com.nqz.voa.entry.TicketEntry;
import com.nqz.voa.entry.TicketTypeEntry;
import com.nqz.voa.mapper.TicketMapper;
import com.nqz.voa.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  private TicketMapper ticketMapper;

  @Override
  public List<TicketEntry> findAllTickets() {
    return ticketMapper.findAllTickets();
  }

  @Override
  public TicketEntry findTicketById(int tktId) {
    return ticketMapper.findTicketById(tktId);
  }

  @Override
  public int addTicket(String tktOnline, String tktVisitDate, int tktPrice, int tktDiscount, String tktIspaid, int tktTypeId) {
    return ticketMapper.addTicket(tktOnline, tktVisitDate, tktPrice, tktDiscount, tktIspaid, tktTypeId);
  }

  @Override
  public int getLastInsertedTicketId() {
    return ticketMapper.getLastInsertedTicketId();
  }

  @Override
  public List<TicketTypeEntry> findAllTicketTypes() {
    return ticketMapper.findAllTicketTypes();
  }

  @Override
  public int addTicketType(String tktTypeName) {
    return ticketMapper.addTicketType(tktTypeName);
  }

  @Override
  public int addAttractionToTicket(int tktId, int attId, String tktAttTime) {
    return ticketMapper.addAttractionToTicket(tktId, attId, tktAttTime);
  }
}
