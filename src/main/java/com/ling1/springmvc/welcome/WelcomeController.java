package com.ling1.springmvc.welcome;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ling1.springmvc.ocaBoard.OcaService;

@Controller
public class WelcomeController {
    @Autowired
    OcaService ocaService;

    @GetMapping(path = {"","/welcome"})   
    public String welcome(Map<String, Object> model, HttpServletResponse response){
        //response.addHeader("Refresh", "1");
        model.put("now", new Date());
        model.put("ocaBoard", ocaService.findById(1).get());
        return "welcome";
    }
}
