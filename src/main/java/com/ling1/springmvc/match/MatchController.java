package com.ling1.springmvc.match;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.chat.MessageChat;
import com.ling1.springmvc.chat.MessageChatService;
import com.ling1.springmvc.chip.Chip;
import com.ling1.springmvc.chip.ChipService;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.ocatile.OcaTile;
import com.ling1.springmvc.ocatile.OcaTileService;
import com.ling1.springmvc.parchistile.ParchisTileService;
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
    public static final String CHOOSE_CHIP = "Matches/ChooseChip";

    public static final String MATCHMESSAGES_LISTING = "Chats/MessagesListing";
    public static final String MESSAGE_EDIT = "Chats/EditMessage";
    public static final List<Integer> safeParchisTiles = List.of(5, 12, 17, 22, 29, 34, 39, 46, 51, 56, 63, 68, 100);

    @Autowired
    LobbyService lobbyService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;
    @Autowired
    PlayerService playerService;
    @Autowired
    MessageChatService messageChatService;
    @Autowired
    OcaTileService ocaTileService;
    @Autowired
    ParchisTileService parchisTileService;
    @Autowired
    FriendService friendService;
    @Autowired
    ChipService chipService;

    @GetMapping("/{matchId}")
    public ModelAndView matchInside(
            @PathVariable("matchId") Integer matchId, HttpServletResponse response) {
        Match currentMatch = matchService.getMatchById(matchId);
        if (currentMatch == null) {
            ModelAndView result = new ModelAndView("redirect:/");
            result.addObject("message",
                    "The match you tried to join is not active or finished");
            return result;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        // In case the PlayerToPlay leaves the match.
        if (!currentMatch.getPlayerStats().contains(currentMatch.getPlayerToPlay())) {
            Boolean assignedNextTurn = false;
            Integer ColorPosition = playerService.findColors()
                    .indexOf(currentMatch.getPlayerToPlay().getPlayerColor());
            while (!assignedNextTurn) {
                if (ColorPosition == 3) {
                    currentMatch.setNumTurns(currentMatch.getNumTurns() + 1);
                    ColorPosition = 0;
                } else
                    ColorPosition++;
                PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                for (PlayerStats ps : currentMatch.getPlayerStats()) {
                    if (ps.getPlayerColor() == colorToTry) {
                        assignedNextTurn = true;
                        currentMatch.setPlayerToPlay(ps);
                    }
                }
            }
        }
        Boolean insideMatch = false;
        for (PlayerStats ps : currentMatch.getPlayerStats()) {
            if (ps.getUser() == loggedUser)
                insideMatch = true;
        }
        for (PlayerStats ps : currentMatch.getPlayerStats()) {
            if ((!friendService.areFriends(loggedUser, ps.getUser()) && !insideMatch)
                    && currentMatch.getWinner() == null) {
                ModelAndView result = new ModelAndView("redirect:/");
                result.addObject("message",
                        "You are not friend of " + ps.getUser().getLogin() + " and cannot spectate the match");
                return result;
            }
        }
        // Set winner if all other players leave
        if (currentMatch.getPlayerStats().size() == 1) {
            for (PlayerStats ps : currentMatch.getPlayerStats()) {
                if (ps.getUser() == loggedUser) {
                    currentMatch.setWinner(ps);
                    matchService.save(currentMatch);
                }
            }
        }

        ModelAndView result = null;
        if (currentMatch.getWinner() != null) {
            result = new ModelAndView(FINISH_MATCH);
            if (currentMatch.getGame() == lobbyService.oca()) {
                result.addObject("maxGoose", currentMatch.getPlayerStats().stream()
                        .max((p1, p2) -> p1.getNumberOfGooses() - p2.getNumberOfGooses()).get().getNumberOfGooses());
                result.addObject("maxWell", currentMatch.getPlayerStats().stream()
                        .max((p1, p2) -> p1.getNumberOfPlayerWells() - p2.getNumberOfPlayerWells()).get()
                        .getNumberOfPlayerWells());
                result.addObject("maxLabyrinth", currentMatch.getPlayerStats().stream()
                        .max((p1, p2) -> p1.getNumberOfLabyrinths() - p2.getNumberOfLabyrinths()).get()
                        .getNumberOfLabyrinths());
                result.addObject("maxPrison", currentMatch.getPlayerStats().stream()
                        .max((p1, p2) -> p1.getNumberOfPlayerPrisons() - p2.getNumberOfPlayerPrisons()).get()
                        .getNumberOfPlayerPrisons());
                result.addObject("maxDeath", currentMatch.getPlayerStats().stream()
                        .max((p1, p2) -> p1.getNumberOfPlayerDeaths() - p2.getNumberOfPlayerDeaths()).get()
                        .getNumberOfPlayerDeaths());
            }
        } else {
            result = new ModelAndView(INSIDE_MATCH);
            response.addHeader("Refresh", "2");
        }

        PlayerStats previousPlayer = null;
        Boolean prevPChosen = false;
        // If the previous player landed in an "extra roll tile" there is no need to
        // find the previous player, as it will be himself again.
        // The info related to the jump between tiles is added as well.
        if (currentMatch.getGame() == lobbyService.oca()) {
            if (currentMatch.getPlayerToPlay().getPosition() != 0) {
                OcaTile currentTile = ocaTileService
                        .findTileTypeByPosition(currentMatch.getPlayerToPlay().getPosition());
                if (currentTile.getType()
                        .getName().equals("OCA")
                        || currentTile.getType()
                                .getName().equals("BRIDGE")
                        || currentTile.getType()
                                .getName().equals("DICE")) {
                    previousPlayer = currentMatch.getPlayerToPlay();
                    if (currentTile.getType()
                            .getName().equals("OCA") && currentTile.getId() != 1) {
                        result.addObject("prevOca", ocaTileService.allOcas().get(ocaTileService.allOcas()
                                .indexOf(currentTile) - 1));
                    }
                    if (currentTile.getType()
                            .getName().equals("BRIDGE")) {
                        result.addObject("otherBridge", ocaTileService.otherBridge(currentTile.getId()));
                    }
                    if (currentTile.getType()
                            .getName().equals("DICE")) {
                        result.addObject("otherDice", ocaTileService.otherDice(currentTile.getId()));
                    }
                    prevPChosen = true;
                }
            }
        }
        // As the color order is always the same, from the color of the player that has
        // to play we can find the previous color in the match and therefore the
        // previous player. This process is skipped if the player landed on a "extra
        // roll tile".
        Integer ColorPosition = playerService.findColors().indexOf(currentMatch.getPlayerToPlay().getPlayerColor());
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
        List<MessageChat> allMessages = messageChatService.findByMatch(matchId);
        Collections.reverse(allMessages);
        List<User> usersInside = new ArrayList<>();
        for (PlayerStats ps :matchService.getMatchById(matchId).getPlayerStats()){
            usersInside.add(ps.getUser());
        }
        result.addObject("usersInside", usersInside);
        result.addObject("messagesChat", allMessages);
        result.addObject("loggedUser", loggedUser);
        result.addObject("match", currentMatch);
        result.addObject("prevPlayer", previousPlayer);
        result.addObject("allTiles", ocaTileService.getAllTiles());
        result.addObject("allOcas", ocaTileService.allOcas());
        result.addObject("allParchisTiles", parchisTileService.getAllTiles());
        return result;
    }

    @GetMapping("/{matchId}/advanceOca")
    public ModelAndView ocaAdvance(
            @PathVariable("matchId") Integer matchId) {
        Match matchToUpdate = matchService.getMatchById(matchId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser() && matchToUpdate.getWinner() == null) {
            // If the current player is stuck a turn is removed from the counter, the last
            // roll is set to -1 for jsp visualization.
            if (matchToUpdate.getPlayerToPlay().getTurnsStuck() != 0) {
                matchToUpdate.getPlayerToPlay().setTurnsStuck(matchToUpdate.getPlayerToPlay().getTurnsStuck() - 1);
                matchToUpdate.setLastRoll(-1);
                playerService.save(matchToUpdate.getPlayerToPlay());
                // Finding next player, similar to finding previous player.
                Integer ColorPosition = playerService.findColors()
                        .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
                Boolean assignedNextTurn = false;
                while (!assignedNextTurn) {
                    if (ColorPosition == 3) {
                        matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                        ColorPosition = 0;
                    } else
                        ColorPosition++;
                    PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                    for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                        if (ps.getPlayerColor() == colorToTry) {
                            assignedNextTurn = true;
                            matchToUpdate.setPlayerToPlay(ps);
                        }
                    }
                }
                // If the current player is not stuck, normal dice rolling is done.
            } else {
                Integer rolledNumber = 1 + (int) Math.floor(Math.random() * NUM_DICES_SIDES);
                Integer newPos = matchToUpdate.getPlayerToPlay().getPosition() + rolledNumber;
                // If you go over the final tile, go back as the same extra tiles.
                if (newPos > ocaTileService.getAllTiles().size()) {
                    newPos = ocaTileService.getAllTiles().size() - (newPos - ocaTileService.getAllTiles().size());
                }
                matchToUpdate.getPlayerToPlay()
                        .setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls() + 1);
                Boolean rollAgain = false;
                Boolean fellInLabyrinth = false;
                switch (ocaTileService.findTileTypeByPosition(newPos).getType().getName()) {
                    case "NORMAL":
                        break;
                    case "OCA":
                        newPos = ocaTileService.nextOca(newPos);
                        // As the final tile is also considered an OCA, if you land on the previous OCA
                        // you should win.
                        if (ocaTileService.findTileTypeByPosition(newPos).getType().getName().equals("END")) {
                            matchToUpdate.getPlayerToPlay().setPosition(newPos);
                            matchToUpdate.getPlayerToPlay()
                                    .setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls() + 1);
                            matchToUpdate.setWinner(matchToUpdate.getPlayerToPlay());
                            matchService.save(matchToUpdate);
                            playerService.save(matchToUpdate.getPlayerToPlay());
                            return new ModelAndView("redirect:/matches/" + matchId);
                        }
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
                        matchToUpdate.getPlayerToPlay()
                                .setNumberOfInns(matchToUpdate.getPlayerToPlay().getNumberOfInns() + 1);
                        break;
                    case "WELL":
                        matchToUpdate.getPlayerToPlay()
                                .setTurnsStuck(3);
                        matchToUpdate.getPlayerToPlay()
                                .setNumberOfPlayerWells(matchToUpdate.getPlayerToPlay().getNumberOfPlayerWells() + 1);
                        break;
                    case "DICE":
                        newPos = ocaTileService.otherDice(newPos);
                        rollAgain = true;
                        break;
                    case "LABYRINTH":
                        newPos = 30;
                        fellInLabyrinth = true;
                        // From 7-12 is understood as "just fell in a labyrinth" for jsp visualization.
                        matchToUpdate.setLastRoll(rolledNumber + 6);
                        matchToUpdate.getPlayerToPlay()
                                .setNumberOfLabyrinths(matchToUpdate.getPlayerToPlay().getNumberOfLabyrinths() + 1);
                        break;
                    case "PRISON":
                        matchToUpdate.getPlayerToPlay()
                                .setTurnsStuck(4);
                        matchToUpdate.getPlayerToPlay()
                                .setNumberOfPlayerPrisons(
                                        matchToUpdate.getPlayerToPlay().getNumberOfPlayerPrisons() + 1);
                        break;
                    case "DEATH":
                        newPos = 1;
                        matchToUpdate.getPlayerToPlay()
                                .setNumberOfPlayerDeaths(matchToUpdate.getPlayerToPlay().getNumberOfPlayerDeaths() + 1);
                        break;
                    case "END":
                        matchToUpdate.getPlayerToPlay().setPosition(newPos);
                        matchToUpdate.getPlayerToPlay()
                                .setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls() + 1);
                        matchToUpdate.setWinner(matchToUpdate.getPlayerToPlay());
                        matchService.save(matchToUpdate);
                        playerService.save(matchToUpdate.getPlayerToPlay());
                        return new ModelAndView("redirect:/matches/" + matchId);
                }
                if (!fellInLabyrinth) {
                    matchToUpdate.setLastRoll(rolledNumber);
                }
                matchToUpdate.getPlayerToPlay().setPosition(newPos);

                Integer ColorPosition = playerService.findColors()
                        .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
                Boolean assignedNextTurn = false;
                // Finding next player, if the player landed on a oca this process is skipped
                // (he is the playerToPlay again).
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

    @GetMapping("{matchId}/advanceParchis")
    public ModelAndView parchisAdvance(@PathVariable("matchId") Integer matchId) {
        Match matchToUpdate = matchService.getMatchById(matchId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser() && matchToUpdate.getWinner() == null) {
            // Random number between 1-6 is set as lastRoll
            Integer rolledNumber = 1 + (int) Math.floor(Math.random() * NUM_DICES_SIDES);
            matchToUpdate.getPlayerToPlay().setNumDiceRolls(1 + matchToUpdate.getPlayerToPlay().getNumDiceRolls());
            matchToUpdate.setLastRoll(rolledNumber);
            // Cheater controller. -100 as lastRoll is understood in the next controller as
            // "set this chip back home"
            if (rolledNumber == 6)
                matchToUpdate.setCheaterCounter(matchToUpdate.getCheaterCounter() + 1);
            if (matchToUpdate.getCheaterCounter() == 3) {
                matchToUpdate.setLastRoll(-100);
                matchToUpdate.setCheaterCounter(0);
                matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin() + " is a cheater!");
            } else {
                // Rolling should only be seen in the constant "player rolled x" line.
                matchToUpdate.setEvent(null);
            }
            matchService.save(matchToUpdate);
            ModelAndView result = new ModelAndView("redirect:/matches/" + matchId + "/chooseChip");
            return result;
        }
        ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
        result.addObject("message", "It's not your turn");
        return result;
    }

    @GetMapping("{matchId}/chooseChip")
    public ModelAndView parchisChip(@PathVariable("matchId") Integer matchId) {
        Match matchToUpdate = matchService.getMatchById(matchId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser() && matchToUpdate.getWinner() == null) {
            // If a 5 is rolled, the turn is automatically set to take a chip out and end
            // turn.
            if (matchToUpdate.getLastRoll() == 5) {
                Integer ColorPosition = playerService.findColors()
                        .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
                // The order of the table should be RED -> BLUE -> GREEN -> YELLOW for this to
                // work.
                Integer startingPoint = 0;
                startingPoint = 5 + 17*ColorPosition;
                // Never should 3 chips share the same relativePosition.
                if (chipService.findChipInRel(startingPoint, matchToUpdate).size() != 2) {
                    for (Chip c : matchToUpdate.getPlayerToPlay().getChips()) {
                        if (c.getRelativePosition() == 0) {
                            c.setRelativePosition(startingPoint);
                            matchToUpdate.setEvent(
                                    matchToUpdate.getPlayerToPlay().getUser().getLogin() + " took out a chip!");
                            chipService.save(c);
                            // Assign next turn.
                            Boolean assignedNextTurn = false;
                            while (!assignedNextTurn) {
                                if (ColorPosition == 3) {
                                    matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                                    ColorPosition = 0;
                                } else
                                    ColorPosition++;
                                PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                                for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                                    if (ps.getPlayerColor() == colorToTry) {
                                        assignedNextTurn = true;
                                        matchToUpdate.setPlayerToPlay(ps);
                                        matchToUpdate.setCheaterCounter(0);
                                    }
                                }
                            }
                            matchService.save(matchToUpdate);
                            return new ModelAndView("redirect:/matches/" + matchId);

                        }
                    }
                }
            }
            // If no chip was taken out:
            List<Chip> yourChips = matchToUpdate.getPlayerToPlay().getChips();
            // Chips out in the table and not finished.
            List<Chip> availableChips = yourChips.stream()
                    .filter(x -> x.getRelativePosition() != 0 && x.getAbsolutePosition() != 71).toList();
            if (availableChips.isEmpty()) {
                if (matchToUpdate.getLastRoll() != 6) {
                    matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin()
                            + "has no chips to play! Turn skipped!");
                    Integer ColorPosition = playerService.findColors()
                            .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
                    Boolean assignedNextTurn = false;
                    while (!assignedNextTurn) {
                        if (ColorPosition == 3) {
                            matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                            ColorPosition = 0;
                        } else
                            ColorPosition++;
                        PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                        for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                            if (ps.getPlayerColor() == colorToTry) {
                                assignedNextTurn = true;
                                matchToUpdate.setPlayerToPlay(ps);
                                matchToUpdate.setCheaterCounter(0);
                            }
                        }
                    }

                    matchService.save(matchToUpdate);
                    ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
                    return result;
                } else {
                    // 6 but no chips in table.
                    matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin()
                            + "has no chips to play, but can roll again!");
                    matchService.save(matchToUpdate);
                    ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
                    return result;
                }
            } else {
                ModelAndView result = new ModelAndView(CHOOSE_CHIP);
                List<MessageChat> allMessages = messageChatService.findByMatch(matchId);
                Collections.reverse(allMessages);
                List<User> usersInside = new ArrayList<>();
                for (PlayerStats ps :matchService.getMatchById(matchId).getPlayerStats()){
                    usersInside.add(ps.getUser());
                }
                result.addObject("usersInside", usersInside);
                result.addObject("messagesChat", allMessages);
                result.addObject("allParchisTiles", parchisTileService.getAllTiles());
                result.addObject("loggedUser", loggedUser);
                result.addObject("match", matchToUpdate);
                return result;
            }
        }
        ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
        result.addObject("message", "It's not your turn");
        return result;
    }

    @GetMapping("{matchId}/chooseChip/{chipId}")
    public ModelAndView chosenChip(@PathVariable("matchId") Integer matchId, @PathVariable("chipId") Integer chipId) {
        Match matchToUpdate = matchService.getMatchById(matchId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        Chip selectedChip = chipService.findById(chipId);

        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser()) {
            if (!matchToUpdate.getPlayerToPlay().getChips().contains(selectedChip)) {
                ModelAndView result = new ModelAndView("redirect:/matches/" + matchId + "/chooseChip");
                result.addObject("message", "It's not your chip");
                return result;
            } else {
                if (selectedChip.getAbsolutePosition()==72){
                    ModelAndView result = new ModelAndView("redirect:/matches/" + matchId + "/chooseChip");
                result.addObject("message", "This chip is already won");
                return result;
                }
                // Later used for event controller.
                Boolean rebound = false;
                // If cheater, you can choose the chip to take home. The jsp for chooseChip MUST
                // display that the chip selected will be set back to home.
                if (matchToUpdate.getLastRoll() == -100) {
                    selectedChip.setAbsolutePosition(0);
                    selectedChip.setRelativePosition(0);
                    matchToUpdate.setEvent(
                            matchToUpdate.getPlayerToPlay().getUser().getLogin() + "lost a chip because he cheats!");
                } else {
                    // The checker for barriers is done in chipService. "Jump" is the correct value
                    // advanced after checking barriers .After obtaining the "jump" to add,
                    // relativePosition and absolutePosition are set.
                    Integer jump = chipService.barrierRebound(matchToUpdate.getLastRoll(), matchToUpdate, selectedChip);
                    // Not expected tiles advanced? Rebound!
                    if (jump != matchToUpdate.getLastRoll())
                        rebound = true;
                    // Similar to Oca End rebounding.
                    Integer absPos = selectedChip.getAbsolutePosition() + jump;
                    if (absPos > 71) {
                        absPos = 71 - (absPos - 71);
                    }
                    selectedChip.setAbsolutePosition(absPos);
                    // If on central bridge, relativePosition is not used. chipColor and
                    // absolutePosition must be used.
                    if (absPos > 63) {
                        selectedChip.setRelativePosition(100);
                        matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin() + " is "
                                + (71 - absPos) + "tiles from winning a chip!");
                    } else {
                        // Not exactly modulus 69 (%69), dumb conditional must be used.
                        Integer relPos = selectedChip.getRelativePosition() + jump;
                        if (relPos > 68) {
                            relPos = relPos - 68;
                        }
                        selectedChip.setRelativePosition(relPos);
                        matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin()
                                + " placed a chip at " + relPos);
                        // Event checking. The barrier is formed if there are 2 chips in x tile and they
                        // are both the same color.
                        if (chipService.findChipInRel(relPos, matchToUpdate).size() == 2
                                && chipService.findChipInRel(relPos, matchToUpdate).get(0).getChipColor() == chipService
                                        .findChipInRel(relPos, matchToUpdate).get(1).getChipColor())
                            matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin()
                                    + " formed a barrier at tile " + relPos);

                    }
                }
                chipService.save(selectedChip);
                // Chip info saved, time to look if something interesting happened!
                if (selectedChip.getAbsolutePosition() == 71) {
                    matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin()
                            + " got a chip to the end and can move 10 tiles any other chip!");
                    matchToUpdate.setLastRoll(10);
                    matchService.save(matchToUpdate);
                    return new ModelAndView("redirect:/matches/" + matchId + "/chooseChip");
                }
                // As barrier checking is done before, there will never be 3 chips at the same
                // tile. We can mow down any chip that is not ours if it is not a safe space.
                Boolean chipEaten = false;
                PlayerColor colorEaten = null;
                if (!safeParchisTiles.contains(selectedChip.getRelativePosition())) {
                    for (Chip c : chipService.findChipInRel(selectedChip.getRelativePosition(), matchToUpdate)) {
                        if (!matchToUpdate.getPlayerToPlay().getChips().contains(c)) {
                            c.setRelativePosition(0);
                            c.setAbsolutePosition(0);
                            chipService.save(c);
                            chipEaten = true;
                            colorEaten = c.getChipColor();
                        }
                    }
                    // Checkers for each 3 special cases: rebound, eat, or rebound THEN eat!
                    if (rebound)
                        matchToUpdate.setEvent("Ouch! " + matchToUpdate.getPlayerToPlay().getUser().getLogin()
                                + "found a barrier and got stuck at " + selectedChip.getRelativePosition());
                    if (chipEaten) {
                        if (rebound)
                            matchToUpdate.setEvent("No way! " + matchToUpdate.getPlayerToPlay().getUser().getLogin()
                                    + " found a barrier and ate a " + colorEaten + " chip! What are the odds!");
                        else
                            matchToUpdate.setEvent(matchToUpdate.getPlayerToPlay().getUser().getLogin() + "ate a "
                                    + colorEaten + "chip!");
                        matchToUpdate.setLastRoll(20);
                        matchService.save(matchToUpdate);
                        return new ModelAndView("redirect:/matches/" + matchId + "/chooseChip");
                    }
                }
                // If you rolled a 6 and no special event has happened, roll again! The special
                // cases that would not allow this to happen would be cheating, eating or
                // finishing chip.
                if (matchToUpdate.getLastRoll() != 6) {
                    Integer ColorPosition = playerService.findColors()
                            .indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
                    Boolean assignedNextTurn = false;
                    while (!assignedNextTurn) {
                        if (ColorPosition == 3) {
                            matchToUpdate.setNumTurns(matchToUpdate.getNumTurns() + 1);
                            ColorPosition = 0;
                        } else
                            ColorPosition++;
                        PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                        for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                            if (ps.getPlayerColor() == colorToTry) {
                                assignedNextTurn = true;
                                matchToUpdate.setPlayerToPlay(ps);
                                matchToUpdate.setCheaterCounter(0);
                            }
                        }
                    }
                }
                // Winner checker.
                for (PlayerStats ps : matchToUpdate.getPlayerStats()) {
                    if (ps.getChips().stream().filter(x -> x.getAbsolutePosition() == 71).toList().size() == 4) {
                        matchToUpdate.setWinner(ps);
                    }
                }
                matchService.save(matchToUpdate);
                return new ModelAndView("redirect:/matches/" + matchId);
            }
        } else {
            ModelAndView result = new ModelAndView("redirect:/matches/" + matchId);
            result.addObject("message", "It's not your turn");
            return result;
        }
    }

    @GetMapping("/{matchId}/chat")
    public ModelAndView matchChat(@PathVariable("matchId") Integer matchId, HttpServletResponse response) {
        response.addHeader("Refresh", "2");
        ModelAndView result = new ModelAndView(MATCHMESSAGES_LISTING);
        List<User> usersInside = new ArrayList<>();
        for (PlayerStats ps :matchService.getMatchById(matchId).getPlayerStats()){
            usersInside.add(ps.getUser());
        }
        List<MessageChat> allMessages = messageChatService.findByMatch(matchId);
        result.addObject("usersInside", usersInside);
        result.addObject("messagesChat", allMessages);
        result.addObject("matchId", matchId);
        return result;
    }

    @GetMapping("/{matchId}/chat/send")
    public ModelAndView createMessage(@PathVariable("matchId") Integer matchId) {
        ModelAndView result = new ModelAndView(MESSAGE_EDIT);
        MessageChat newMessage = new MessageChat();
        result.addObject("newMessageChat", newMessage);
        result.addObject("matchId", matchId);

        return result;
    }

    @PostMapping("/{matchId}/chat/send")
    public ModelAndView saveNewMessage(@Valid MessageChat messageChat, BindingResult br,
            @PathVariable("matchId") Integer matchId) {
        ModelAndView result = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        messageChat.setUser(loggedUser);
        messageChat.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME));
        messageChat.setMatch(messageChatService.findMatchById(matchId));
        if (messageChat.getDescription().length()>250){
            result = new ModelAndView("redirect:/matches/" + matchId + "/chat/send");
            result.addObject("message", "message too long!");
            return result;
        }
        if (br.hasErrors()) {
            result = new ModelAndView(MESSAGE_EDIT);
            result.addAllObjects(br.getModel());
        } else {
            if (!messageChat.getDescription().isEmpty()) {
                messageChatService.save(messageChat);
            }
            result = new ModelAndView("redirect:/matches/" + matchId);
        }
        return result;
    }

}
