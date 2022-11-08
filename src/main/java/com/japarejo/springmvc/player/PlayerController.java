package com.japarejo.springmvc.player;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.japarejo.springmvc.user.User;
import com.japarejo.springmvc.user.UserService;

@Controller
@RequestMapping("/playerstats")
public class PlayerController {

    public static final String PLAYER_LISTING = "PlayerListing";
    public static final String PLAYER_MATCH_LISTING = "PlayerMatchListing";

    private final PlayerService ps;
    private final UserService us;

    @Autowired
    PlayerController(PlayerService ps, UserService us) {
        this.ps = ps;
        this.us = us;
    }


    @GetMapping("/user/{user_id}")
    ModelAndView playerStats(@PathVariable("user_id") Integer user_id) {
        ModelAndView result = new ModelAndView(PLAYER_LISTING);
        List<PlayerMatchStats> stats = this.ps.giveAllStatsForPlayer(user_id);
        result.addObject("stats", stats);
        User user = this.us.getUserById(user_id);
        result.addObject("user", user);
        return result;
    }

    @GetMapping("/match/{match_id}/user/{user_id}")
    ModelAndView playerMatchStats(@PathVariable("match_id") Integer match_id, @PathVariable("user_id") Integer user_id) {
        ModelAndView result = new ModelAndView(PLAYER_MATCH_LISTING);
        PlayerMatchStats stats = this.ps.findStatsForPlayerAndMatch(user_id, match_id);
        result.addObject("stats", stats);
        return result;

    }
}
