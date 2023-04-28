package com.nqz.voa.service;

import com.nqz.voa.entry.TicketEntry;

import java.util.List;

public interface TicketService {
  List<TicketEntry> findAllTickets();
}
