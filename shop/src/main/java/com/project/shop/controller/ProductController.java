package com.project.shop.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

import java.util.*;
import com.project.shop.dao.ProductDao;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProductController {
    @Autowired
    ProductDao productDao;
    @GetMapping("/product")
    public String productList(Model model, HttpSession session){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        if(!session.getAttribute("userGrade").equals("1")){
            return "redirect:/login/failure";
        }
        String userId = session.getAttribute("userId").toString();
        List<Map<String, Object>> productSet = productDao.selectProductList(userId);
        model.addAttribute("productSet", productSet);
        model.addAttribute("userId", session.getAttribute("userId"));

        return "product/index";
    }
    @GetMapping("product/insert")
    public String productInsert(HttpSession session, Model model) {
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        if(!session.getAttribute("userGrade").equals("1")){
            return "redirect:/login/failure";
        }
        List<Map<String, Object>> categorySet = productDao.getCategory();
        // String userId = session.getAttribute("userId");
        model.addAttribute("categorySet", categorySet);
        return "product/product_insert";
    }
    @PostMapping("product/insert/action")
    public String productInsertAction(@RequestParam String productCategory,
                                      @RequestParam String productName,
                                      @RequestParam String productKeyword,
                                      @RequestParam MultipartFile productImage,
                                      HttpSession session) throws IOException{
        String userId = session.getAttribute("userId").toString();
        String productId;
        int productCnt = productDao.getProductCnt(userId) + 1;
        productId = userId+"_"+productCategory+productCnt;
        UUID uuid = UUID.randomUUID();
        String uploadPath = "C:/project/shop/shop/src/main/resources/static/img/product/";
        String fileName = uploadPath+uuid.toString()+productImage.getOriginalFilename();
        String filePath = "/img/product/"+uuid.toString()+productImage.getOriginalFilename();
        File savefileName = new File(fileName);
        productImage.transferTo(savefileName);
        productDao.insertProduct(productId,userId,productCategory,productName,productKeyword,filePath);
        return "redirect:/product";
    }
    @GetMapping("product/insert/detail")
    public String productInsertDetail(@RequestParam String productId, Model model){
        model.addAttribute("productId", productId);
        return "product/insert_detail";
    }
    @GetMapping("product/insert/detail/action")
    public String productInsertDetailAction(@RequestParam String productId,
                                            @RequestParam String productPrice,
                                            @RequestParam String productAmount,
                                            @RequestParam String productDesc,
                                            Model model){
        productDao.insertProductDetail(productId, productPrice, productAmount, productDesc);
        Map<String, Object> productSet = productDao.selectProduct(productId);
        Map<String, Object> productDetailSet = productDao.selectProductDetail(productId);
        model.addAttribute("productSet", productSet);
        model.addAttribute("productDetailSet", productDetailSet);

        return "product/detail";
    }                                  
}
