package com.japarejo.springmvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.japarejo.springmvc.lobby.GameEnum;
import com.japarejo.springmvc.lobby.Lobby;
import com.japarejo.springmvc.lobby.LobbyRepository;
import com.japarejo.springmvc.lobby.LobbyService;
import com.japarejo.springmvc.user.User;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestUS6 {

    @Autowired(required = false)
    private LobbyRepository lr;

    @Autowired
    private LobbyService ls;

    @Test
    public void testUS6(){
        testFindLobbyTypes();
        testFindOca();
        testFindParchis();
        testFindPlayerLobby();
        testFindAll();
    }

    void testFindLobbyTypes() {
        Collection<GameEnum> games = ls.findGameTypes();
        List<String> gameNames = games.stream().map((e) -> e.getName()).collect(Collectors.toList());
        assertTrue(gameNames.contains("Oca"), "Game does contain Oca");
        assertTrue(gameNames.contains("Parchis"), "Game does contain Parchis");
    }

    void testFindOca() {
        List<Lobby> lobbies = lr.findOca();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 7).collect(Collectors.toList()).get(0);
        assertTrue(lobbies.size() == 4);
        assertTrue(myLobby.getGame().getId() == 1);
    }

    void testFindParchis() {
        List<Lobby> lobbies = lr.findParchis();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 2).collect(Collectors.toList()).get(0);
        assertTrue(lobbies.size() == 3);
        assertTrue(myLobby.getGame().getId() == 2);
    }

    void testFindPlayerLobby() {
        Collection<User> user = ls.findPlayersLobby(1);
        Set<Integer> ids = user.stream().map(u -> u.getId()).collect(Collectors.toSet());
        assertEquals(new HashSet<>(Arrays.asList(4,5)), ids);
    }

    void testFindAll() {
        List<Lobby> lobbies = lr.findAll();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 7).collect(Collectors.toList()).get(0);
        Lobby myOtherLobby = lobbies.stream().filter(e -> e.getId() == 2).collect(Collectors.toList()).get(0);
        assertTrue(lobbies.size() == 7);
        assertTrue(myLobby.getGame().getId() == 1);
        assertTrue(myOtherLobby.getGame().getId() == 2);

    }
    
}
