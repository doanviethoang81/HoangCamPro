package com.project.shopHoangCamPro.configurations;

import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
                        .requestMatchers("/register", "/", "/login")
//                                "/product/get-price/**",
//                                "/product/product_security_home/**",
//                                "/product/product_detail/**")
                        .permitAll() // Cho phép truy cập các trang này

                        .requestMatchers("/admin/**").hasAuthority("admin") // ADMIN mới được vào /admin/**
//                        .requestMatchers("/cart/**").hasAuthority("user") // USER mới được vào /product/**
                        .anyRequest().authenticated()) // Các trang khác yêu cầu đăng nhập
                .formLogin(login -> login
                        .loginPage("/login") // Trang đăng nhập
                        .loginProcessingUrl("/login") // Xử lý logic đăng nhập
                        .usernameParameter("phone") // Input phone người dùng
                        .passwordParameter("password") // Input mật khẩu
                        .successHandler((request, response, authentication) -> { // Xử lý đăng nhập thành công

                            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
                            if (!user.getIsActive()) {
                                request.getSession().setAttribute("error", "Tài khoản của bạn đã bị khóa.");
                                response.sendRedirect("/login");
                                return;
                            }
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
