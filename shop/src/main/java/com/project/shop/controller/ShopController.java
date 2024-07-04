package com.project.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import com.project.shop.dao.ShopDao;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShopController {
    @Autowired
    ShopDao shopDao;
    @GetMapping("shop")
    public String shopList(Model model){
        List<Map<String,Object>> productList = shopDao.selectShopList();
        model.addAttribute("productList", productList);
        return "shop/index";
    }
    @GetMapping("shop/detail")
    public String shopDetail(@RequestParam String productId, Model model){
        Map<String, Object> productDetail = shopDao.selectShopDetail(productId);
        model.addAttribute("productDetail",productDetail);
        return "shop/detail";
    }
    @GetMapping("shop/login")
    public String shopLogin(){
        return "shop/login";
    }
    @GetMapping("shop/login/check")
    public String shopLoginCheck(){
        return "shop/login_check";
    }
    @PostMapping("shop/login/check")
    public String shopLoginCheckPost(@RequestParam String userEmail, 
                                     @RequestParam String userPassword,
                                     HttpSession session){
        int cnt = shopDao.checkEmail(userEmail, userPassword);
        if(cnt > 0){
            session.setAttribute("userEmail", userEmail);
            return "redirect:/user/mypage";
        }else {
            return "login/failuer";
        }
    }

    @PostMapping("shop/user/insert")
    public String shopUserInsert(@RequestParam String userEmail,
                                 @RequestParam String userPassword,
                                 @RequestParam String address1,
                                 @RequestParam String address2,
                                 HttpSession session){
        
        int cnt1 = shopDao.selectUserEmail(userEmail);
        int cnt2 = shopDao.selectShopUserEmail(userEmail);
        
        if(cnt1 > 0 && cnt2 == 0){
            System.out.println("1: ===============");
            session.setAttribute("userEmail",userEmail);    
            shopDao.insertShopUser(userEmail,userPassword,address1,address2);
            return "redirect:/user/mypage";
        }else if(cnt2 > 0 ){
            System.out.println("2: ===============");
            return "redirect:/shop";
        }
        System.out.println("3: ===============");
        session.setAttribute("userEmail",userEmail);
        shopDao.insertShopUser(userEmail,userPassword,address1,address2);
        return "redirect:/order/basket";
    }
}
