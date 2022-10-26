package com.japarejo.springmvc.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping(path = {"","/"})   
    public String welcome(){
        return "welcome";
    }
}
