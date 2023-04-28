package com.nqz.voa.service.impl;

import com.nqz.voa.mapper.AccountMapper;
import com.nqz.voa.entry.AccountEntry;
import com.nqz.voa.entry.LoginRequestEntry;
import com.nqz.voa.model.Result;
import com.nqz.voa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  protected AccountMapper accountMapper;

  @Override
  public int Register(String accEmail, String accPwd, int vId) {
    return accountMapper.Register(accEmail, accPwd, vId);
  }

  @Override
  public AccountEntry findAccountByAccEmail(String accEmail) {
    return accountMapper.findAccountByAccEmail(accEmail);
  }

  @Override
  public Result<AccountEntry> login(LoginRequestEntry loginRequest) {
    Result<AccountEntry> result = new Result<>();
    AccountEntry getAccount = null;

    try {
      getAccount = accountMapper.findAccountByAccEmail(loginRequest.getAccEmail());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getAccount == null) {
      result.setResultFailed("Account does not exist!");
      return result;
    }
    if (!getAccount.getAcc_pwd().equals(DigestUtils.md5Hex(loginRequest.getAccPwd()))) {
      result.setResultFailed("Account email or password does not match!");
      return result;
    }
    result.setResultSuccess("Login successfully!", getAccount);
    return result;
  }

  @Override
  public String findVisitTypeByAccEmail(String accEmail) {
    return accountMapper.findVisitTypeByAccEmail(accEmail);
  }

  @Override
  public String getRoleByAccEmail(String accEmail) {
    return accountMapper.getRoleByEmail(accEmail);
  }

  @Override
  public Boolean isAdmin(String accEmail) {
    return getRoleByAccEmail(accEmail).equals("admin");
  }

  @Override
  public void addAccRole(int accId) {
    accountMapper.addAccRole(accId);
  }
}
