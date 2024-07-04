package com.project.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.project.shop.dao.SessionDao;

import jakarta.servlet.http.HttpSession;


@Controller
public class SessionController {
    @Autowired
    SessionDao sessionDao;
    @GetMapping("login")
    public String getLogin(){
        return "login";
    }
    @PostMapping("login")
    public String postLogin(@RequestParam String userId, 
                            @RequestParam String userPassword,
                            HttpSession session){
        int cnt = sessionDao.userCheck(userId, userPassword);
        if(cnt > 0){
            Map<String,Object> user = sessionDao.getUserInfo(userId,userPassword);
            session.setAttribute("userEmail", user.get("userEmail").toString());
            session.setAttribute("userName",user.get("userName").toString());
            session.setAttribute("userGrade", user.get("userGrade").toString());
            session.setAttribute("userId", userId);
            return "login/success";        
        }else{
            return "login/failuer";
        }
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login/logout";
    }
}
