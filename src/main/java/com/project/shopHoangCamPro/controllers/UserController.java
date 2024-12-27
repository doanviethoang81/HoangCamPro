package com.project.shopHoangCamPro.controllers;

import ch.qos.logback.core.model.Model;
import com.project.shopHoangCamPro.models.CustomUserDetail;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequiredArgsConstructor
@Controller
public class UserController {

    @RequestMapping("/login")
    public  String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return  "index";
    }

//    @GetMapping("")
//    public ResponseEntity<String> getkkk(){
//        return ResponseEntity.ok("chao bn");
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> createUser (
//            @Valid @RequestBody UserDTO userDTO,
//            BindingResult result) {
//        try{
//            if (result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
//                return ResponseEntity.badRequest().body("Password khong khop voi nhau");
//            }
////            return ResponseEntity.ok("register successfully");
//            return ResponseEntity.ok("ddang ky thanh cong");
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("e.getMessage()");
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @Valid @RequestBody UserLogDTO userLogDTO){
////        //kiểm tra thông itn đăng nhập
////        try {
////            String token =  userService.login(userLogDTO.getPhoneNumber(), userLogDTO.getPassword());
////            // trả về token trong response
//            return ResponseEntity.ok("some token");
////        } catch (Exception e) {
////            return ResponseEntity.badRequest().body(e.getMessage());
////        }
//
//    }
}
