package com.project.shopHoangCamPro.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.services.OrderDetailService;
import com.project.shopHoangCamPro.services.OrderService;
import com.project.shopHoangCamPro.services.ProductService;
import com.project.shopHoangCamPro.services.ProductVariantService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @RequestMapping("/get-payment/{name}")
    public String index(
            @PathVariable("name") String name,
            @RequestParam("variantId") int variantId,
            @RequestParam("quantity") int quantity,
            Model model)
    {
        try {
            model.addAttribute("Order", new Order());
            Product product = productService.getProductByName(name);
            ProductVariant productVariant= productVariantService.getProductVariantById(variantId);

            BigDecimal price = new BigDecimal(productVariant.getDiscount()); // price là chuỗi
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity)); // Tính tổng giá

//            model.addAttribute("variantId", variantId);
            model.addAttribute("quantity", quantity);
            model.addAttribute("productVariant", productVariant);
            model.addAttribute("product", product);
            model.addAttribute("totalPrice", totalPrice);
            return "payment"; // Tên file HTML để hiển thị danh sách sản phẩm
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "payment"; // Tên file HTML để hiển thị lỗi
        }
    }

    @PostMapping("/buy")
    public String userBuy(
            @Valid
            @ModelAttribute("Order") Order order,  // Sử dụng @ModelAttribute để giữ lại thông tin Order
            BindingResult result,
            @ModelAttribute("OrderDetail") OrderDetail orderDetail,
            @RequestParam("totalMoney") Float totalMoney,
            @RequestParam("productName") String name,
            @RequestParam("variantId") Integer variantId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "deliveryFee", required = false) String deliveryFee,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
            Model model) throws Exception {
        if (result.hasErrors()) {
            //nếu có lỗi, lấy lại thông tin sản phẩm từ DB
            Product product = productService.getProductByName(name);
            ProductVariant productVariant = productVariantService.getProductVariantById(variantId);
            //truyền lại thông tin vào model để hiển thị lại trên giao diện
            model.addAttribute("product", product);
            model.addAttribute("productVariant", productVariant);
            model.addAttribute("quantity", quantity);
            model.addAttribute("totalPrice", new BigDecimal(productVariant.getDiscount()).multiply(new BigDecimal(quantity)));
            return "payment";
        }
        // Kiểm tra checkbox
        if (deliveryFee == null || paymentMethod == null) {
            model.addAttribute("error", "Vui lòng chọn phương thức vận chuyển và thanh toán.");
            Product product = productService.getProductByName(name);
            ProductVariant productVariant = productVariantService.getProductVariantById(variantId);
            model.addAttribute("product", product);
            model.addAttribute("productVariant", productVariant);
            model.addAttribute("quantity", quantity);
            model.addAttribute("totalPrice", new BigDecimal(productVariant.getDiscount()).multiply(new BigDecimal(quantity)));
            return "payment";
        }
        try {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            order.setDate(new Date());
            if(order.getNote() == null){
                order.setNote("");
            }
            if(order.getStatus() == null){
                order.setStatus("Chờ duyệt đơn");
            }
            order.setUser(userDetails.getUser());
            order.setTotalMoney(totalMoney);
            order.setActive(true);
            orderService.save(order);

            Product product = productService.getProductByName(name);
            ProductVariant productVariant = productVariantService.getProductVariantById(variantId);

            orderDetail.setOrder(order);
            orderDetail.setProduct(productVariant);
            orderDetail.setNumberOfProducts(quantity);
            Float priceValue = Float.parseFloat(productVariant.getDiscount()); // Chuyển đổi từ String sang Float
            orderDetail.setPrice(priceValue);
            Float totalPrice = priceValue * quantity; // Nhân giá với số lượng
            orderDetail.setTotalPrice(totalPrice);
            orderDetailService.save(orderDetail);
            return "success";
        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Có lỗi xảy ra khi mua hàng");
//            return "error";

            e.printStackTrace();
            throw new RuntimeException("Error saving OrderDetail: " + e.getMessage());
//             "error";
        }
    }

}
