package com.project.shop.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
    @Autowired
    JdbcTemplate jt;
    public void insertOrder(String productId, String amount, String userId, String orderState){
        String sqlStmt = "insert into tb_order_mst (product_id, user_id, amount,order_state) values(?,?,?,?)";
        jt.update(sqlStmt,productId,userId, amount, orderState);
    }
    public List<Map<String,Object>> selectOrderBasketList(String id){
        String sqlStmt = "";
               sqlStmt += "select a.product_id as productId, a.seq as seq, ";
               sqlStmt += "b.product_name as productName, ";
               sqlStmt +=  "a.amount as amount, ";
               sqlStmt +=  "c.product_price as productPrice, ";
               sqlStmt +=  "a.reg_dt as regDT, ";
               sqlStmt += "a.amount*c.product_price as totalPrice ";
               sqlStmt += "from tb_order_mst a, tb_product_mst b, tb_product_detail c ";
               sqlStmt += "where a.product_id = b.product_id and b.product_id = c.product_id ";
               sqlStmt += "and a.user_id = ? and a.order_state = 0";
        return jt.queryForList(sqlStmt,id);
    }
    public List<Map<String,Object>> selectOrderBuyList(String id){
        String sqlStmt = "";
        sqlStmt += "select a.product_id as productId, a.seq as seq, ";
        sqlStmt += "b.product_name as productName, ";
        sqlStmt +=  "a.amount as amount, ";
        sqlStmt +=  "c.product_price as productPrice, ";
        sqlStmt +=  "a.reg_dt as regDT, ";
        sqlStmt += "a.amount*c.product_price as totalPrice ";
        sqlStmt += "from tb_order_mst a, tb_product_mst b, tb_product_detail c ";
        sqlStmt += "where a.product_id = b.product_id and b.product_id = c.product_id ";
        sqlStmt += "and a.user_id = ? and a.order_state = 1";
        
        return jt.queryForList(sqlStmt,id);
    }
    public void updateBuy(String productId, String amount, String seq){
        String sqlStmt1 = "update tb_order_mst set order_state = 1 where seq = ?";
        String sqlStmt2 = "update tb_product_detail set ";
               sqlStmt2 += "product_amount = product_amount - ? where product_id = ?";
        jt.update(sqlStmt1, seq);
        jt.update(sqlStmt2, amount, productId);

    }
    public void deleteBasket(String seq){
        String sqlStmt = "delete from tb_order_mst where seq = ?";
        jt.update(sqlStmt,seq);
    }
    public void updateOrderChange(String seq){
        String sqlStmt = "update tb_order_mst set delivery_state = 18 where seq = ?";
        jt.update(sqlStmt,seq);
    }
    public Map<String,Object> checkOrderState(String seq){
        String sqlStmt = "";
        return jt.queryForMap(sqlStmt, seq);
    }
}
