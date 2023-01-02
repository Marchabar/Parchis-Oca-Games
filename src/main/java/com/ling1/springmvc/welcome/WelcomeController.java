package com.ling1.springmvc.welcome;

import java.util.Collection;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.*;
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
import java.util.*;
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
<<<<<<< HEAD

=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendController;
import com.ling1.springmvc.friend.FriendService;
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerStats;

@Controller
public class WelcomeController {
    @Autowired
    LobbyService lobbyService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
<<<<<<< HEAD
<<<<<<< HEAD

    @GetMapping(path = { "", "/" })
    public String welcome() {
=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
    @Autowired
    FriendService friendService;

    public final String WELCOME = "welcome";

    @GetMapping(path = { "", "/" })
    public ModelAndView welcome() {
        ModelAndView result = new ModelAndView(WELCOME);
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser != null) {
            for (Lobby lobby : lobbyService.getAllLobbies()) {
                if (lobby.getPlayers().contains(loggedUser)) {
                    Collection<User> newPlayers = lobby.getPlayers();
                    newPlayers.remove(loggedUser);
                    lobby.setPlayers(newPlayers);
                    lobbyService.save(lobby);
                }
            }
            for (Match match : matchService.findAll()) {
                if (match != null) {
                    if (match.getWinner() == null && !match.getPlayerStats().isEmpty()) {
                        for (PlayerStats ps : match.getPlayerStats().stream().collect(Collectors.toList())) {
                            if (ps.getUser() == loggedUser) {
                                Collection<PlayerStats> playingUsers = match.getPlayerStats();
                                playingUsers.remove(ps);
                                match.setPlayerStats(playingUsers);
                                matchService.save(match);
                            }
                        }
                    }
                }
            }
        }
<<<<<<< HEAD
<<<<<<< HEAD
        return "welcome";
=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
        List<Match> activeMatches = new ArrayList<Match>();
        Boolean pendingRequest = false;
        for (Friend f :friendService.getMyFriends(loggedUser)){
            if (f.getAccept()==false && f.getSolicitingUser()!=loggedUser) pendingRequest=true;
            User userToSearch = null;
            if (f.getUser1()==loggedUser) userToSearch = f.getUser2();
            else userToSearch = f.getUser1();
            activeMatches.add(matchService.activeMatchOf(userToSearch));
        }
        for (Lobby l : lobbyService.getAllLobbies()){
            if (l.getPlayers().contains(loggedUser)) result.addObject("currentLobby", l);
        }
       result.addObject("pendingRequest", pendingRequest);
       result.addObject("activeMatches", activeMatches);
       result.addObject("friends", friendService.getMyFriends(loggedUser));
       result.addObject("loggedUser", loggedUser);

        return result;
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0

    }
}
