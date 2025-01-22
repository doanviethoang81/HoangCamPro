//package com.project.shopHoangCamPro.controllers;
//
//import com.project.shopHoangCamPro.models.Product;
//import com.project.shopHoangCamPro.services.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class FilterController {
//    private final ProductService productService;
//
//    @GetMapping("/filter")
//    public String filterProducts(
//            @RequestParam(required = false) List<String> resolution,
//            @RequestParam(required = false) List<String> price,
//            Model model) {
//
//        // Gọi service để lọc sản phẩm
//        List<Product> filteredProducts = productService.filterProducts(resolution, price);
//
//        // Đưa danh sách sản phẩm vào model
//        model.addAttribute("products", filteredProducts);
//
//        // Trả về đoạn HTML chứa danh sách sản phẩm
//        return "fragments/product-list :: productList";
//    }
//}
