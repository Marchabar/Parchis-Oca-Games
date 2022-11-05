package com.japarejo.springmvc.user2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    
    public static final String USERS_LISTING="UsersListing";

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ModelAndView showUsersListing(){
        ModelAndView result = new ModelAndView(USERS_LISTING);
        result.addObject("users", userService.getAllUsers());
        return result;
    }
}
