package com.ling1.springmvc.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

<<<<<<< HEAD
=======
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;


@Controller
@RequestMapping("/playerstats")
public class PlayerController {

    public static final String PLAYER_LISTING = "Stats/PlayerListing";
    public static final String PLAYER_RECORD = "Stats/PlayerRecord";
    public static final String GLOBAL_LISTING = "Stats/GlobalListing";
    public static final String GLOBAL_RECORD = "Stats/GlobalRecord";
    public static final String RANKING_LISTING = "Stats/RankingListing";

    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
<<<<<<< HEAD
=======
    @Autowired
    LobbyService lobbyService;
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
 
    @GetMapping("/history")
    ModelAndView playerhistory() {
        ModelAndView result = new ModelAndView(PLAYER_LISTING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(loggedUser.getId());
        User user = userService.getUserById(loggedUser.getId());
        List<Match> matchEachPlayer = new ArrayList<Match>();
        for(PlayerStats ps : allStats){
            matchEachPlayer.add(matchService.findMatchByPlayer(ps.getId()));
        }
<<<<<<< HEAD
=======
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        result.addObject("user", user);
        result.addObject("stats", allStats);
        result.addObject("matches", matchEachPlayer);
        return result;
    }
    @GetMapping("/global/history")
    ModelAndView globalhistory() {
        ModelAndView result = new ModelAndView(GLOBAL_LISTING);
<<<<<<< HEAD
=======
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        List<PlayerStats> allStats = playerService.findAll();
        List<Match> matchEachPlayer = new ArrayList<Match>();
        for(PlayerStats ps : allStats){
            matchEachPlayer.add(matchService.findMatchByPlayer(ps.getId()));
        }
<<<<<<< HEAD
=======
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        result.addObject("stats", allStats);
        result.addObject("matches", matchEachPlayer);
        return result;
    }

    @GetMapping("/ranking")
    ModelAndView ranking(){
<<<<<<< HEAD
=======
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        ModelAndView result = new ModelAndView(RANKING_LISTING);
        List<String> winnersNames = playerService.winnersByName();
        List<Integer> countWins = playerService.numberWins();
        List<String> rankingByNameTurnStuck = playerService.rankingByNameTurnStuck();
        List<Integer> countTurnStuck = playerService.countTurnStuck();
        List<String> rankingByGoose= playerService.rankingByGoose();
        List<Integer> countGoose = playerService.countGoose();
        List<String> rankingByWell= playerService.rankingByWell();
        List<Integer> countWell = playerService.countWell();
        List<String> rankingByLabyrinth= playerService.rankingByLabyrinth();
        List<Integer> countLabyrinth = playerService.countLabyrinth();
        List<String> rankingByPrison= playerService.rankingByPrison();
        List<Integer> countPrison = playerService.countPrison();
        List<String> rankingByDeath= playerService.rankingByDeath();
        List<Integer> countDeath = playerService.countDeath();
        List<String> rankingByInn= playerService.rankingByInn();
        List<Integer> countInn = playerService.countInn();
        result.addObject("winners", winnersNames);
        result.addObject("wins", countWins);
        result.addObject("rankingByNameTurnStuck", rankingByNameTurnStuck);
        result.addObject("countTurnStuck", countTurnStuck);
        result.addObject("rankingByGoose", rankingByGoose);
        result.addObject("countGoose", countGoose);
        result.addObject("rankingByWell", rankingByWell);
        result.addObject("countWell", countWell);
        result.addObject("rankingByLabyrinth", rankingByLabyrinth);
        result.addObject("countLabyrinth", countLabyrinth);
        result.addObject("rankingByPrison", rankingByPrison);
        result.addObject("countPrison", countPrison);
        result.addObject("rankingByDeath", rankingByDeath);
        result.addObject("countDeath", countDeath);
        result.addObject("rankingByInn", rankingByInn);
        result.addObject("countInn", countInn);
<<<<<<< HEAD
=======
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        return result;

    }


    @GetMapping
    ModelAndView playerStats() {
        ModelAndView result = new ModelAndView(PLAYER_RECORD);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(loggedUser.getId());
        if(allStats.isEmpty()) {
            result = new ModelAndView("welcome");
            result.addObject("message", "No stats available");
            return result;
        }
        User user = userService.getUserById(loggedUser.getId());
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
<<<<<<< HEAD
=======
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        result.addObject("user", user);
        result.addObject("stat", total);
        return result; 
    }
    @GetMapping("/global")
    ModelAndView globalStats() {
<<<<<<< HEAD
=======
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        ModelAndView result = new ModelAndView(GLOBAL_RECORD);
        List<PlayerStats> allStats = playerService.findAll();
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
        result.addObject("stat", total);
<<<<<<< HEAD
=======
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
        return result; 
    }


}
