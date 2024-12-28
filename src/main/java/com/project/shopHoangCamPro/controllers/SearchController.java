package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.services.ProductService;
import com.project.shopHoangCamPro.services.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;

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
//            model.addAttribute("products", products);
            if (products == null || products.isEmpty()) {
                // Thêm thông báo nếu không có sản phẩm nào
                model.addAttribute("error", "Không tìm thấy sản phẩm này!");
            } else {
                List<Map<String, Object>> productsWithFirstVariant = new ArrayList<>();
                for (Product product : products) {
                    ProductVariant firstVariant = productVariantService.getFirstVariantByProductId(product.getId());
                    if (firstVariant != null) {
                        Map<String, Object> productData = new HashMap<>();
                        productData.put("id", product.getId());
                        productData.put("name", product.getName());
                        productData.put("imageUrl", product.getImageUrl());
                        productData.put("price", firstVariant.getPrice());
                        productData.put("discount", firstVariant.getDiscount());

                        double price = Double.parseDouble(firstVariant.getPrice());
                        double discount = Double.parseDouble(firstVariant.getDiscount());

                        double discountPercentage = (discount / price) * 100;// tính phần trăm giảm giá
                        productData.put("discountPercentage", discountPercentage);

                        productsWithFirstVariant.add(productData);
                    }
                }
                model.addAttribute("productsWithPrices", productsWithFirstVariant);
                model.addAttribute("keyword", keywordUser);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        }
        return "search";
    }


}
