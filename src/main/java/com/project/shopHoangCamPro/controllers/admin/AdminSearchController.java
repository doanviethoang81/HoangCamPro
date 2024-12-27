package com.project.shopHoangCamPro.controllers.admin;

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
@RequestMapping("admin/search")
@RequiredArgsConstructor
public class AdminSearchController {

    private final ProductService productService;

    @GetMapping
    public String searchProductsAdmin(@RequestParam("keyword") String keyword, Model
            model) {
        try {
            List<Product> products = productService.searchProductsByName(keyword);

            // Thêm danh sách sản phẩm tìm thấy vào model
            model.addAttribute("products", products);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        }

        return "admin/search";
    }
}
