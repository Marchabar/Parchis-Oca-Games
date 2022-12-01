package com.ling1.springmvc.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.ocatile.OcaTile;
import com.ling1.springmvc.ocatile.OcaTileService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/matches")
public class MatchController {

    public static int NUM_DICES_SIDES = 6;

    public static final String INSIDE_MATCH = "Matches/InsideMatch";
    public static final String FINISH_MATCH = "Matches/FinishedMatch";

    @Autowired
    LobbyService lobbyService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;
    @Autowired
    PlayerService playerService;
    @Autowired
    OcaTileService ocaTileService;

    @GetMapping("/{matchId}")
    public ModelAndView matchInside(
            @PathVariable("matchId") Integer matchId, HttpServletResponse response) {
        Match currentMatch = matchService.getMatchById(matchId);
        if (currentMatch.getWinner() == null) {
            response.addHeader("Refresh", "3");
        }
        ModelAndView result = null;
        if (currentMatch.getWinner() != null) {
            result = new ModelAndView(FINISH_MATCH);
        } else {
            result = new ModelAndView(INSIDE_MATCH);
        }
 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        result.addObject("loggedUser", loggedUser);
        result.addObject("match", currentMatch);
        PlayerStats previousPlayer = null;

        Integer ColorPosition = playerService.findColors().indexOf(currentMatch.getPlayerToPlay().getPlayerColor());
        Boolean prevPChosen = false;
        while (!prevPChosen) {
            if (ColorPosition == 0) {
                ColorPosition = 3;
            } else
                ColorPosition--;
            PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
            for (PlayerStats ps : currentMatch.getPlayerStats()) {
                if (ps.getPlayerColor() == colorToTry) {
                    previousPlayer = ps;
                    prevPChosen = true;
                }
            }
        }
        result.addObject("prevPlayer", previousPlayer);
        return result;
    }

    @GetMapping("/{matchId}/advance")
    public ModelAndView matchAdvance(
            @PathVariable("matchId") Integer matchId) {
        Match matchToUpdate = matchService.getMatchById(matchId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser() && matchToUpdate.getWinner() == null) {
            Integer rolledNumber = 1 + (int) Math.floor(Math.random() * NUM_DICES_SIDES);
            Integer newPos = matchToUpdate.getPlayerToPlay().getPosition() + rolledNumber;
            if (newPos > 63) {
                newPos = 63 - (newPos - 63);
            }
            matchToUpdate.getPlayerToPlay()
                    .setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls() + 1);
            Boolean rollAgain = false;
            switch (ocaTileService.findTileTypeByPosition(newPos).getType().getName()) {
                case "NORMAL":
                    break;
                case "OCA":
                    newPos = ocaTileService.nextOca(newPos);
                    rollAgain = true;
                    matchToUpdate.getPlayerToPlay()
                            .setNumberOfGooses(matchToUpdate.getPlayerToPlay().getNumberOfGooses() + 1);
                    break;
                case "BRIDGE":
                    newPos = ocaTileService.otherBridge(newPos);
                    rollAgain = true;
                    break;
                case "INN":
                    matchToUpdate.getPlayerToPlay()
                            .setTurnsStuck(2);
                    matchToUpdate.getPlayerToPlay().setNumberOfInns(matchToUpdate.getPlayerToPlay().getNumberOfInns() + 1);
                    break;
                case "WELL":
                    matchToUpdate.getPlayerToPlay()
                            .setTurnsStuck(3);
                            matchToUpdate.getPlayerToPlay().setNumberOfPlayerWells(matchToUpdate.getPlayerToPlay().getNumberOfPlayerWells() + 1);
                    break;
                case "DICE":
                    newPos = ocaTileService.otherDice(newPos);
                    rollAgain = true;
                    break;
                case "LABYRINTH":
                    newPos = 30;
                    matchToUpdate.getPlayerToPlay().setNumberOfLabyrinths(matchToUpdate.getPlayerToPlay().getNumberOfLabyrinths() + 1);
                    break;
                case "PRISON":
                    matchToUpdate.getPlayerToPlay()
                            .setTurnsStuck(4);
                            matchToUpdate.getPlayerToPlay().setNumberOfPlayerPrisons(matchToUpdate.getPlayerToPlay().getNumberOfPlayerPrisons() + 1);
                    break;
                case "DEATH":
                    newPos = 1;
                    matchToUpdate.getPlayerToPlay().setNumberOfPlayerDeaths(matchToUpdate.getPlayerToPlay().getNumberOfPlayerDeaths() + 1);
                    break;
                case "END":
                    matchToUpdate.setWinner(matchToUpdate.getPlayerToPlay());
                    break;
            }
            matchToUpdate.setLastRoll(rolledNumber);
            Integer ColorPosition = playerService.findColors()
                    .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
            Boolean assignedNextTurn = false;
            matchToUpdate.getPlayerToPlay().setPosition(newPos);
            if (!rollAgain) {
                while (!assignedNextTurn) {
                    if (ColorPosition == 3) {
                        matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                        ColorPosition = 0;
                    } else
                        ColorPosition++; // this code could be done way cleaner with modulus ((ColorPosition+1)%3);
                                         // yet to discover why it doesn't work
                    PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                    for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                        if (ps.getPlayerColor() == colorToTry) {
                            if (ps.getTurnsStuck() != 0) {
                                ps.setTurnsStuck(ps.getTurnsStuck() - 1);
                                matchToUpdate.setLastRoll(-1);
                                ColorPosition++;
                            } else {
                                assignedNextTurn = true;
                                matchToUpdate.setPlayerToPlay(ps);
                            }
                        }
                    }
                }
            }
            matchService.save(matchToUpdate);
            playerService.save(matchToUpdate.getPlayerToPlay());
            ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
            return result;
        }
        ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
        result.addObject("message", "It's not your turn");
        return result;

    }

}
