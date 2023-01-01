package com.ling1.springmvc.achievements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ling1.springmvc.friend.FriendService;
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
    public static final String ACHIEVEMENT_EDIT="Achievements/EditAchievement";

    @Autowired
    private UserService userService;
    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private FriendService friendService;
    
    @ModelAttribute("types")
    public Collection<AchievementType> populateAchievement(){
        return this.achievementService.findTypes();
    }
    
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
        
        for (Achievement a : achievementService.getAllAchievements()){
            if (a.getAchievementType().getName().equals("DICE")){
                if (total.getNumDiceRolls() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("FRIENDS")){
                if (friendService.getMyFriends(loggedUser).size() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("GOOSE")){
                if (total.getNumberOfGooses() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("MATCHES_PLAYED")){
                if (allStats.size() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
        }

        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }

        result.addObject("achievements", achievementService.getAllAchievements());
        result.addObject("myAchievements", myAchievements);
        result.addObject("loggedUser", loggedUser);
        return result;
    }

    @GetMapping("/create")
    public ModelAndView createAchievement(){
        ModelAndView result = new ModelAndView(ACHIEVEMENT_EDIT);
        Achievement achievement = new Achievement();
        result.addObject("achievement", achievement);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewAchievement(@Valid Achievement achievement, BindingResult br){
        ModelAndView result = null;
        if(br.hasErrors()){
            result=new ModelAndView(ACHIEVEMENT_EDIT);
            result.addObject(br.getModel());
        } else {
            if (achievement.getAchievementType().getName().equals("DICE")){
                achievement.setName("Roller "+ achievement.getValue());
                achievement.setDescription("Roll the dice up to "+achievement.getValue()+" point or more");
                achievement.setFileImage("dice");
            } else if (achievement.getAchievementType().getName().equals("FRIENDS")){
                achievement.setName("Friend "+ achievement.getValue());
                achievement.setDescription("Have "+achievement.getValue()+" or more friends");
                achievement.setFileImage("friend");
            } else if (achievement.getAchievementType().getName().equals("GOOSE")){
                achievement.setName("Goose "+achievement.getValue());
                achievement.setDescription("Fall "+achievement.getValue()+" or more times in a Goose tile");
                achievement.setFileImage("goose");
            } else if (achievement.getAchievementType().getName().equals("MATCHES_PLAYED")){
                achievement.setName("Player "+ achievement.getValue());
                achievement.setDescription("Play "+achievement.getValue()+" or more matches");
                achievement.setFileImage("player");
            }

            if(achievementService.getAllAchievements().stream().map(Achievement::getName).collect(Collectors.toList()).contains(achievement.getName())){
                result=new ModelAndView(ACHIEVEMENT_EDIT);
                result.addObject("message", achievement.getName() + " already exists!");
            } else {
                achievementService.save(achievement);
                result=showAchievementListing();
                result.addObject("message", "Achievement saved successfully");
            }  
        }
        return result;
    }
}
