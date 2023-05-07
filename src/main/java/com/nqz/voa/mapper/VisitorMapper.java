package com.nqz.voa.mapper;

import com.nqz.voa.entry.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitorMapper {

    @Select("SELECT * FROM nqz_visitor")
    List<VisitorEntry> findAllVisitors();

    @Select("SELECT * FROM nqz_visitor WHERE v_id = #{vId}")
    VisitorEntry findVisitorById(int vId);

    @Insert("INSERT INTO nqz_visitor (v_fname, v_mname, v_lname, v_stadd, v_city, v_state, v_country, v_email, v_telnum, v_type, v_bdate) " +
            "VALUES (#{vFName}, #{vMName}, #{vLName}, #{vStAdd}, #{vCity}, #{vState}, #{vCountry}, #{vEmail}, #{vTelNum}, #{vType}, str_to_date( #{vBDate} , '%Y-%m-%d' ))")
    int addVisitor(String vFName, String vMName, String vLName, String vStAdd, String vCity, String vState, String vCountry, String vEmail, String vTelNum, String vType, String vBDate);

    @Select("SELECT * FROM nqz_visitor WHERE v_email=#{vEmail}")
    VisitorEntry findVisitorByEmail(String vEmail);

    @Insert("INSERT INTO nqz_group (v_id, g_size) VALUES (#{vId}, #{gSize});")
    int addGroupVisitor(int vId, int gSize);

    @Insert("INSERT INTO nqz_member (v_id, m_startdate, m_enddate, m_num_purchased)" +
            "VALUES (#{vId}, str_to_date( #{mStartDate} , '%Y-%m-%d' ), str_to_date( #{mEndDate} , '%Y-%m-%d' ), #{mNumPurchased})")
    int addMemberVisitor(int vId, String mStartDate, String mEndDate, int mNumPurchased);

    @Insert("INSERT INTO nqz_student (v_id, stu_school) VALUES (#{vId}, #{stuSchool})")
    int addStudentVisitor(int vId, String stuSchool);

    @Insert("INSERT INTO nqz_individual (v_id, i_times_visit) VALUES (#{vId}, #{iTimesVisit})")
    int addIndividualVisitor(int vId, int iTimesVisit);

    @Select("SELECT * FROM nqz_group WHERE v_id = #{vId}")
    GroupVisitEntry findGroupVisitById(int vId);

    @Select("SELECT * FROM nqz_member WHERE v_id = #{vId}")
    MemberVisitEntry findMemberVisitById(int vId);

    @Select("SELECT * FROM nqz_student WHERE v_id = #{vId}")
    StudentVisitEntry findStuVisitById(int vId);

    @Select("SELECT * FROM nqz_individual WHERE v_id = #{vId}")
    IndividualVisitEntry findIndiVisitById(int vId);

    @Select("UPDATE nqz_member SET m_num_purchased = #{mNumPurchased} WHERE v_id = #{vId}")
    void updateNumPurchased(int vId, int mNumPurchased);

    @Select("SELECT COUNT(*) FROM nqz_group")
    int getGroupCount();

    @Select("SELECT COUNT(*) FROM nqz_member")
    int getMemberCount();

    @Select("SELECT COUNT(*) FROM nqz_student")
    int getStudentCount();

    @Select("SELECT COUNT(*) FROM nqz_individual")
    int getIndividualCount();

}
