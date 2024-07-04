package com.project.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import com.project.shop.dao.ProductDao;

@Controller
public class SellerController {
    @Autowired
    ProductDao productDao;
    @GetMapping("user/sellerpage")
    public String sellerMypage(HttpSession session, Model model){
        if(session.getAttribute("userId").equals(null)){
            return "redirect:/login";
        }
        String userId = session.getAttribute("userId").toString();
        List<Map<String,Object>> productStateList = productDao.selectProductState(userId);
        List<Map<String,Object>> productDeliveryList = productDao.selectDelivery(userId);
        model.addAttribute("productStateList", productStateList);
        model.addAttribute("productDeliveryList", productDeliveryList);
        return "user/seller_page";
    }
}
