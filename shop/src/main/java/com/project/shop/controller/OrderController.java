package com.project.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import com.project.shop.dao.OrderDao;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    OrderDao orderDao;
    @GetMapping("order/basket")
    public String orderBasket(@RequestParam String productId,
                              @RequestParam String productPrice,
                              @RequestParam String amount,
                              @RequestParam String totalPrice,
                              HttpSession session,
                              Model model){
    
        model.addAttribute("productId", productId);
        model.addAttribute("productPrice", productPrice);
        model.addAttribute("amount", amount);
        model.addAttribute("totalPrice", totalPrice);
        return "order/basket";
    }
    @GetMapping("order/basket/insert")
    public String orderBasketInsert(@RequestParam String productId, 
                                    @RequestParam String amount,
                                    @RequestParam String userId){
            String orderState = "0";
            orderDao.insertOrder(productId,amount,userId,orderState);
        return "redirect:/user/mypage";
    }
    @GetMapping("order/buy/insert")
    public String orderBuyInsert(@RequestParam String productId, 
                                    @RequestParam String amount,
                                    @RequestParam String userId){
            String orderState = "1";
            orderDao.insertOrder(productId,amount,userId,orderState);
        return "redirect:/user/mypage";
    }
    @GetMapping("order/buy/update")
    public String orderBuyUpdate(@RequestParam String productId,
                                 @RequestParam String amount,
                                 @RequestParam String seq){
        orderDao.updateBuy(productId,amount,seq);
        return "redirect:/user/mypage";
    }
    @GetMapping("order/basket/delete")
    public String orderBasketDelete(@RequestParam String seq){
        orderDao.deleteBasket(seq);
        return "redirect:/user/mypage";
    }
    @GetMapping("order/delivery")
    public String orderDelivery(@RequestParam String seq){
        orderDao.updateOrderChange(seq);
        return "redirect:/user/sellerpage";
    }
    @GetMapping("order/search")
    @ResponseBody
    public String orderSearch(@RequestParam String seq){
        Map<String,Object>
    }
}

