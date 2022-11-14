package com.japarejo.springmvc.player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.japarejo.springmvc.match.Match;
import com.japarejo.springmvc.match.MatchService;
import com.japarejo.springmvc.user.User;
import com.japarejo.springmvc.user.UserService;

@Controller
@RequestMapping("/playerstats")
public class PlayerController {

    public static final String PLAYER_LISTING = "PlayerListing";

    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
 
    @GetMapping("/{user_id}")
    ModelAndView playerStats(@PathVariable("user_id") Integer user_id) {
        ModelAndView result = new ModelAndView(PLAYER_LISTING);
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(user_id);
        User user = userService.getUserById(user_id);
        result.addObject("user", user);
        result.addObject("stats", allStats);
        return result;
    }
}
