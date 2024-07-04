package com.project.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class ProductDao {
    @Autowired
    JdbcTemplate jt;
    public List<Map<String,Object>> selectProductList(String userId){
        String sqlStmt = "";
               sqlStmt += "select seq, ";
               sqlStmt += "product_id as productId, ";
               sqlStmt += "product_category as productCaterogy, ";
               sqlStmt += "(select code_name from tb_code_detail ";
               sqlStmt += "where seq = product_category) as productCaterogyName, ";
               sqlStmt += "product_name as productName, ";
               sqlStmt += "product_image as productImage, ";
               sqlStmt += "product_keyword as productKeyword, ";
               sqlStmt += "product_state as productState, ";
               sqlStmt += "(select code_name from tb_code_detail ";
               sqlStmt += "where seq = product_state ) as productStateName, ";
               sqlStmt += "reg_dt as regDt ";
               sqlStmt += "from tb_product_mst where seller_id = ?";
        return jt.queryForList(sqlStmt,userId);
    }
    public List<Map<String,Object>> getCategory(){
        String sqlStmt = "select seq as codeSeq, code_name as codeName "; 
               sqlStmt += "from tb_code_detail where code_code = 2";
        return jt.queryForList(sqlStmt);
    }
    public int getProductCnt(String userId){
        String sqlStmt = "select count(*) from tb_product_mst where seller_id = ?";
        return jt.queryForObject(sqlStmt,Integer.class,userId);
    }
    public void insertProduct(String productId,
                              String userId,
                              String productCategory,
                              String productName,
                              String productKeyword,
                              String fileName
                              ){
        String sqlStmt = "insert into tb_product_mst( ";
               sqlStmt += "product_id, seller_id, product_category,  ";
               sqlStmt += "product_name, product_keyword, product_image) ";
               sqlStmt += "values(?,?,?,?,?,?)";

        jt.update(sqlStmt,productId, userId, productCategory, productName, productKeyword,fileName);

    }
    public void insertProductDetail(String productId, String productPrice, 
                                   String productAmount, String productDesc){
        String sqlStmtInsert = "insert into tb_product_detail( ";
               sqlStmtInsert += "product_id, product_price, product_amount, product_descript) ";
               sqlStmtInsert += "values(?,?,?,?)";
        String sqlStmtUpdate = "update tb_product_mst set product_state = 16 where product_id = ?";
        jt.update(sqlStmtInsert,productId, productPrice, productAmount, productDesc);
        jt.update(sqlStmtUpdate,productId);
    }
    public Map<String, Object> selectProduct(String productId){
        String sqlStmt = "select product_id as productId ,";
               sqlStmt += "seller_id as sellerId, ";
               sqlStmt += "product_category as productCategory, ";
               sqlStmt += "product_name as productName, ";
               sqlStmt += "product_image as productImage, ";
               sqlStmt += "product_keyword as productKeyword ";
               sqlStmt += "from tb_product_mst where product_id = ?";
        return jt.queryForMap(sqlStmt, productId);

    }
    public Map<String, Object> selectProductDetail(String productId){
        String sqlStmt = "select product_id as productId, ";
               sqlStmt += "product_price as productPrice, ";
               sqlStmt += "product_amount as productAmount, ";
               sqlStmt += "product_descript as productDesc ";
               sqlStmt += "from tb_product_detail where product_id = ?";
        return jt.queryForMap(sqlStmt, productId);
    }
    public List<Map<String,Object>> selectProductState(String userId){
        String sqlStmt = "";
               sqlStmt += "select a.seq as seq, ";
               sqlStmt += "a.product_id as productId, ";
               sqlStmt += "a.reg_dt as regDt, ";
               sqlStmt += "(select code_name from tb_code_detail ";
               sqlStmt += "where seq = a.product_category) as productCategory, ";
               sqlStmt += "a.product_name as productName, ";
               sqlStmt += "b.product_amount as productAmount, ";
               sqlStmt += "b.product_price as productPrice, ";
               sqlStmt += "(select sum(amount) from tb_order_mst where product_id = a.product_id) ";
               sqlStmt += "as totalSalesAmount, ";
               sqlStmt += "(select sum(amount) from tb_order_mst where product_id = a.product_id)*b.product_price as totalSalesPrice ";
               sqlStmt += "from tb_product_mst a, tb_product_detail b ";
               sqlStmt += "where a.product_id = b.product_id and seller_id = ? ";
        return jt.queryForList(sqlStmt,userId);
    }
    public List<Map<String,Object>> selectDelivery(String userId){
        String sqlStmt = "";
               sqlStmt += "select a.product_id as productId, ";
               sqlStmt += "a.seq as seq, ";
               sqlStmt += "a.amount as amount, ";
               sqlStmt += "a.user_id as userId, ";
               sqlStmt += "a.reg_dt as regDt, ";
               sqlStmt += "a.delivery_state, ";
               sqlStmt += "(select code_name from tb_code_detail where seq = a.delivery_state) as deliveryState, ";
               sqlStmt += "b.product_name as productName, ";
               sqlStmt += "(select code_name from tb_code_detail where seq = b.product_category) as productCategory, ";
               sqlStmt += "c.address1 as address1, ";
               sqlStmt += "c.address2 as address2 ";
               sqlStmt += "from tb_order_mst a, tb_product_mst b, tb_shop_user c  ";
               sqlStmt += "where a.product_id = b.product_id ";
               sqlStmt += "and a.user_id = c.email ";
               sqlStmt += "and b.seller_id = ? ";
               sqlStmt += "and a.order_state = 1 ";
  

        return jt.queryForList(sqlStmt,userId);
    }
}
