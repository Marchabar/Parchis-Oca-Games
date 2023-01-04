package com.ling1.springmvc.welcome;

import java.util.Collection;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendController;
import com.ling1.springmvc.friend.FriendService;
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
    @Autowired
    FriendService friendService;

    public final String WELCOME = "welcome";

    @GetMapping(path = { "", "/" })
    public ModelAndView welcome() {
        ModelAndView result = new ModelAndView(WELCOME);
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
        result.addObject("AvailableLobbies", friendService.getLobbiesWithFriendsAvailable(loggedUser));

        return result;

    }
}
