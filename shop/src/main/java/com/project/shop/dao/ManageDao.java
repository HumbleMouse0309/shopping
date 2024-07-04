package com.project.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public class ManageDao {
    @Autowired
    JdbcTemplate jt;
    public List<Map<String,Object>> selectCode(){
        String sqlStmt = "select seq, "; 
               sqlStmt += "code_name as codeName ";
               sqlStmt += "from tb_code_mst";
        return jt.queryForList(sqlStmt);
    }
    public void insertCode(String codeName){
        String sqlStmt = "insert into tb_code_mst(code_name) values(?)";
        jt.update(sqlStmt,codeName);
    }
    public List<Map<String,Object>> selectCodeDetail(){
        String sqlStmt = "";
               sqlStmt += "select a.seq as seq, ";
               sqlStmt += "a.code_code as codeCode, ";
               sqlStmt += "b.code_name as category, ";
               sqlStmt += "a.code_name as codeName, ";
               sqlStmt += "a.code_desc as codeDesc, ";
               sqlStmt += "a.state as state ";
               sqlStmt += "from tb_code_detail a, tb_code_mst b ";
               sqlStmt += "where a.code_code = b.seq order by codeCode";

        return jt.queryForList(sqlStmt);
    }
    public void insertCodeDetail(String category,String state, String codeName, String codeDesc){
        String sqlStmt = "";
               sqlStmt += "insert into tb_code_detail (code_code, code_name, code_desc, state) ";
               sqlStmt += "values(?,?,?,?)";    
        jt.update(sqlStmt,category,codeName, codeDesc,state);
    }
    public void updateCodeDetailState(String seq, String state){
        String sqlStmt = "update tb_code_detail set state = ? where seq = ? ";
        jt.update(sqlStmt,state,seq);
    }
    public List<Map<String,Object>> selectBankCode(){
        String sqlStmt = "select seq, code_name as codeName from tb_code_detail where code_code = 8 ";
        return jt.queryForList(sqlStmt);
    }
    public List<Map<String, Object>> selectUserList(){
        String sqlStmt = "";
              sqlStmt += "select a.user_id as userId, ";
              sqlStmt += "a.user_name as userName, ";
              sqlStmt += "a.user_password as userPassword, ";
              sqlStmt += "a.user_grade as userGrade, ";
              sqlStmt += "b.code_name as codeName, ";
              sqlStmt += "a.reg_dt as regDt ";
              sqlStmt += "from tb_user_mst a, (select seq, code_name ";
              sqlStmt += "                   from tb_code_detail ";
              sqlStmt += "                  where code_code = (select seq ";
              sqlStmt += "                  from tb_code_mst where code_name='회원관리')) b ";
              sqlStmt += "where a.user_grade = b.seq ";
        return jt.queryForList(sqlStmt);    
    }
    public List<Map<String,Object>> selectProductList(String orderString){
        String sqlStmt = "";
               sqlStmt += "select product_id as productId, ";
               sqlStmt += "seller_id as sellerId, ";
               sqlStmt += "product_category as productCategory, ";
               sqlStmt += "product_name as productName, ";
               sqlStmt += "(select code_name ";
               sqlStmt += "from tb_code_detail where seq = product_category) as category, ";
               sqlStmt += "product_state as productState, ";
               sqlStmt += "(select code_name  ";
               sqlStmt += "from tb_code_detail where seq = product_state) as state, ";
               sqlStmt += "reg_dt as regDt ";
               sqlStmt += "from tb_product_mst ";
               sqlStmt += "order by "+orderString;
        return jt.queryForList(sqlStmt);
    }
}
