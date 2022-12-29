package com.ling1.springmvc.achievements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/achievements")
public class AchievementController {
    
    public static final String ACHIEVEMENTS_LISTING="Achievements/AchievementsListing";
    @Autowired
    private UserService userService;
    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private AchievementService achievementService;
    
    @GetMapping
    public ModelAndView showAchievementListing(){
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_LISTING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(loggedUser.getId());
        if(allStats.isEmpty()) {
            result = new ModelAndView("welcome");
            result.addObject("message", "No achievements available");
            return result;
        }
        List<Achievement> myAchievements = new ArrayList<>();
        PlayerStats total = new PlayerStats();
        Integer numDiceRolls =0;
        List<PlayerColor> colors = new ArrayList<>();
        Integer tilesAdvanced =0;
        Integer GoosesStepped =0;
        Integer WellsFallen =0;
        Integer LabyrinthLosses =0;
        Integer PrisonsEntered =0;
        Integer Deaths =0;
        for (PlayerStats ps : allStats){
            if (ps.getNumDiceRolls()!=null) 
            numDiceRolls=numDiceRolls+ps.getNumDiceRolls();
            colors.add(ps.getPlayerColor());
            if (ps.getPosition()!=null) 
            tilesAdvanced=tilesAdvanced+ps.getPosition();
            if (ps.getNumberOfGooses()!=null) 
            GoosesStepped=GoosesStepped+ps.getNumberOfGooses();
            if (ps.getNumberOfPlayerWells()!=null) 
            WellsFallen=WellsFallen+ps.getNumberOfPlayerWells();
            if (ps.getNumberOfLabyrinths()!=null) 
            LabyrinthLosses=LabyrinthLosses+ps.getNumberOfLabyrinths();
            if (ps.getNumberOfPlayerPrisons()!=null) 
            PrisonsEntered=PrisonsEntered+ps.getNumberOfPlayerPrisons();
            if (ps.getNumberOfPlayerDeaths()!=null) 
            Deaths=Deaths+ps.getNumberOfPlayerDeaths();
        }
        total.setNumDiceRolls(numDiceRolls);
        total.setPlayerColor(colors.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
      .entrySet()
      .stream()
      .max(Map.Entry.comparingByValue()).get().getKey());
        total.setPosition(tilesAdvanced);
        total.setNumberOfGooses(GoosesStepped);
        total.setNumberOfPlayerWells(WellsFallen);
        total.setNumberOfLabyrinths(LabyrinthLosses);
        total.setNumberOfPlayerPrisons(PrisonsEntered);
        total.setNumberOfPlayerDeaths(Deaths);
        
        if(total.getNumDiceRolls() >= 1){
            myAchievements.add(achievementService.findAchievementByName("Roller I"));
        }
        if(total.getNumDiceRolls() >= 10){
            myAchievements.add(achievementService.findAchievementByName("Roller II"));
        }
        if(total.getNumDiceRolls() >= 100){
            myAchievements.add(achievementService.findAchievementByName("Roller III"));
        }
        if(total.getNumDiceRolls() >= 1000){
            myAchievements.add(achievementService.findAchievementByName("Roller IV"));
        }
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
        result.addObject("achievements", achievementService.getAllAchievements());
        result.addObject("myAchievements", myAchievements);
        return result;
    }
}
