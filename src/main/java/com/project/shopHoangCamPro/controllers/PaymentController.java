package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.DTO.PaymentDTO;
import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final PaymentService paymentService;

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
            model.addAttribute("error", "Vui lòng chọn phương thức vận chuyển và thanh toán!");
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
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")); // Múi giờ Việt Nam
            Date date = Date.from(zonedDateTime.toInstant()); // Chuyển đổi thành Date

            if ("COD".equals(paymentMethod)) {
                // Xử lý thanh toán khi mua hàng trực tiếp
                order.setDate(date);
                if(order.getNote() == null){
                    order.setNote("");
                }
                if(order.getStatus() == null){
                    order.setStatus("Chờ duyệt đơn");
                }
                order.setUser(userDetails.getUser());
                order.setPaymentStatus("Chưa thanh toán");
                order.setPaymentMethod("COD");
                order.setTotalMoney(totalMoney);
                order.setActive(true);
                orderService.save(order);

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
            } else if ("VN_PAY".equals(paymentMethod)) { // nếu thanh toán bằng vn pay
                order.setDate(date);
                if(order.getNote() == null){
                    order.setNote("");
                }
                if(order.getStatus() == null){
                    order.setStatus("");
                }
                order.setUser(userDetails.getUser());
                order.setPaymentStatus("");// kiểm tra bên /vn-pay-callback
                order.setPaymentMethod("VNPAY");
                order.setTotalMoney(totalMoney);
                order.setActive(true);
                orderService.save(order);

                ProductVariant productVariant = productVariantService.getProductVariantById(variantId);

                orderDetail.setOrder(order);
                orderDetail.setProduct(productVariant);
                orderDetail.setNumberOfProducts(quantity);
                Float priceValue = Float.parseFloat(productVariant.getDiscount()); // Chuyển đổi từ String sang Float
                orderDetail.setPrice(priceValue);
                Float totalPrice = priceValue * quantity; // Nhân giá với số lượng
                orderDetail.setTotalPrice(totalPrice);
                orderDetailService.save(orderDetail);
                return "redirect:/payment/vn-pay?amount=" + (long)(totalMoney *100L) + "&orderId=" + order.getId();
            }
            return "error";
        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Có lỗi xảy ra khi mua hàng");
//            return "error";
            e.printStackTrace();
            throw new RuntimeException("Error saving OrderDetail: " + e.getMessage());
        }
    }


    @GetMapping("/vn-pay")
    public String pay(
            @RequestParam("amount") Long amount,
            @RequestParam("orderId") Integer orderId,
            Model model,
            HttpServletRequest request) {
        try {
            request.setAttribute("amount", amount); // Gán lại amount cho request nếu cần
            request.setAttribute("orderId", orderId);
            // Lấy payment URL từ paymentService
            PaymentDTO.VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request);

            // Kiểm tra nếu paymentUrl không null và trả về cho người dùng
            if (vnPayResponse != null && vnPayResponse.paymentUrl != null) {
                String paymentUrl = vnPayResponse.paymentUrl;

                // Trả lại paymentUrl hoặc làm điều gì đó với nó
                model.addAttribute("paymentUrl", paymentUrl);
                return "redirect:" + paymentUrl;  // Redirect tới payment URL
            } else {
                model.addAttribute("error", "Không có URL thanh toán");
                return "error";  // Trả về trang lỗi nếu không có paymentUrl
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            model.addAttribute("error", "Có lỗi xảy ra khi lấy URL thanh toán");
            return "error";
        }
//        return new ResponseObject<>(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/vn-pay-callback")
    public String handlePaymentCallback(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String orderId = request.getParameter("vnp_TxnRef");
        String message;
        if ("00".equals(status)) {
            orderService.updatePaymentStatus(Integer.parseInt(orderId), "Đã thanh toán", "Chờ duyệt đơn");
            message = "Thanh toán thành công!";
        } else if ("24".equals(status)) {
            orderService.updatePaymentStatus(Integer.parseInt(orderId), "Đã hủy đơn", "Đã hủy đơn");
            message = "Khách hàng đã hủy giao dịch.";
        } else {
            orderService.updatePaymentStatus(Integer.parseInt(orderId), "Lỗi khi thanh toán", "Lỗi");
            message = "Có lỗi xảy ra khi thanh toán.";
        }
        // Thêm thông điệp vào RedirectAttributes
        redirectAttributes.addFlashAttribute("message", message);

        // Redirect về trang chủ
        return "redirect:/";
    }

//    @GetMapping("/vn-pay")
//    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
//        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
//    }


//    @GetMapping("/vn-pay-callback")
//    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
//        String status = request.getParameter("vnp_ResponseCode");
//        if (status.equals("00")) {
//            PaymentDTO.VNPayResponse response = PaymentDTO.VNPayResponse.builder()
//                    .code("00")
//                    .message("Success")
//                    .paymentUrl("")
//                    .build();
//            return new ResponseObject<>(HttpStatus.OK, "Success", response);
//        } else {
//            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
//        }
//    }

}
