package com.project.shopHoangCamPro.services;

//import com.white.apidoc.core.config.payment.VNPAYConfig;
//import com.white.apidoc.util.VNPayUtil;
import com.project.shopHoangCamPro.DTO.PaymentDTO;
import com.project.shopHoangCamPro.configurations.VNPAYConfig;
import com.project.shopHoangCamPro.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
//    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
////        String bankCode = request.getParameter("bankCode");
//
////        long amount =  100000*100L;
//        String bankCode = "NCB";
//        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
//        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnpParamsMap.put("vnp_BankCode", bankCode);
//        }
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//        //build query url
//        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
//        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
//        queryUrl += "&vnp_SecureHash=" + VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//        return PaymentDTO.VNPayResponse.builder()
//                .code("ok")
//                .message("success")
//                .paymentUrl(paymentUrl).build();
//    }

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Long.parseLong(request.getParameter("amount"));
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
//        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = "NCB"; // Hoặc lấy từ request nếu cần
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_TxnRef", String.valueOf(orderId));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        queryUrl += "&vnp_SecureHash=" + VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

}
