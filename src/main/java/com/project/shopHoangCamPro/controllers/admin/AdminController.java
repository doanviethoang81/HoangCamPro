package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.repository.UserRepository;
import com.project.shopHoangCamPro.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String index() {
        return  "admin/index";  // Trả về view admin/index.html
    }

    @RequestMapping("/admin/order")
    public String order(Model model) {
        List<Order> orders = this.orderService.findAll();
        model.addAttribute("listOrder", orders);
        return  "admin/order/index";  // Trả về view admin/index.html
    }

    @RequestMapping("/admin/product")
    public String product(Model model){
        List<Product> list = this.productService.getAll();
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

        model.addAttribute("orderDetails", orderDetails);

        return "/admin/product/index";
    }

    @RequestMapping("/admin/user")
    public String user(Model model) {
        List<User> users = this.userService.findAll();

        model.addAttribute("listUser", users);
        return  "/admin/user/index";  // Trả về view admin/index.html
    }

//    sử dụng SecurityContextHolder để lấy thông tin ng dùng đăng nhập
    @RequestMapping("/admin/profile")
    public String getProfile(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //định dạng ngày sinh (chỉ lấy ngày, tháng, năm)
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateOfBirth = dateFormat.format(userDetails.getDateOfBirth());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedDateOfBirth", formattedDateOfBirth);
        return "/admin/profile/index";
    }

}
