package com.ling1.springmvc.lobby;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/lobbies")
public class LobbyController {

    public static final String LOBBIES_LISTING = "Lobbies/LobbiesListing";
    public static final String LOBBY_EDIT = "Lobbies/EditLobby";
    public static final String OCA_LISTING = "Lobbies/OcaListing";
    public static final String PARCHIS_LISTING = "Lobbies/ParchisListing";
    public static final String LOBBY_INSIDE = "Lobbies/InsideLobby";
    // MATCHES DATA
    public static final String MATCHES_LISTING = "Matches/MatchesListing";

    @Autowired
    LobbyService lobbyService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;
    @Autowired
    PlayerService playerService;

    @ModelAttribute("games")
    public Collection<GameEnum> populateGameTypes() {
        return this.lobbyService.findGameTypes();
    }

    @GetMapping
    // Admin only.
    public ModelAndView showLobbiesListing() {
        ModelAndView result = new ModelAndView(LOBBIES_LISTING);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        for (Lobby lobby : lobbyService.getAllLobbies()) {
            if (lobby.getPlayers() != null) {
                if (lobby.getPlayers().contains(loggedUser)) {
                    Collection<User> newPlayers = lobby.getPlayers();
                    newPlayers.remove(loggedUser);
                    lobby.setPlayers(newPlayers);
                    lobbyService.save(lobby);
                }
                if (!lobby.getPlayers().contains(lobby.getHost())) {
                    lobbyService.deleteLobby(lobby.getId());
                }
            }
        }
        result.addObject("lobbies", lobbyService.getAllLobbies());
        return result;
    }

    @GetMapping("/oca")
    // Like previous "/lobbies", simple filter added.
    public ModelAndView showOcaListing(HttpServletResponse response) {
        response.addHeader("Refresh", "10");

        ModelAndView result = new ModelAndView(OCA_LISTING);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        // If in a lobby and then exit to the general listing, you are removed from the
        // lobby.
        for (Lobby lobby : lobbyService.getAllLobbies()) {
            if (lobby.getPlayers().contains(loggedUser)) {
                Collection<User> newPlayers = lobby.getPlayers();
                newPlayers.remove(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
            }
            // If the host leaves, either another player in the lobby is chosen to be the
            // new host or if there is no one and no matches to store, the lobby is deleted
            // por efficiency purposes. If it has matches to store, it is put on "standby".
            if (!lobby.getPlayers().contains(lobby.getHost())) {
                if (!lobby.getPlayers().isEmpty()) {
                    lobby.setHost(lobby.getPlayers().stream().findFirst().get());
                } else if (lobby.getMatches().isEmpty()) {
                    lobbyService.deleteLobby(lobby.getId());
                } else {
                    lobby.setHost(null);
                }
                lobbyService.save(lobby);
            }
        }
        result.addObject("lobbiesOca", lobbyService.getAllOca());
        return result;
    }

    @GetMapping("/parchis")
    // Same as oca, different filter.
    public ModelAndView showParchisListing(HttpServletResponse response) {
        response.addHeader("Refresh", "10");

        ModelAndView result = new ModelAndView(PARCHIS_LISTING);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        for (Lobby lobby : lobbyService.getAllLobbies()) {
            if (lobby.getPlayers().contains(loggedUser)) {
                Collection<User> newPlayers = lobby.getPlayers();
                newPlayers.remove(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
            }

            if (!lobby.getPlayers().contains(lobby.getHost())) {
                if (!lobby.getPlayers().isEmpty()) {
                    lobby.setHost(lobby.getPlayers().stream().findFirst().get());
                } else if (lobby.getMatches().isEmpty()) {
                    lobbyService.deleteLobby(lobby.getId());
                } else {
                    lobby.setHost(null);
                }
                lobbyService.save(lobby);
            }
        }
        result.addObject("lobbiesParchis", lobbyService.getAllParchis());
        return result;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteLobby(@PathVariable("id") int id) {
        GameEnum game = lobbyService.getLobbyById(id).getGame();
        String urlPath = null;
        if (game.getName().contains("O")) {
            urlPath = "oca";
        } else
            urlPath = "parchis";

        lobbyService.deleteLobby(id);
        ModelAndView result = new ModelAndView("redirect:/lobbies/" + urlPath);
        result.addObject("message", "Lobby removed successfully");
        return result;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editLobby(@PathVariable("id") int id) {
        ModelAndView result = new ModelAndView(LOBBY_EDIT);
        Lobby lobby = lobbyService.getLobbyById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (lobby != null) {
            if (loggedUser == lobby.getHost() || loggedUser.getRole().equals("admin")) {
                result.addObject("lobby", lobby);
                result.addObject("game", lobby.getGame().toString().toLowerCase());
            } else {
                result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
            }
        } else {
            result = showLobbiesListing();
        }
        return result;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editLobby(@PathVariable("id") int id, @Valid Lobby lobby, BindingResult br) {
        ModelAndView result = null;

        if (br.hasErrors()) {
            result = new ModelAndView(LOBBY_EDIT);
            result.addAllObjects(br.getModel());
        } else {
            Lobby lobbyToUpdate = lobbyService.getLobbyById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            if (lobbyToUpdate != null) {
                if (loggedUser == lobbyToUpdate.getHost() || loggedUser.getRole().equals("admin")) {
                    lobbyToUpdate.setGame(lobby.getGame());
                    lobbyService.save(lobbyToUpdate);
                    if (!loggedUser.getRole().equals("admin")) {
                        result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
                    } else if (lobbyToUpdate.getHost().getLogin() == loggedUser.getLogin()) {
                        result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
                    } else {
                        result = new ModelAndView("redirect:/lobbies/" + lobby.getGame().toString().toLowerCase());
                    }
                    result.addObject("message", "Lobby saved succesfully!");
                } else {
                    result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
                    result.addObject("message", "Lobby with id " + id + " is not yours!");
                }
            } else {
                result = showLobbiesListing();
                result.addObject("message", "Lobby with id " + id + " not found!");
            }
        }
        return result;
    }

    @GetMapping("/create")
    public ModelAndView createLobby() {
        ModelAndView result = new ModelAndView(LOBBY_EDIT);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        for (Lobby checkLobby : lobbyService.getAllLobbies()) {
            if (checkLobby.getPlayers().contains(loggedUser)) {
                result = new ModelAndView("redirect:/lobbies/" + checkLobby.getId());
                return result;
            }
        }
        Lobby lobby = new Lobby();
        result.addObject("lobby", lobby);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewLobby(@Valid Lobby lobby, BindingResult br) {
        ModelAndView result = null;
        if (br.hasErrors()) {
            result = new ModelAndView(LOBBY_EDIT);
            result.addAllObjects(br.getModel());
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            for (Lobby checkLobby : lobbyService.getAllLobbies()) {

                if (checkLobby.getPlayers().contains(loggedUser)) {
                    result = new ModelAndView("redirect:/lobbies/" + checkLobby.getId());
                    return result;
                }
            }
            lobby.setHost(loggedUser);
            Collection<User> newPlayers = new ArrayList<User>();
            newPlayers.add(loggedUser);
            lobby.setPlayers(newPlayers);
            lobbyService.save(lobby);
            result = new ModelAndView("redirect:/lobbies/" + lobby.getId());
            result.addObject("message", "Lobby saved succesfully!");
        }
        return result;
    }

    @GetMapping("/createOca")
    public ModelAndView createOca(@Valid Lobby lobby2, BindingResult br) {
        // This controller tries to "reuse" previous empty lobbies before creating a new
        // one from scratch.
        ModelAndView result = null;
        if (br.hasErrors()) {
            result = new ModelAndView(LOBBY_INSIDE);
            result.addAllObjects(br.getModel());
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            for (Lobby checkLobby : lobbyService.getAllLobbies()) {
                if (checkLobby.getPlayers().contains(loggedUser)) {
                    result = new ModelAndView("redirect:/lobbies/" + checkLobby.getId());
                    return result;
                }
            }
            if (lobbyService.getAllLobbies().stream().filter(x -> x.getPlayers().isEmpty()).count() == 0) {
                Lobby lobby = new Lobby();
                for (Lobby checkLobby : lobbyService.getAllLobbies()) {
                    if (checkLobby.getPlayers().contains(loggedUser)) {
                        result = new ModelAndView("redirect:/lobbies/" + checkLobby.getId());
                        return result;
                    }
                }
                lobby.setGame(lobbyService.oca());
                lobby.setHost(loggedUser);
                Collection<User> newPlayers = new ArrayList<User>();
                newPlayers.add(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
                result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
            } else {
                Lobby reusedOcaLobby = lobbyService.getLobbyById(lobbyService.getAllLobbies().stream()
                        .filter(x -> x.getPlayers().isEmpty()).findFirst().get().getId());
                reusedOcaLobby.setGame(lobbyService.oca());
                lobbyService.save(reusedOcaLobby);
                result = new ModelAndView("redirect:/lobbies/" + lobbyService.getAllLobbies().stream()
                        .filter(x -> x.getPlayers().isEmpty()).findFirst().get().getId());
            }
        }
        return result;
    }

    @GetMapping("/createParchis")
    // Same process as "/createOca"
    public ModelAndView createParchis(@Valid Lobby lobby2, BindingResult br) {
        ModelAndView result = null;
        if (br.hasErrors()) {
            result = new ModelAndView(LOBBY_INSIDE);
            result.addAllObjects(br.getModel());
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());

            for (Lobby checkLobby : lobbyService.getAllLobbies()) {

                if (checkLobby.getPlayers().contains(loggedUser)) {
                    result = new ModelAndView("redirect:/lobbies/" + checkLobby.getId());
                    return result;
                }

            }
            if (lobbyService.getAllLobbies().stream().filter(x -> x.getPlayers().isEmpty()).count() == 0) {
                Lobby lobby = new Lobby();

                lobby.setGame(lobbyService.parchis());
                lobby.setHost(loggedUser);
                Collection<User> newPlayers = new ArrayList<User>();
                newPlayers.add(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
                result = new ModelAndView("redirect:/lobbies/" + lobby.getId().toString());
                result.addObject("lobby", lobby);
                result.addObject("players", newPlayers);
            } else {
                Lobby reusedParchisLobby = lobbyService.getLobbyById(lobbyService.getAllLobbies().stream()
                        .filter(x -> x.getPlayers().isEmpty()).findFirst().get().getId());
                reusedParchisLobby.setGame(lobbyService.parchis());
                lobbyService.save(reusedParchisLobby);
                result = new ModelAndView("redirect:/lobbies/" + lobbyService.getAllLobbies().stream()
                        .filter(x -> x.getPlayers().isEmpty()).findFirst().get().getId());
            }
        }
        return result;
    }

    @GetMapping("/{id}")
    public ModelAndView insideLobby(@PathVariable("id") int id, HttpServletResponse response) {
        response.addHeader("Refresh", "5");
        ModelAndView result = new ModelAndView(LOBBY_INSIDE);

        Lobby lobby = lobbyService.getLobbyById(id);
        Collection<User> players = lobbyService.findPlayersLobby(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        // If there is a running match with yourself in it, you are redirected to the
        // match.
        for (Match m : lobby.getMatches()) {
            for (PlayerStats ps : m.getPlayerStats()) {
                if (ps.getUser() == loggedUser && m.getWinner() == null) {
                    return new ModelAndView("redirect:/matches/" + m.getId());
                }
            }
        }
        // If you are in lobby 3 and try to go to lobby 4 via url, you are sent back to
        // lobby 3.
        for (Lobby checklobby : lobbyService.getAllLobbies()) {
            if (checklobby.getPlayers().contains(loggedUser) && checklobby.getId() != id) {
                return new ModelAndView("redirect:/lobbies/" + checklobby.getId());
            }
        }
        // If the lobby is full, you are sent back to the listing you were looking.
        if (lobby != null && players != null) {
            result.addObject("lobby", lobby);
            result.addObject("players", players);
            result.addObject("loggedUser", loggedUser);
            result.addObject("now", LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
                    .format(DateTimeFormatter.ISO_LOCAL_TIME));
            if (players.size() >= 4 && !lobby.getPlayers().contains(loggedUser)) {
                if (lobby.getGame().getName().contains("Oca")) {
                    result = new ModelAndView("redirect:/lobbies/oca");
                } else {
                    result = new ModelAndView("redirect:/lobbies/parchis");
                }
                result.addObject("message", "Lobby is full!");
            }
            // If the lobby is empty, you are set to be the new host.
            if (players.size() == 0) {
                lobby.setHost(loggedUser);
                players.add(loggedUser);
                lobby.setPlayers(players);
                lobbyService.save(lobby);
                // If you are not inside the lobby and there is space for you, you are added.
            } else if (players.size() <= 3 && !players.contains(loggedUser)) {
                players.add(loggedUser);
                lobby.setPlayers(players);
                lobbyService.save(lobby);
            }
        } else {
            result = new ModelAndView("redirect:/");

        }
        return result;
    }

    // MATCHES

    @GetMapping("/{id}/matches")
    // Admin only
    public ModelAndView showMatchesByLobbyId(@PathVariable("id") Integer id) {
        ModelAndView result = new ModelAndView(MATCHES_LISTING);
        result.addObject("matches", matchService.findMatchesByLobbyId(id));
        return result;
    }

    @GetMapping("/{lobbyId}/createMatch")
    public ModelAndView createMatch(@Valid PlayerStats ps1, @PathVariable("lobbyId") Integer lobbyId) {
        Match createdMatch = new Match();
        Lobby originalLobby = lobbyService.getLobbyById(lobbyId);
        Collection<PlayerStats> newPlayers = new ArrayList<PlayerStats>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());

        if (originalLobby.getHost() == loggedUser) {
            if (originalLobby.getPlayers().size() >= 2) {
                // As if User1 and User2 could have the same preferred color when joining the
                // lobby, we need to check if they have changed them properly.
                Collection<PlayerColor> chosenColors = new ArrayList<PlayerColor>();
                for (User u : originalLobby.getPlayers()) {
                    if (chosenColors.contains(u.getPrefColor())) {
                        ModelAndView result = new ModelAndView("redirect:/lobbies/" + lobbyId);
                        result.addObject("message", "At least 2 players have the same color, choose different colors");
                        return result;
                    } else {
                        chosenColors.add(u.getPrefColor());
                    }
                }
                for (User u : originalLobby.getPlayers()) {
                    // It is ugly, but to make sure no value is set as null when the match starts.
                    PlayerStats newPlayer = new PlayerStats();
                    newPlayer.setUser(u);
                    newPlayer.setNumDiceRolls(0);
                    newPlayer.setNumberOfGooses(0);
                    newPlayer.setNumberOfLabyrinths(0);
                    newPlayer.setNumberOfPlayerDeaths(0);
                    newPlayer.setNumberOfPlayerPrisons(0);
                    newPlayer.setNumberOfPlayerWells(0);
                    newPlayer.setNumberOfInns(0);
                    newPlayer.setPosition(0);
                    newPlayer.setTurnsStuck(0);
                    newPlayer.setPlayerColor(u.getPrefColor());
                    playerService.save(newPlayer);
                    newPlayers.add(newPlayer);
                }
                // As we sum 1 directly when entering the loop the starting value is -1 (0 after
                // the sum).
                Integer ColorPosition = -1;
                User firstUser = null;
                Boolean prevPChosen = false;
                // Currently the order to play is decided in the color order, if there is no RED
                // player we look for a BLUE player and so on.
                while (!prevPChosen) {
                    ColorPosition++;
                    PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                    for (User u : originalLobby.getPlayers()) {
                        if (u.getPrefColor() == colorToTry) {
                            firstUser = u;
                            prevPChosen = true;
                        }
                    }
                }
                PlayerStats firstPlayer = null;
                for (PlayerStats ps : newPlayers) {
                    if (ps.getUser() == firstUser)
                        firstPlayer = ps;
                }

                createdMatch.setPlayerToPlay(firstPlayer);
                createdMatch.setGame(lobbyService.oca());
                createdMatch.setLobby(originalLobby);
                createdMatch.setNumTurns(0);
                createdMatch.setPlayerStats(newPlayers);
                createdMatch.setLastRoll(0);
                matchService.save(createdMatch);
                ModelAndView result = new ModelAndView("redirect:/matches/" + createdMatch.getId());
                return result;
            } else {
                ModelAndView result = new ModelAndView("redirect:/lobbies/" + lobbyId);
                result.addObject("message", "Not enough players to start the game");
                return result;
            }
        } else {
            ModelAndView result = new ModelAndView("redirect:/lobbies/" + lobbyId);
            result.addObject("message", "You are not the host of the lobby and therefore cannot start the game");
            return result;
        }
    }

    @GetMapping("/{id}/{chosenColor}")
    public ModelAndView createOca(@PathVariable("id") Integer lobbyId,
            @PathVariable("chosenColor") String chosenColor) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        Lobby lobby = lobbyService.getLobbyById(lobbyId);
        for (User u : lobby.getPlayers()) {
            if (u.getPrefColor().getName().equals(chosenColor)) {
                ModelAndView result = new ModelAndView("redirect:/lobbies/" + lobbyId);
                result.addObject("message", chosenColor + " is already selected");
                return result;
            }
        }
        for (PlayerColor pc : playerService.findColors()) {
            if (pc.getName().equals(chosenColor))
                loggedUser.setPrefColor(pc);
        }
        userService.save(loggedUser);
        return new ModelAndView("redirect:/lobbies/" + lobbyId);
    }
}
