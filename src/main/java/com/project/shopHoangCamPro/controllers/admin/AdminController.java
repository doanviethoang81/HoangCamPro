package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.repository.UserRepository;
import com.project.shopHoangCamPro.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller("productControllerAdmin")
@RequiredArgsConstructor

public class AdminController {

    private final ProductService productService;
    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final ProductVariantService productVariantService;

//    @RequestMapping("/admin")
//    public String index(){
//        return  "redirect:/admin";
//    }

    @RequestMapping("/admin")
    public String index(Model model) {
        model.addAttribute("bodyContent", "admin/index");
        return  "/admin/layout";  // Trả về view admin/index.html
    }

    @RequestMapping("/admin/order")
    public String order(Model model, @RequestParam(name ="pageNo", defaultValue = "1") Integer pageNo) {

        model.addAttribute("bodyContent", "admin/order/index");
        Page<Order> orders = this.orderService.getAll(pageNo);
        model.addAttribute("listOrder", orders);
        model.addAttribute("totalPage", orders.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return  "admin/layout";  // Trả về view admin/index.html
    }

    @RequestMapping("/admin/product")
    public String product(Model model, @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo){
        model.addAttribute("bodyContent", "admin/product/index");
        Page<Product> list = this.productService.getAll(pageNo);
        List<OrderDetail> orderDetails = this.orderDetailService.getAll();

        List<Integer> productIds = orderDetails.stream()
                .map(orderDetail -> orderDetail.getProduct().getId()) // Lấy ID từ Product
                .collect(Collectors.toList());
        List<ProductVariant> productVariants = productVariantService.getProductsByIds(productIds);

        List<Product> productsFromVariants = productVariants.stream()
                .map(ProductVariant::getProduct) // Lấy Product từ ProductVariant
                .distinct() // Loại bỏ các giá trị trùng lặp
                .collect(Collectors.toList());

        model.addAttribute("productsFromVariants", productsFromVariants);
        model.addAttribute("list", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("orderDetails", orderDetails);

        return "/admin/layout";
    }

    @RequestMapping("/admin/user")
    public String user(Model model, @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo){
        model.addAttribute("bodyContent", "admin/user/index");
        Page<User> users = this.userService.getAll(pageNo);
        model.addAttribute("listUser", users);
        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return  "/admin/layout";  // Trả về view admin/index.html
    }

//    sử dụng SecurityContextHolder để lấy thông tin ng dùng đăng nhập
    @RequestMapping("/admin/profile")
    public String getProfile(Model model) {

        model.addAttribute("bodyContent", "admin/profile/index");
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //định dạng ngày sinh (chỉ lấy ngày, tháng, năm)
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateOfBirth = dateFormat.format(userDetails.getDateOfBirth());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedDateOfBirth", formattedDateOfBirth);
        return "/admin/layout";
    }

}
