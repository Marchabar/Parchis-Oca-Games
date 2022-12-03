package com.ling1.springmvc.welcome;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.lobby.LobbyService;

@Controller
public class WelcomeController {
    @Autowired
    LobbyService lobbyService;
    @Autowired
    UserService userService;

    @GetMapping(path = { "", "/" })
    public String welcome() {
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
        }
        return "welcome";

    }
}
