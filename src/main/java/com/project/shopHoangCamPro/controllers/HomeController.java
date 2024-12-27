package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("USER", new User());
        return "register";
    }
}
