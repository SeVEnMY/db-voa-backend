package com.nqz.voa.service;

import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.LoginRequestEntry;
import com.nqz.voa.model.Result;

public interface AccountService {
  int Register(String accEmail, String accPwd, int vId);

  AccountEntry findAccountByAccEmail(String accEmail);

  Result<AccountEntry> login(LoginRequestEntry loginRequest);

  String findVisitTypeByAccEmail(String accEmail);
  String getRoleByAccEmail(String accEmail);

  Boolean isAdmin(String accEmail);

  void addAccRole(int accId);
}
