package com.project.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class SessionDao {
    @Autowired
    JdbcTemplate jt;
    public int userCheck(String userId, String userPassword){
        String sqlStmt  = "select count(*) from tb_user_mst where user_id = ? and user_password = ?";
        return jt.queryForObject(sqlStmt, Integer.class, userId, userPassword);
    }
    public Map<String,Object> getUserInfo(String userId, String userPassword){
        String sqlStmt = "select user_name as userName, user_grade as userGrade, user_email as userEmail ";
               sqlStmt += "from tb_user_mst where user_id = ? and user_password = ?";
        return jt.queryForMap(sqlStmt, userId, userPassword);
    }
}
