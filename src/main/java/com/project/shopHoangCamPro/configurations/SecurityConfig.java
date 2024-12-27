package com.project.shopHoangCamPro.configurations;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tắt CSRF để xử lý đơn giản hơn
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/login").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/product").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/categories").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/category_security").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/category_smart_home").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/product/product_security_home").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/product_detail").permitAll() // Cho phép truy cập trang đăng nhập
                        .requestMatchers("/admin/add-product").permitAll() // Cho phép truy cập trang đăng nhập


                        .requestMatchers("/admin/**").hasAuthority("admin") // ADMIN mới được vào /admin/**
//                        .requestMatchers("/product/**").hasAuthority("user") // USER mới được vào /product/**
                        .anyRequest().authenticated()) // Các trang khác yêu cầu đăng nhập
                .formLogin(login -> login
                        .loginPage("/login") // Trang đăng nhập
                        .loginProcessingUrl("/login") // Xử lý logic đăng nhập
                        .usernameParameter("name") // Input tên người dùng
                        .passwordParameter("password") // Input mật khẩu
                        .successHandler((request, response, authentication) -> { // Xử lý đăng nhập thành công

                            //Lấy thông tin người dùng từ authentication
//                            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

                            // Lưu ID vào session nếu cần
//                            request.getSession().setAttribute("userId", userDetails.getName());

                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            if ("admin".equals(role)) {
                                response.sendRedirect("/admin"); // Điều hướng ADMIN
                            } else if ("user".equals(role)) {
                                response.sendRedirect("/"); // Điều hướng USER
                            }
                        })
                        .failureHandler((request, response, exception) -> { // Xử lý đăng nhập thất bại
                            request.getSession().setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
                            response.sendRedirect("/login"); // Quay lại trang đăng nhập
                        }))
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL đăng xuất
                        .logoutSuccessUrl("/login?logout") // Quay về trang login khi đăng xuất thành công
                        .permitAll()); // Cho phép tất cả được logout
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        System.out.println("Ignoring static files for security...");
        return (web -> web.ignoring().requestMatchers("/static/**","/fe/**","/assets/**","/uploads/**"));
    }
}
