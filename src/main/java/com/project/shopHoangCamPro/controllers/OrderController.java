package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.models.OrderDetail;
import com.project.shopHoangCamPro.services.OrderDetailService;
import com.project.shopHoangCamPro.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @RequestMapping("")
    public String index(Model model) {
        try {
            // Lấy thông tin người dùng hiện tại
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userId = userDetails.getId();

            // Kiểm tra nếu có đơn hàng của user
            if (orderService.isUserIdExists(userId)) {
                // Lấy danh sách Order dựa vào userId
                List<Order> orders = orderService.findOrdersByUserId(userId);

                // Sắp xếp danh sách đơn hàng theo date mới nhất
                orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

                // Danh sách để lưu các chi tiết đơn hàng (bao gồm nhiều mặt hàng)
                LinkedHashMap<Order, List<OrderDetail>> orderDetailsMap = new LinkedHashMap<>();
//               Map<Order, List<OrderDetail>> orderDetailsMap = new HashMap<>();

                // Duyệt qua từng đơn hàng và lấy chi tiết của chúng
                for (Order order : orders) {
                    // Lấy danh sách các OrderDetail của mỗi đơn hàng
                    List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByOrderId(order.getId());
                    // Thêm vào Map để liên kết mỗi đơn hàng với các chi tiết của nó
                    orderDetailsMap.put(order, orderDetails);
                }

                // Gửi dữ liệu tới view (model)
                model.addAttribute("orders", orders); // Danh sách đơn hàng
                model.addAttribute("orderDetailsMap", orderDetailsMap); // Chi tiết đơn hàng

            } else {
                // Nếu không có đơn hàng, gửi thông báo
                model.addAttribute("message", "Bạn chưa có đơn hàng nào.");
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            model.addAttribute("error", "Đã xảy ra lỗi khi xử lý dữ liệu: " + e.getMessage());
        }

        return "order"; // Trả về view order.html
    }
}
