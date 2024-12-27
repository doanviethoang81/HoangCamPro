package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.repository.CartDetailRepository;
import com.project.shopHoangCamPro.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;
    private final CartService cartService;
    private final UserServiceImpl userService;
    private final CartDetailService cartDetailService;
    private final CartDetailRepository cartDetailRepository;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody Map<String, Object> request) {// trả về Json
        Map<String, Object> response = new HashMap<>();

        try {
            // Lấy thông tin từ request
            Integer variantId = request.containsKey("variantId")
                    ? Integer.parseInt(request.get("variantId").toString())
                    : null;

            Integer quantity = request.containsKey("quantity")
                    ? Integer.parseInt(request.get("quantity").toString())
                    : 1;

            Float price = request.containsKey("price")
                    ? Float.parseFloat(request.get("price").toString())
                    : null;

            // Lấy thông tin người dùng hiện tại
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userId = userDetails.getId();

            // Kiểm tra xem user_id đã có trong bảng Cart hay chưa
            if (cartService.isUserIdExists(userId)) { // nếu user đã có giỏ hàng r
                // Lấy giỏ hàng của người dùng
                Cart cart = cartService.findCartByUserId(userId);
                // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                CartDetail existingCartDetail = cartDetailService.findByCartAndProductVariant(cart, variantId);

                if (existingCartDetail != null) {// nếu đã có sản phẩm thì cập nhat só lượng
                    // Nếu sản phẩm đã có, cập nhật số lượng
                    existingCartDetail.setQuantity(existingCartDetail.getQuantity() + quantity);
                    cartDetailService.save(existingCartDetail); // Lưu lại sự thay đổi
                    response.put("success", true);
                    response.put("message", "Số lượng sản phẩm trong giỏ hàng đã được cập nhật.");
                }
                else {
                    // Thêm sản phẩm vào giỏ hàng
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProductVariant(productVariantService.getProductVariantById(variantId));
                    cartDetail.setQuantity(quantity);
                    cartDetail.setPrice(price);
                    cartDetailService.save(cartDetail);

                    response.put("success", true);
                    response.put("message", "Sản phẩm đã được thêm vào giỏ hàng.");
                }
            } else {
                // Nếu user_id chưa có trong bảng Cart, tạo mới Cart và CartDetail
                Cart cart = new Cart();
                cart.setUser(userDetails.getUser());
                cartService.save(cart);

                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProductVariant(productVariantService.getProductVariantById(variantId));
                cartDetail.setQuantity(quantity);
                cartDetail.setPrice(price);
                cartDetailService.save(cartDetail);

                response.put("success", true);
                response.put("message", "Sản phẩm đã được thêm vào giỏ hàng.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra khi thêm vào giỏ hàng.");
        }
        return response;
    }

    @RequestMapping("")
    public String cart(Model model) {

        // Lấy thông tin người dùng hiện tại
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getId();

        // Kiểm tra xem user_id đã có trong bảng Cart hay chưa
        if (cartService.isUserIdExists(userId)) {
            // Lấy giỏ hàng của người dùng
            Cart cart = cartService.findCartByUserId(userId);
            List<CartDetail> cartDetails = cartService.findCartDetailsByCartId(cart.getId());

            if(cartDetails.size() > 0){
                model.addAttribute("cartDetails", cartDetails);
            }
            else{ // nếu có id user trong cart nhưng k có sản phẩm thì hiện giỏ trống
                model.addAttribute("message", "Giỏ hàng của bạn đang trống!");
            }

        } else {
            // Người dùng chưa có giỏ hàng
            model.addAttribute("message", "Giỏ hàng của bạn đang trống!");
            model.addAttribute("totalPrice", 0);
        }

        return "cart";
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> showCart(@RequestBody List<CartDetail> selectedProducts, HttpSession session) {
        // Lấy danh sách sản phẩm đã chọn
        List<CartDetail> cartDetails = new ArrayList<>();
        for (CartDetail cartDetail : selectedProducts) {
            Integer cartDetailId = cartDetail.getId();
            Optional<CartDetail> cartDetail2 = cartDetailRepository.findById(cartDetailId);
            cartDetail2.ifPresent(cartDetails::add);
        }

        // Lưu cartDetails vào session để sử dụng trong phương thức GET
        session.setAttribute("cartDetails", cartDetails);

        // Trả về trạng thái 200 OK (POST thành công)
        return ResponseEntity.ok().build();
    }


    @GetMapping("/payment")
    public String paymentPage(HttpSession session, Model model) {
        // Lấy cartDetails từ session
        model.addAttribute("Order", new Order());

        List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("cartDetails");
        if (cartDetails != null) {
            Integer quantityProductInCart = cartDetails.size(); //số lượng sản pham cua ng mua

            //tính tổng tiền
            double totalPrice = cartDetails.stream()
                    .mapToDouble(cartDetail -> cartDetail.getPrice() * cartDetail.getQuantity())
                    .sum();
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartDetails", cartDetails);
            model.addAttribute("quantityProductInCart", quantityProductInCart);
        } else {
            model.addAttribute("cartDetails", new ArrayList<>()); // Tránh lỗi null
        }
        return "payment_cart"; // Trả về view payment.html
    }

//    thanh toán  các sản phẩm từ giỏ hàng
    @PostMapping("/buy")
    public String userBuy(
            @Valid @ModelAttribute("Order") Order order,
            BindingResult result,
            HttpSession session,
            @RequestParam("totalMoney") Float totalMoney,
            @RequestParam(value = "deliveryFee", required = false) String deliveryFee,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
            Model model) throws Exception {

        // Lấy danh sách cartDetails từ session
        List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("cartDetails");

        // Xử lý lỗi validate
        if (result.hasErrors()) {
            if (cartDetails != null) {
                double totalPrice = cartDetails.stream()
                        .mapToDouble(cartDetail -> cartDetail.getPrice() * cartDetail.getQuantity())
                        .sum();
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("cartDetails", cartDetails);
                model.addAttribute("quantityProductInCart", cartDetails.size());
            } else {
                model.addAttribute("cartDetails", new ArrayList<>()); // Tránh lỗi null
            }
            return "payment_cart";
        }
        // Kiểm tra checkbox
        if (deliveryFee == null || paymentMethod == null) {
            model.addAttribute("error", "Vui lòng chọn phương thức vận chuyển và thanh toán.");
            if (cartDetails != null) {
                double totalPrice = cartDetails.stream()
                        .mapToDouble(cartDetail -> cartDetail.getPrice() * cartDetail.getQuantity())
                        .sum();
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("cartDetails", cartDetails);
                model.addAttribute("quantityProductInCart", cartDetails.size());
            } else {
                model.addAttribute("cartDetails", new ArrayList<>()); // Tránh lỗi null
            }
            return "payment_cart";
        }
        try {
            // Lấy thông tin người dùng
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

            // Lưu Order vào cơ sở dữ liệu
            orderService.save(order);

            // Lặp qua cartDetails để tạo và lưu từng OrderDetail
            for (CartDetail cartDetail : cartDetails) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartDetail.getProductVariant());
                orderDetail.setNumberOfProducts(cartDetail.getQuantity());
                orderDetail.setPrice(cartDetail.getPrice());
                orderDetail.setTotalPrice(cartDetail.getPrice() * cartDetail.getQuantity());

                // Lưu OrderDetail vào cơ sở dữ liệu
                orderDetailService.save(orderDetail);

                Integer cartDetailId = cartDetail.getId();
                cartDetailService.deleteById(cartDetailId);
            }

            // Xóa cartDetails khỏi session sau khi lưu xong
            session.removeAttribute("cartDetails");

            return "success"; // Trang cảm ơn sau khi thanh toán thành công
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving Order or OrderDetails: " + e.getMessage());
        }
    }

}
