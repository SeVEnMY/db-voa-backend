package com.nqz.voa.mapper;

import com.nqz.voa.entry.AccountEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
  @Insert("INSERT INTO nqz_account (acc_email, acc_pwd, v_id) " +
          "VALUES (#{accEmail}, #{accPwd}, #{vId})")
  int Register(String accEmail, String accPwd, int vId);

  @Select("SELECT * FROM nqz_account WHERE acc_email = #{accEmail}")
  AccountEntry findAccountByAccEmail(String accEmail);

  @Select("SELECT v_type FROM nqz_account NATURAL JOIN nqz_visitor WHERE acc_email = #{accEmail}")
  String findVisitTypeByAccEmail(String accEmail);

  @Select("SELECT r_name FROM nqz_account NATURAL JOIN nqz_acc_role NATURAL JOIN nqz_role WHERE acc_email = #{accEmail}")
  String getRoleByEmail(String accEmail);

  @Insert("INSERT INTO nqz_acc_role (acc_id, r_id) VALUES (#{accId}, 1)")
  void addAccRole(int accId);
}
