package com.project.shopHoangCamPro.controllers;

//import com.project.shopHoangCamPro.dtos.ProductDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.models.Product;
//import com.project.shopHoangCamPro.responses.ProductResponse;
//import com.project.shopHoangCamPro.services.IProductService;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.repository.ProductVariantRepository;
import com.project.shopHoangCamPro.services.CategoryService;
import com.project.shopHoangCamPro.services.ProductService;
import com.project.shopHoangCamPro.services.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

//@Controller("productControllerUser")
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private  final CategoryService categoryService;
    private  final ProductVariantService productVariantService;
    private  final ProductVariantRepository productVariantRepository;

    @GetMapping("/product_security/{categoryName}")
    public String getProductSecurity(@PathVariable("categoryName") String categoryName, Model model) {
        try {
            // Lấy category ID từ tên
            Category category = categoryService.getCategoryByName(categoryName);
            if (category == null) {
                throw new RuntimeException("Category không tồn tại");
            }
            // Lấy danh sách sản phẩm dựa trên category ID
            List<Product> products = productService.getProductsByCategoryId(category.getId());
            if (products == null || products.isEmpty()) {
                // Thêm thông báo nếu không có sản phẩm nào
                model.addAttribute("message", "Chưa có sản phẩm của loại hàng này!");
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
                        double discount =Double.parseDouble(firstVariant.getDiscount());

                        double discountPercentage = (discount / price) * 100;// tính phần trăm giảm giá
                        productData.put("discountPercentage", discountPercentage);

                        productsWithFirstVariant.add(productData);
                    }
                }
                model.addAttribute("productsWithPrices", productsWithFirstVariant);
            }
            model.addAttribute("category", category);
            return "category_security"; // Tên file HTML để hiển thị danh sách sản phẩm
        } catch (Exception e) {
            model.addAttribute("error", "Sản phẩm không có thông tin");
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @GetMapping("/product_smart_home/{categoryName}")
    public String getProductSmartHome(@PathVariable("categoryName") String categoryName, Model model) {
        try {
            // Lấy category ID từ tên
            Category category = categoryService.getCategoryByName(categoryName);
            if (category == null) {
                throw new RuntimeException("Category không tồn tại");
            }
            // Lấy danh sách sản phẩm dựa trên category ID
            List<Product> products = productService.getProductsByCategoryId(category.getId());
            if (products == null || products.isEmpty()) {
                // Thêm thông báo nếu không có sản phẩm nào
                model.addAttribute("message", "Chưa có sản phẩm của loại hàng này!");
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
                        double discount =Double.parseDouble(firstVariant.getDiscount());

                        double discountPercentage = (discount / price) * 100;// tính phần trăm giảm giá
                        productData.put("discountPercentage", discountPercentage);

                        productsWithFirstVariant.add(productData);
                    }
                }
                model.addAttribute("productsWithPrices", productsWithFirstVariant);
            }
            model.addAttribute("category", category);
            return "category_smart_home"; // Tên file HTML để hiển thị danh sách sản phẩm
        } catch (Exception e) {
            model.addAttribute("error", "Sản phẩm không có thông tin");
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @GetMapping("/product_detail/{name}")
    public String getProductsDetailByProductName(@PathVariable("name") String name, Model model) {
        try {
            Product product = productService.getProductByName(name);
            if (product == null) {
                throw new RuntimeException("Sản phẩm không tồn tại");
            }

            List<ProductVariant> variants = productVariantService.getProductVariantsByProductId(product.getId());
            double price = Double.parseDouble(variants.getFirst().getPrice());
            double discount =Double.parseDouble(variants.getFirst().getDiscount());

            double economize = price - discount;
            double discountPercentage = (discount / price) * 100;// tính phần trăm giảm giá
            model.addAttribute("discountPercentage", discountPercentage);
            model.addAttribute("economize", economize);

            model.addAttribute("list", product);
            model.addAttribute("variants", variants);

            return "product_detail"; // Tên file HTML để hiển thị danh sách sản phẩm
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @GetMapping("/get-price/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPrice(@PathVariable int id) {
        Optional<ProductVariant> variant = productVariantService.findById(id);
        if (variant.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("sku", variant.get().getSku());
            response.put("discount", variant.get().getDiscount());
            response.put("price", variant.get().getPrice());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
