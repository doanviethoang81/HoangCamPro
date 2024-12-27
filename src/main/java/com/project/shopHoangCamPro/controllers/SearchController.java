package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @RequestMapping("")
    private String index(){
        return "search";
    }

    //user tìm kiếm sản phẩm
    @GetMapping("/product")
    public String searchProducts(@RequestParam("keywordUser") String keywordUser, Model
            model) {
        try {
            // Tìm kiếm sản phẩm theo tên
            List<Product> products = productService.searchProductsByName(keywordUser);
            // Thêm danh sách sản phẩm tìm thấy vào model
            model.addAttribute("products", products);
            model.addAttribute("keyword", keywordUser);
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        }
        return "search";
    }


}
