package com.nqz.voa.dao;

import com.nqz.voa.enrty.VisitorEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.compiler.ast.Visitor;

import java.util.List;

@Mapper
public interface VisitorMapper {

    @Select("SELECT * FROM nqz_visitor")
    List<VisitorEntry> findAllVisitors();

    @Select("SELECT * FROM nqz_visitor WHERE v_id = #{vId}")
    VisitorEntry findVisitorById(int vId);

    @Insert("INSERT INTO nqz_visitor (v_fname, v_mname, v_lname, v_stadd, v_city, v_state, v_country, v_email, v_telnum, v_type, v_bdate) " +
            "VALUES (#{vFName}, #{vMName}, #{vLName}, #{vStAdd}, #{vCity}, #{vState}, #{vCountry}, #{vEmail}, #{vTelNum}, #{vType}, #{vBDate})")
    int addVisitor(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType, String vBDate);

    @Select("SELECT v_id FROM nqz_visitor WHERE v_email=#{vEmail}")
    Integer findVisitorByEmail(String vEmail);


}
