package com.ling1.springmvc.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/playerstats")
public class PlayerController {

    public static final String PLAYER_LISTING = "PlayerListing";
    public static final String PLAYER_RECORD = "PlayerRecord";

    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
 
    @GetMapping("/history/{user_id}")
    ModelAndView playerhistory(@PathVariable("user_id") Integer user_id) {
        ModelAndView result = new ModelAndView(PLAYER_LISTING);
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(user_id);
        User user = userService.getUserById(user_id);
        result.addObject("user", user);
        result.addObject("stats", allStats);
        return result;
    }
  /*   @GetMapping("/{user_id}")
    ModelAndView playerStats(@PathVariable("user_id") Integer user_id) {
        ModelAndView result = new ModelAndView(PLAYER_RECORD);
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(user_id);
        PlayerStats sum = new PlayerStats();
        List<PlayerColor> colorsChosen = new ArrayList<>();
        User user = userService.getUserById(user_id);
        for (PlayerStats ps : allStats){
            if(ps.getNumDiceRolls()==null) sum.setNumDiceRolls(
                ps.getNumDiceRolls()+
                sum.getNumDiceRolls());
            if(ps.getNumTurnsPlayer()==null)sum.setNumTurnsPlayer(ps.getNumTurnsPlayer()+sum.getNumTurnsPlayer());
            if(ps.getNumberOfGooses()==null)sum.setNumberOfGooses(ps.getNumberOfGooses()+sum.getNumberOfGooses());
            if(ps.getNumberOfLabyrinths()==null) sum.setNumberOfLabyrinths(ps.getNumberOfLabyrinths()+sum.getNumberOfLabyrinths());
            if(ps.getNumberOfPlayerDeaths()==null)sum.setNumberOfPlayerDeaths(ps.getNumberOfPlayerDeaths()+sum.getNumberOfPlayerDeaths());
            if(ps.getNumberOfPlayerPrisons()==null) sum.setNumberOfPlayerPrisons(ps.getNumberOfPlayerPrisons()+sum.getNumberOfPlayerPrisons());
            if(ps.getNumberOfPlayerWells()==null)sum.setNumberOfPlayerWells(ps.getNumberOfPlayerWells()+sum.getNumberOfPlayerWells());
            if(ps.getPosition()==null)sum.setPosition(ps.getPosition()+sum.getPosition());
            colorsChosen.add(ps.getPlayerColor());
        }
        sum.setPlayerColor(colorsChosen.stream()
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
      .entrySet()
      .stream()
      .max(Map.Entry.comparingByValue()).get().getKey());
        result.addObject("user", user);
        result.addObject("stats", sum);
        return result; 
    }*/
}
