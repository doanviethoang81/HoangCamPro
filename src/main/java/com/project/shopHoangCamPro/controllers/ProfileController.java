package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.models.User;
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
import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserServiceImpl userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping("")
    public String profile(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //định dạng ngày sinh (chỉ lấy ngày, tháng, năm)
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateOfBirth = dateFormat.format(userDetails.getDateOfBirth());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedDateOfBirth", formattedDateOfBirth);
        return "profile";
    }

    @RequestMapping("/edit-profile")
    public String editProfile(Model model) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateOfBirth = dateFormat.format(userDetails.getDateOfBirth());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedDateOfBirth", formattedDateOfBirth);
        return "edit_profile";
    }

    @PostMapping("/edit-profile/{id}")
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
            return "edit_profile";
        }
        //kiểm tra mật khẩu mới và mật khẩu nhập lại có giống nhau không
        if (!updateProfile.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và mật khẩu nhập lại không khớp!");
            return "edit_profile";
        }

        // Chuyển đổi từ java.util.Date sang LocalDate
        LocalDate dateOfBirth = updateProfile.getDateOfBirth().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (dateOfBirth.isAfter(LocalDate.now())) {
            model.addAttribute("error", "Ngày sinh không được lớn hơn ngày hiện tại!");
            return "edit_profile";
        }
        if (userService.isPhoneExistsEdit(updateProfile.getPhone(), updateProfile.getId())) {
            redirectAttributes.addFlashAttribute("error", "Số điện thoại đã được đăng ký!");
            return "redirect:/profile/edit-profile";
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
            return "redirect:/profile/edit-profile";
        } catch (Exception e) {
            // Thông báo lỗi
            redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại! ");
            return "redirect:/profile/edit-profile";
        }
    }
}
