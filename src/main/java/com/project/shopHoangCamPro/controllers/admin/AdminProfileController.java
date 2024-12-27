package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.models.User;
import com.project.shopHoangCamPro.repository.UserRepository;
import com.project.shopHoangCamPro.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("admin/")
@RequiredArgsConstructor
public class AdminProfileController {

    private final UserServiceImpl userService;

    @Autowired
    private UserServiceImpl userServiceImpl;


    private UserRepository userRepository;

    @RequestMapping("/profile/edit")
    public String editProfile(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //định dạng ngày sinh (chỉ lấy ngày, tháng, năm)
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateOfBirth = dateFormat.format(userDetails.getDateOfBirth());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedDateOfBirth", formattedDateOfBirth);
        return  "admin/profile/edit";
    }

    @PostMapping("/profile-edit/{id}")
    public String updatePoifile(
            @PathVariable("id") Integer id,
            @Valid
            @ModelAttribute("user") User updateProfile,
            BindingResult result,
            Model model,
            @RequestParam("confirm-password") String confirmPassword,//lấy confirm passwprd
            RedirectAttributes redirectAttributes)
    {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập lại mật khẩu!");
            return "admin/profile/edit";
        }
        //kiểm tra mật khẩu mới và mật khẩu nhập lại có giống nhau không
        if (!updateProfile.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và mật khẩu nhập lại không khớp!");
            return "admin/profile/edit";
        }
        if (userService.isPhoneExistsEdit(updateProfile.getPhone(), updateProfile.getId())) {
            redirectAttributes.addFlashAttribute("error", "Số điện thoại đã được đăng ký!");
            return "redirect:/admin/profile/edit";
        }
        try {
            // Cập nhật thông tin người dùng
            userServiceImpl.updateProfile(id, updateProfile);

            // Lấy lại user mới từ cơ sở dữ liệu
            User updatedUser = userServiceImpl.getUserById(id);

            // Chuyển đối tượng User thành CustomUserDetail
            CustomUserDetail updatedUserDetails = new CustomUserDetail(updatedUser);
            // Kiểm tra quyền của người dùng
            System.out.println("Updated Authorities: " + updatedUserDetails.getAuthorities());
            // Cập nhật lại SecurityContextHolder
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    updatedUserDetails,
                    null,
                    updatedUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Thêm thông tin updatedUser vào model để hiển thị trong view nếu cần
            model.addAttribute("user", updatedUser);

            // Thông báo thành công
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
            return "redirect:/admin/profile/edit";
        } catch (Exception e) {
            // Thông báo lỗi
            redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại! ");
            return "redirect:/admin/profile/edit";
        }
    }

}
