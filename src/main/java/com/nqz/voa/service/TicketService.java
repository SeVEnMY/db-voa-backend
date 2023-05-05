package com.nqz.voa.service;

import com.nqz.voa.entry.TicketEntry;
import com.nqz.voa.entry.TicketTypeEntry;

import java.util.List;

public interface TicketService {

  List<TicketEntry> findAllTickets();

  TicketEntry findTicketById(int tktId);

  int addTicket(String tktOnline, String tktVisitDate, int tktPrice, int tktDiscount, String tktIspaid, int tktTypeId);

  List<TicketTypeEntry> findAllTicketTypes();

  int addTicketType(String tktTypeName);

  int addAttractionToTicket(int tktId, int attId, String tktAttTime);

  void payTicket(int tktId);
}
