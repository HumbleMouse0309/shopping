package com.project.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jt;
    public int checkUserIdDup(String userIdLower){
        String sqlStmt = "select count(*) from tb_user_mst where user_id = ?";
        return jt.queryForObject(sqlStmt,Integer.class,userIdLower);
    }
    public void insertUser(String userType,
                            String userId,
                            String userPassword,
                            String userName,
                            String userEmail,
                            String year,
                            String gender){
        String sqlStmt = "insert into tb_user_mst(user_id,user_name,user_email,user_birth,";
               sqlStmt += "user_gender,user_grade,user_password) values(?,?,?,?,?,?,?)";

               jt.update(sqlStmt,userId,userName,userEmail,year,gender,userType,userPassword);
    }
    public void insertSeller(String bizType,
                             String userId,
                             String name,
                             String email,
                             String bankName,
                             String userAccount,
                             String userPhone){
        String sqlStmt = "insert into tb_seller_mst(biz_type,user_id,company_name,user_email,bank_code,user_account,user_phone) ";
               sqlStmt += "values(?,?,?,?,?,?,?)";
        jt.update(sqlStmt,bizType,userId,name,email,bankName,userAccount,userPhone);
    }


}
