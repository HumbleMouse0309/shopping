package com.project.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ShopDao {
    @Autowired
    JdbcTemplate jt;
    public List<Map<String,Object>> selectShopList(){
        String sqlStmt = "";
               sqlStmt += "select product_id as productId, ";
               sqlStmt += "seller_id as sellerId, ";
               sqlStmt += "(select code_name from tb_code_detail ";
               sqlStmt += "where seq = product_category) as productCategoryName, ";
               sqlStmt += "product_name as productName, ";
               sqlStmt += "(select product_price from tb_product_detail  ";
               sqlStmt += "where product_id = a.product_id) as productPrice, ";
               sqlStmt += "product_image as productImage, ";
               sqlStmt += "reg_dt as regDt ";
               sqlStmt += "from tb_product_mst a ";
               sqlStmt += "where product_state = 16";
        return jt.queryForList(sqlStmt);
    }
    public Map<String, Object> selectShopDetail(String productId){
        String sqlStmt = "";
               sqlStmt += "select a.product_name as productName, ";
               sqlStmt += "a.product_id as productId, ";
               sqlStmt += "a.product_image as productImage, ";
               sqlStmt += "(select code_name from tb_code_detail ";
               sqlStmt += "where seq= a.product_category) as productCategoryName, ";
               sqlStmt += "b.product_descript as productDesc, ";
               sqlStmt += "b.product_amount as productAmount, ";
               sqlStmt += "b.product_price as productPrice ";
               sqlStmt += "from tb_product_mst a, tb_product_detail b ";
               sqlStmt += "where a.product_id = b.product_id ";
               sqlStmt += "and a.product_id = ? ";
        return jt.queryForMap(sqlStmt,productId);
    }
    public int selectShopUserEmail(String userEmail){
        String sqlStmt = "select count(*) from tb_shop_user where email = ?";
        return jt.queryForObject(sqlStmt,Integer.class,userEmail);
    }
    public int selectUserEmail(String userEmail){
        String sqlStmt = "select count(*) from tb_user_mst where user_email = ?";
        return jt.queryForObject(sqlStmt,Integer.class,userEmail);
    }


    public void insertShopUser(String userEmail, String userPassword, String address1, String address2){
        String sqlStmt = "insert into tb_shop_user(email,user_password ,address1,address2) values(?,?,?,?) ";
        jt.update(sqlStmt, userEmail,userPassword,address1,address2);
    }
    public int checkEmail(String userEmail, String userPassword){
        String sqlStmt = "select count(*) from tb_shop_user where email = ? and user_password = ?";
        return jt.queryForObject(sqlStmt,Integer.class,userEmail,userPassword);
    }
}
