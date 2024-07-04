package com.project.shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.project.shop.dao.UserDao;
import com.project.shop.dao.ManageDao;

@Controller
public class User {
    @Autowired
    UserDao userDao;
    @Autowired
    ManageDao manageDao;
    @GetMapping("user/insert")
    public String userInsert(){
        return "user/insert";
    }
    @GetMapping("user/dupcheck")
    public String userDupCheck(@RequestParam String userId, Model model){
        String userIdLower = userId.toLowerCase();
        int resultSet = userDao.checkUserIdDup(userIdLower);
        if(resultSet >0 ){
            model.addAttribute("fg", "1");
        }else{
            model.addAttribute("fg","0");
        }
        model.addAttribute("userId", userId);
        return "user/dupcheck";
    }
    @GetMapping("user/insert/action")
    public String userInserAction(@RequestParam String userType,
                                    @RequestParam String userId,
                                    @RequestParam String userPassword,
                                    @RequestParam String userName,
                                    @RequestParam String year,
                                    @RequestParam String gender,
                                    Model model
                                    ){
        if(userType.equals("4")){
            userDao.insertUser(userType,userId,userPassword,userName,year,gender);
            List<Map<String,Object>> bankList = manageDao.selectBankCode();
            model.addAttribute("userId", userId);
            model.addAttribute("userName", userName);
            model.addAttribute("bankList", bankList);
            return "user/seller_insert";

        }else{
            userDao.insertUser(userType,userId,userPassword,userName,year,gender);
            return "redirect:/manage/code";
        }
    }
    @GetMapping("user/seller/insert/action")
    public String userSellerInsertAction(@RequestParam String bizType,
                                         @RequestParam String userId,
                                         @RequestParam String userName,
                                         @RequestParam String companyName,
                                         @RequestParam String email,
                                         @RequestParam String bankName,
                                         @RequestParam String userAccount,
                                         @RequestParam String userPhone){
                String name;
                if(bizType.equals("0")){
                    name = userName;
                }else{
                    name = companyName;
                }
                userDao.insertSeller(bizType,userId,name,email,bankName,userAccount,userPhone);
            return "redirect:/manage/code";
        }
        
}
