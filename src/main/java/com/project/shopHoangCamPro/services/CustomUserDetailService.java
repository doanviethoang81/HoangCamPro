package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.models.Role;
import com.project.shopHoangCamPro.models.User;
import com.project.shopHoangCamPro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.findByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("Số điện thoại này chưa đăng ký!");
        }
        // Lấy quyền từ bảng Role
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole_id();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new CustomUserDetail(user, authorities);
    }

}
