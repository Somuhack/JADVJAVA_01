package com.example.day2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Mycontroller {
    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name","Somnath");
        model.addAttribute("age",30);
        model.addAttribute("address","Kolkata");
        return "Home";
    }
    
}
