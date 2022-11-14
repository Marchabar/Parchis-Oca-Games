package com.japarejo.springmvc.match;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RollDiceController {

    public static int NUM_DICES_SIDES = 6;

    @GetMapping("rolldice")
    public @ResponseBody Integer rollDices(HttpSession session){
        Integer dice = 1+(int)Math.floor(Math.random()*NUM_DICES_SIDES);
        session.setAttribute("dice", dice);
        return dice;
    }

}