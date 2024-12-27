package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.Role;
import com.project.shopHoangCamPro.models.User;
import com.project.shopHoangCamPro.repository.RoleRepository;
import com.project.shopHoangCamPro.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @PostMapping("/register")
    public String addCustomer(
            @Valid
            @ModelAttribute("USER") User user,
            BindingResult result,
            Model model,
            @RequestParam("confirm-password") String confirmPassword,//lấy confirm passwprd
            RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "register";
        }
        if (userService.isPhoneExists(user.getPhone())) {
            model.addAttribute("error", "Số điện thoại đã được đăng ký!");
            return "register";
        }
        // Kiểm tra confirmPassword bị trống
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập lại mật khẩu!");
            return "register";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error",  "Mật khẩu và Nhập lại mật khẩu không khớp!");
            return "register";
        }
        try {
            if (user.getIsActive() == null) {
                user.setIsActive(true); // Trạng thái mặc định là true
            }
            // Gán role_id là 1 (giả sử 1 là USER)
            Role role = new Role();
            role.setId(1); // ID của vai trò USER là 1
            user.setRole_id(role); // Gán vai trò cho người dùng

            user.setPassword(passwordEncoder.encode(user.getPassword()));//mã hóa mật kẩu
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Đăng ký tài khoản thành công");
            return "redirect:/register";
        } catch (Exception e) {
            e.printStackTrace();// bat loi
            model.addAttribute("error", "Có lỗi xảy ra khi thêm người dùng!");
            return "register";
        }
    }
}

//// Lấy vai trò mặc định từ cơ sở dữ liệu (giả sử "USER" là vai trò mặc định)
//Role role = roleRepository.findByName(Role.USER);  // Bạn cần RoleRepository để lấy vai trò USER
//            if (role == null) {
//// Nếu chưa có vai trò USER, tạo mới
//role = new Role();
//                role.setName(Role.USER);
//                roleRepository.save(role); // Lưu vai trò mới vào cơ sở dữ liệu
//            }
//
//                    // Gán vai trò cho người dùng
//                    user.setRole_id(role);