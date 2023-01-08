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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/playerstats")
public class PlayerController {

    public static final String PLAYER_LISTING = "Stats/PlayerListing";
    public static final String PLAYER_RECORD = "Stats/PlayerRecord";
    public static final String OTHERPLAYER_RECORD = "Stats/PlayerRecordUsername";
    public static final String GLOBAL_LISTING = "Stats/GlobalListing";
    public static final String GLOBAL_RECORD = "Stats/GlobalRecord";
    public static final String RANKING_LISTING = "Stats/RankingListing";

    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
    @Autowired
    LobbyService lobbyService;
    @Autowired
    FriendService friendService;

    @GetMapping("/history")
    ModelAndView playerhistory() {
        ModelAndView result = new ModelAndView(PLAYER_LISTING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(loggedUser.getId());
        User user = userService.getUserById(loggedUser.getId());
        List<Match> matchEachPlayer = new ArrayList<Match>();
        for (PlayerStats ps : allStats) {
            matchEachPlayer.add(matchService.findMatchByPlayer(ps.getId()));
        }
        for (Lobby l : lobbyService.getAllLobbies()) {
            if (l.getPlayers().contains(loggedUser))
                result.addObject("currentLobby", l);
        }
        result.addObject("user", user);
        result.addObject("stats", allStats);
        result.addObject("matches", matchEachPlayer);
        result.addObject("loggedUser", loggedUser);
        return result;
    }

    @GetMapping("/global/history")
    ModelAndView globalhistory() {
        ModelAndView result = new ModelAndView(GLOBAL_LISTING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        List<PlayerStats> allStats = playerService.findAll();
        List<Match> matchEachPlayer = new ArrayList<Match>();
        for (PlayerStats ps : allStats) {
            matchEachPlayer.add(matchService.findMatchByPlayer(ps.getId()));
        }
        for (Lobby l : lobbyService.getAllLobbies()) {
            if (l.getPlayers().contains(loggedUser))
                result.addObject("currentLobby", l);
        }
        result.addObject("stats", allStats);
        result.addObject("matches", matchEachPlayer);
        return result;
    }

    @GetMapping("/ranking")
    ModelAndView ranking() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        ModelAndView result = new ModelAndView(RANKING_LISTING);
        List<String> winnersNames = playerService.winnersByName();
        List<Integer> countWins = playerService.numberWins();

        List<String> rankingByNameTurnStuck = playerService.rankingByNameTurnStuck();
        List<Integer> countTurnStuck = playerService.countTurnStuck();
        List<String> rankingByGoose = playerService.rankingByGoose();
        List<Integer> countGoose = playerService.countGoose();
        List<String> rankingByWell = playerService.rankingByWell();
        List<Integer> countWell = playerService.countWell();
        List<String> rankingByLabyrinth = playerService.rankingByLabyrinth();
        List<Integer> countLabyrinth = playerService.countLabyrinth();
        List<String> rankingByPrison = playerService.rankingByPrison();
        List<Integer> countPrison = playerService.countPrison();
        List<String> rankingByDeath = playerService.rankingByDeath();
        List<Integer> countDeath = playerService.countDeath();
        List<String> rankingByInn = playerService.rankingByInn();
        List<Integer> countInn = playerService.countInn();

        List<String> rankingByCheats = playerService.rankingByCheats();
        List<Integer> countCheats = playerService.countCheats();
        List<String> rankingByChipsOut = playerService.rankingByChipsOut();
        List<Integer> countChipsOut = playerService.countChipsOut();
        List<String> rankingByBarriersFormed = playerService.rankingByBarriersFormed();
        List<Integer> countBarriersFormed = playerService.countBarriersFormed();
        List<String> rankingByEndChips = playerService.rankingByEndChips();
        List<Integer> countEndChips = playerService.countEndChips();
        List<String> rankingByBarrierRebound = playerService.rankingByBarrierRebound();
        List<Integer> countBarrierRebound = playerService.countBarrierRebound();
        List<String> rankingByChipsEaten = playerService.rankingByChipsEaten();
        List<Integer> countChipsEaten = playerService.countChipsEaten();

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

        result.addObject("rankingByCheats", rankingByCheats);
        result.addObject("countCheats", countCheats);
        result.addObject("rankingByChipsOut", rankingByChipsOut);
        result.addObject("countChipsOut", countChipsOut);
        result.addObject("rankingByBarriersFormed", rankingByBarriersFormed);
        result.addObject("countBarriersFormed", countBarriersFormed);
        result.addObject("rankingByEndChips", rankingByEndChips);
        result.addObject("countEndChips", countEndChips);
        result.addObject("rankingByBarrierRebound", rankingByBarrierRebound);
        result.addObject("countBarrierRebound", countBarrierRebound);
        result.addObject("rankingByChipsEaten", rankingByChipsEaten);
        result.addObject("countChipsEaten", countChipsEaten);
        for (Lobby l : lobbyService.getAllLobbies()) {
            if (l.getPlayers().contains(loggedUser))
                result.addObject("currentLobby", l);
        }
        return result;

    }

    @GetMapping
    ModelAndView playerStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        return playerStats(loggedUser.getLogin());
    }

    @GetMapping("/global")
    ModelAndView globalStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        ModelAndView result = new ModelAndView(GLOBAL_RECORD);
        List<PlayerStats> allStats = playerService.findAll();
        User user = userService.getUserById(loggedUser.getId());
        PlayerStats total = new PlayerStats();
        Integer numDiceRolls = 0;
        List<PlayerColor> colors = new ArrayList<>();

        Integer tilesAdvanced = 0;
        Integer GoosesStepped = 0;
        Integer WellsFallen = 0;
        Integer LabyrinthLosses = 0;
        Integer PrisonsEntered = 0;
        Integer Deaths = 0;

        Integer Cheats = 0;
        Integer ChipsOut = 0;
        Integer BarriersFormed = 0;
        Integer BarrierRebound = 0;
        Integer EndChips = 0;
        Integer ChipsEaten = 0;

        for (PlayerStats ps : allStats) {
            if (ps.getNumDiceRolls() != null)
                numDiceRolls = numDiceRolls + ps.getNumDiceRolls();
            colors.add(ps.getPlayerColor());

            if (ps.getPosition() != null)
                tilesAdvanced = tilesAdvanced + ps.getPosition();
            if (ps.getNumberOfGooses() != null)
                GoosesStepped = GoosesStepped + ps.getNumberOfGooses();
            if (ps.getNumberOfPlayerWells() != null)
                WellsFallen = WellsFallen + ps.getNumberOfPlayerWells();
            if (ps.getNumberOfLabyrinths() != null)
                LabyrinthLosses = LabyrinthLosses + ps.getNumberOfLabyrinths();
            if (ps.getNumberOfPlayerPrisons() != null)
                PrisonsEntered = PrisonsEntered + ps.getNumberOfPlayerPrisons();
            if (ps.getNumberOfPlayerDeaths() != null)
                Deaths = Deaths + ps.getNumberOfPlayerDeaths();

            if (ps.getNumberOfCheats() != null)
                Cheats = Cheats + ps.getNumberOfCheats();
            if (ps.getNumberOfChipsOut() != null)
                ChipsOut = ChipsOut + ps.getNumberOfChipsOut();
            if (ps.getNumberOfBarriersFormed() != null)
                BarriersFormed = BarriersFormed + ps.getNumberOfBarriersFormed();
            if (ps.getNumberOfBarrierRebound() != null)
                BarrierRebound = BarrierRebound + ps.getNumberOfBarrierRebound();
            if (ps.getNumberOfEndChips() != null)
                EndChips = EndChips + ps.getNumberOfEndChips();
            if (ps.getNumberOfChipsEaten() != null)
                ChipsEaten = ChipsEaten + ps.getNumberOfChipsEaten();

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

        total.setNumberOfCheats(Cheats);
        total.setNumberOfChipsOut(ChipsOut);
        total.setNumberOfBarriersFormed(BarriersFormed);
        total.setNumberOfEndChips(EndChips);
        total.setNumberOfBarrierRebound(BarrierRebound);
        total.setNumberOfChipsEaten(ChipsEaten);
        for (Lobby l : lobbyService.getAllLobbies()) {
            if (l.getPlayers().contains(loggedUser))
                result.addObject("currentLobby", l);
        }
        result.addObject("user", user);
        result.addObject("stat", total);
        result.addObject("loggedUser", loggedUser);

        return result;
    }

    @GetMapping("/{username}")
    ModelAndView playerStats(@PathVariable("username") String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        User profUser = userService.findUsername(username);
        List<PlayerStats> allStats = playerService.giveAllStatsForPlayer(profUser.getId());
        ModelAndView result = null;
        if (loggedUser == profUser) {
            result = new ModelAndView(PLAYER_RECORD);
        } else {
            result = new ModelAndView(OTHERPLAYER_RECORD);
        }
        if (allStats.isEmpty()) {
            result = new ModelAndView("welcome");
            result.addObject("message", "No stats available");
            return result;
        } else if (loggedUser == profUser || friendService.areFriends(loggedUser, profUser)
                || (loggedUser.getRole().equals("admin") && profUser != null)) {
            User user = userService.getUserById(profUser.getId());
            PlayerStats total = new PlayerStats();
            Integer numDiceRolls = 0;
            List<PlayerColor> colors = new ArrayList<>();

            Integer tilesAdvanced = 0;
            Integer GoosesStepped = 0;
            Integer WellsFallen = 0;
            Integer LabyrinthLosses = 0;
            Integer PrisonsEntered = 0;
            Integer Deaths = 0;

            Integer Cheats = 0;
            Integer ChipsOut = 0;
            Integer BarriersFormed = 0;
            Integer BarrierRebound = 0;
            Integer EndChips = 0;
            Integer ChipsEaten = 0;

            for (PlayerStats ps : allStats) {
                if (ps.getNumDiceRolls() != null)
                    numDiceRolls = numDiceRolls + ps.getNumDiceRolls();
                colors.add(ps.getPlayerColor());

                if (ps.getPosition() != null)
                    tilesAdvanced = tilesAdvanced + ps.getPosition();
                if (ps.getNumberOfGooses() != null)
                    GoosesStepped = GoosesStepped + ps.getNumberOfGooses();
                if (ps.getNumberOfPlayerWells() != null)
                    WellsFallen = WellsFallen + ps.getNumberOfPlayerWells();
                if (ps.getNumberOfLabyrinths() != null)
                    LabyrinthLosses = LabyrinthLosses + ps.getNumberOfLabyrinths();
                if (ps.getNumberOfPlayerPrisons() != null)
                    PrisonsEntered = PrisonsEntered + ps.getNumberOfPlayerPrisons();
                if (ps.getNumberOfPlayerDeaths() != null)
                    Deaths = Deaths + ps.getNumberOfPlayerDeaths();

                if (ps.getNumberOfCheats() != null)
                    Cheats = Cheats + ps.getNumberOfCheats();
                if (ps.getNumberOfChipsOut() != null)
                    ChipsOut = ChipsOut + ps.getNumberOfChipsOut();
                if (ps.getNumberOfBarriersFormed() != null)
                    BarriersFormed = BarriersFormed + ps.getNumberOfBarriersFormed();
                if (ps.getNumberOfBarrierRebound() != null)
                    BarrierRebound = BarrierRebound + ps.getNumberOfBarrierRebound();
                if (ps.getNumberOfEndChips() != null)
                    EndChips = EndChips + ps.getNumberOfEndChips();
                if (ps.getNumberOfChipsEaten() != null)
                    ChipsEaten = ChipsEaten + ps.getNumberOfChipsEaten();

            }
            total.setNumDiceRolls(numDiceRolls);
            total.setPlayerColor(
                    colors.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue()).get().getKey());

            total.setPosition(tilesAdvanced);
            total.setNumberOfGooses(GoosesStepped);
            total.setNumberOfPlayerWells(WellsFallen);
            total.setNumberOfLabyrinths(LabyrinthLosses);
            total.setNumberOfPlayerPrisons(PrisonsEntered);
            total.setNumberOfPlayerDeaths(Deaths);

            total.setNumberOfCheats(Cheats);
            total.setNumberOfChipsOut(ChipsOut);
            total.setNumberOfBarriersFormed(BarriersFormed);
            total.setNumberOfEndChips(EndChips);
            total.setNumberOfBarrierRebound(BarrierRebound);
            total.setNumberOfChipsEaten(ChipsEaten);
            for (Lobby l : lobbyService.getAllLobbies()) {
                if (l.getPlayers().contains(loggedUser))
                    result.addObject("currentLobby", l);
            }
            result.addObject("user", user);
            result.addObject("stat", total);
            result.addObject("loggedUser", profUser);
        } else {
            result = new ModelAndView("redirect:/");
            result.addObject("message", "You cannot access these stats!");
        }
        return result;
    }

}
