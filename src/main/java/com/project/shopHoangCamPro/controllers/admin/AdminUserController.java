package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.*;
import com.project.shopHoangCamPro.services.RoleService;
import com.project.shopHoangCamPro.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin/")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping("/user/add")
    public String add(Model model){
        model.addAttribute("bodyContent", "admin/user/add");
        model.addAttribute("USER", new User());
        model.addAttribute("ROLE", roleService.findAll());
        return "admin/layout";
    }

    @PostMapping("/add-user")
    public String addCustomer(
            @Valid
            @ModelAttribute("USER") User user,
            BindingResult result,
            Model model,
            @RequestParam("confirm-password") String confirmPassword,//lấy confirm passwprd
            RedirectAttributes redirectAttributes) {
        model.addAttribute("bodyContent", "admin/user/add");
        if(result.hasErrors()){
            return "admin/layout";
        }
        if (userService.isPhoneExists(user.getPhone())) {
            model.addAttribute("error", "Số điện thoại đã được đăng ký!");
            return "admin/layout";
        }
        // Kiểm tra confirmPassword bị trống
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập lại mật khẩu!");
            return "admin/layout";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error",  "Mật khẩu và Nhập lại mật khẩu không khớp!");
            return "admin/layout";
        }
        try {
            if (user.getIsActive() == null) {
                user.setIsActive(true); // Trạng thái mặc định là true
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));//mã hóa mật kẩu
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Thêm người dùng thành công!");
            return "redirect:/admin/user/add";
        } catch (Exception e) {
            e.printStackTrace();// bat loi
            model.addAttribute("error", "Có lỗi xảy ra khi thêm người dùng!");
            return "admin/user/add";
        }
    }

    @RequestMapping("/user-edit/{id}")
    public String getEditUser(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("bodyContent", "admin/user/edit");
            List<Role> roles = roleService.findAll();
            model.addAttribute("listRoles", roles);
            User user = userServiceImpl.getUserById(id);
            if (user == null) {
                throw new RuntimeException("User không tồn tại");
            }
            model.addAttribute("USER", user);
            return "admin/layout";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

//    update quyền nười dùng
    @PostMapping("/edit-user/{id}")
    public String updateRoleUser(
            @PathVariable("id") Integer id,
            @ModelAttribute("USER") User updateRoleUser,
            Model model,
            RedirectAttributes redirectAttributes){
        model.addAttribute("bodyContent", "admin/user/edit");
        if(updateRoleUser.getRole_id() == null){
            redirectAttributes.addFlashAttribute("error","Chưa nhập quyền người dùng!");
            return "redirect:/admin/user-edit/" + updateRoleUser.getId();
        }
        try {
            userServiceImpl.updateRoleUser(id,updateRoleUser);
            redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công!");
            return "redirect:/admin/user-edit/" + updateRoleUser.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại: ");
            return "redirect:/admin/user-edit/" + updateRoleUser.getId();
        }
    }

    //khóa user
    @GetMapping("/user-lock/{id}")
    public String blockUser(
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes){
        try {
            userServiceImpl.lockUser(id);
            redirectAttributes.addFlashAttribute("message", "Chặn người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Chặn người dùng thất bại! ");
        }
        return "redirect:/admin/user";
    }

    //mở khóa
    @GetMapping("/user-unblock/{id}")
    public String unblockUser(
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes){
        try {

            userServiceImpl.unblockUser(id);
            redirectAttributes.addFlashAttribute("message", "Mở khóa người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Mở khóa người dùng thất bại! ");
        }
        return "redirect:/admin/user";
    }
}
