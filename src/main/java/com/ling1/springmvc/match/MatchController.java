package com.ling1.springmvc.match;

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

    @GetMapping("/{matchId}")
    public ModelAndView matchInside(
            @PathVariable("matchId") Integer matchId, HttpServletResponse response) {
        Match currentMatch = matchService.getMatchById(matchId);
        if (currentMatch.getWinner()==null){
        response.addHeader("Refresh", "3");
        }
        ModelAndView result = null;
        if (currentMatch.getWinner() != null) {
            result = new ModelAndView(FINISH_MATCH);
            result.addObject("maxGoose", currentMatch.getPlayerStats().stream()
            .max((p1, p2) -> p1.getNumberOfGooses() - p2.getNumberOfGooses()).get().getNumberOfGooses());
            result.addObject("maxWell", currentMatch.getPlayerStats().stream()
            .max((p1, p2) -> p1.getNumberOfPlayerWells() - p2.getNumberOfPlayerWells()).get().getNumberOfPlayerWells());
            result.addObject("maxLabyrinth", currentMatch.getPlayerStats().stream()
            .max((p1, p2) -> p1.getNumberOfLabyrinths() - p2.getNumberOfLabyrinths()).get().getNumberOfLabyrinths());
            result.addObject("maxPrison", currentMatch.getPlayerStats().stream()
            .max((p1, p2) -> p1.getNumberOfPlayerPrisons() - p2.getNumberOfPlayerPrisons()).get().getNumberOfPlayerPrisons());
            result.addObject("maxDeath", currentMatch.getPlayerStats().stream()
            .max((p1, p2) -> p1.getNumberOfPlayerDeaths() - p2.getNumberOfPlayerDeaths()).get().getNumberOfPlayerDeaths());
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
            Integer ColorPosition = playerService.findColors()
                    .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
            Integer rolledNumber = 1 + (int) Math.floor(Math.random() * NUM_DICES_SIDES);
            Integer newPos = matchToUpdate.getPlayerToPlay().getPosition() + rolledNumber;
            if (newPos == 63) {
                matchToUpdate.setWinner(matchToUpdate.getPlayerToPlay());
            }
            if (newPos > 63) {
                newPos = 63 - (newPos - 63);
            }
            matchToUpdate.getPlayerToPlay().setPosition(newPos);
            matchToUpdate.setLastRoll(rolledNumber);
            matchToUpdate.getPlayerToPlay().setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls() + 1);
            Boolean assignedNextTurn = false;
            while (!assignedNextTurn) {
                if (ColorPosition == 3) {
                    matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                    ColorPosition = 0;
                } else
                    ColorPosition++; // this code could be done way cleaner with modulus ((ColorPosition+1)%3); yet
                                     // to discover why it doesn't work
                PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                    if (ps.getPlayerColor() == colorToTry) {
                        assignedNextTurn = true;
                        matchToUpdate.setPlayerToPlay(ps);
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
