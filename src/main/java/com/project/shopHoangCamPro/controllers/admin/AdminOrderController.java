package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.services.CartService;
import com.project.shopHoangCamPro.services.OrderDetailService;
import com.project.shopHoangCamPro.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    @RequestMapping("/order/{id}")
    public String getOrderId(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("bodyContent", "admin/order/order_detail");
            Order order = orderService.getById(id);
            List<OrderDetail> listOrderDetail = orderDetailService.getOrderDetailByOrderId(id);
            model.addAttribute("order", order);
            model.addAttribute("listOrderDetail", listOrderDetail);

            return "admin/layout";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


    @RequestMapping("/order/approveOrder/{id}")
    public String approveOrder(
            @PathVariable("id") Integer id,Model model,
            RedirectAttributes redirectAttributes
    ){
        try {
            orderService.updateApproveOrder(id);
            redirectAttributes.addFlashAttribute("message", "Duyệt đơn hàng thành công!");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Duyệt đơn hàng thất bại!");
        }
        return "redirect:/admin/order/" + id;

    }
}
