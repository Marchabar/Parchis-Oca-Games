package com.ling1.springmvc.achievements;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    public static final String PLAYERACHIEVEMENTS_LISTING="Achievements/PlayerAchievementsListing";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        return showAchievementListing(loggedUser.getLogin());
    }
    @GetMapping("/{username}")
    public ModelAndView showAchievementListing(@PathVariable("username") String username){
        ModelAndView result = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        User profUser = userService.findUsername(username);
        if (loggedUser == profUser) {
            result = new ModelAndView(ACHIEVEMENTS_LISTING);
        } else {
            result = new ModelAndView(PLAYERACHIEVEMENTS_LISTING);
        }
        if (loggedUser == profUser || friendService.areFriends(loggedUser, profUser)
        || (loggedUser.getRole().equals("admin") && profUser != null)) {
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(profUser.getId());
        List<Achievement> myAchievements = new ArrayList<>();
        PlayerStats total = new PlayerStats();
        Integer numDiceRolls =0;
        List<PlayerColor> colors = new ArrayList<>();
        Integer tilesAdvanced =0;
        Integer goosesStepped = 0;
        Integer wellsFallen =0;
        Integer labyrinthLosses =0;
        Integer prisonsEntered =0;
        Integer deaths =0;
        if(allStats.isEmpty()){
            total.setNumDiceRolls(0);
            total.setPlayerColor(null);
            total.setPosition(0);
            total.setNumberOfGooses(0);
            total.setNumberOfPlayerWells(0);
            total.setNumberOfLabyrinths(0);
            total.setNumberOfPlayerPrisons(0);
            total.setNumberOfPlayerDeaths(0);
        } else {
        for (PlayerStats ps : allStats){
            if (ps.getNumDiceRolls()!=null) 
                numDiceRolls=numDiceRolls+ps.getNumDiceRolls();
            colors.add(ps.getPlayerColor());
            if (ps.getPosition()!=null) 
                tilesAdvanced=tilesAdvanced+ps.getPosition();
            if (ps.getNumberOfGooses()!=null) 
                goosesStepped=goosesStepped+ps.getNumberOfGooses();
            if (ps.getNumberOfPlayerWells()!=null) 
                wellsFallen=wellsFallen+ps.getNumberOfPlayerWells();
            if (ps.getNumberOfLabyrinths()!=null) 
                labyrinthLosses=labyrinthLosses+ps.getNumberOfLabyrinths();
            if (ps.getNumberOfPlayerPrisons()!=null) 
                prisonsEntered=prisonsEntered+ps.getNumberOfPlayerPrisons();
            if (ps.getNumberOfPlayerDeaths()!=null) 
                deaths=deaths+ps.getNumberOfPlayerDeaths();
        }
        total.setNumDiceRolls(numDiceRolls);
        total.setPlayerColor(colors.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
      .entrySet()
      .stream()
      .max(Map.Entry.comparingByValue()).get().getKey());
        total.setPosition(tilesAdvanced);
        total.setNumberOfGooses(goosesStepped);
        total.setNumberOfPlayerWells(wellsFallen);
        total.setNumberOfLabyrinths(labyrinthLosses);
        total.setNumberOfPlayerPrisons(prisonsEntered);
        total.setNumberOfPlayerDeaths(deaths);
    }
        for (Achievement a : achievementService.getAllAchievements()){
            if (a.getAchievementType().getName().equals("DICE")){
                if (total.getNumDiceRolls() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("FRIENDS")){
                if (friendService.getMyFriends(profUser).size() >= a.getValue()){
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
            if (a.getAchievementType().getName().equals("WINS")){
                if (playerService.winsUser(loggedUser.getLogin()) >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("ADVANCE")){
                if (total.getPosition() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("WELL")){
                if (total.getNumberOfPlayerWells() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("MAZE")){
                if (total.getNumberOfLabyrinths() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("PRISON")){
                if (total.getNumberOfPlayerPrisons() >= a.getValue()){
                    myAchievements.add(a);
                }
            }
            if (a.getAchievementType().getName().equals("DEATH")){
                if (total.getNumberOfPlayerDeaths() >= a.getValue()){
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
        result.addObject("profUser", profUser);

    } else {
        result = new ModelAndView("redirect:/");
        result.addObject("message", "You cannot access these achievements!");
    }
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
            } else if (achievement.getAchievementType().getName().equals("WINS")){
                achievement.setName("Winner "+ achievement.getValue());
                achievement.setDescription("Win "+achievement.getValue()+" or more matches");
                achievement.setFileImage("crown");
            } else if (achievement.getAchievementType().getName().equals("ADVANCE")){
                achievement.setName("Advance "+ achievement.getValue());
                achievement.setDescription("Advance "+achievement.getValue()+" or more tiles");
                achievement.setFileImage("advance");
            } else if (achievement.getAchievementType().getName().equals("WELL")){
                achievement.setName("Well "+ achievement.getValue());
                achievement.setDescription("Fall "+achievement.getValue()+" or more times in the well");
                achievement.setFileImage("well");
            } else if (achievement.getAchievementType().getName().equals("MAZE")){
                achievement.setName("Maze "+ achievement.getValue());
                achievement.setDescription("Get lost "+achievement.getValue()+" or more times in the maze");
                achievement.setFileImage("maze");
            } else if (achievement.getAchievementType().getName().equals("PRISON")){
                achievement.setName("Prison "+ achievement.getValue());
                achievement.setDescription("Go to prison "+achievement.getValue()+" or more times");
                achievement.setFileImage("prison");
            } else if (achievement.getAchievementType().getName().equals("DEATH")){
                achievement.setName("Death "+ achievement.getValue());
                achievement.setDescription("Die "+achievement.getValue()+" or more times");
                achievement.setFileImage("death");
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
