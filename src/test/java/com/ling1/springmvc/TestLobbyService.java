package com.ling1.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.lobby.GameEnum;
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestLobbyService {

    @Autowired
    private LobbyService ls;

    @Autowired
    private UserService us;

    @Test
    public void testService(){
        testFindLobbyTypes();
        testFindOca();
        testFindParchis();
        testFindPlayerLobby();
        testFindAll();
        testOca();
        testParchis();
        testSave();
    }

    void testFindLobbyTypes() {
        Collection<GameEnum> games = ls.findGameTypes();
        List<String> gameNames = games.stream().map((e) -> e.getName()).collect(Collectors.toList());
        assertTrue(gameNames.contains("Oca"), "Expected GameEnum to contain Oca");
        assertTrue(gameNames.contains("Parchis"), "Ecpected GameEnum to contain Parchis");
    }

    void testFindOca() {
        List<Lobby> lobbies = ls.getAllOca();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 7).collect(Collectors.toList()).get(0);
        assertTrue(lobbies.size() == 4, String.format("Expected to find %d lobbies of Oca but got", 4, lobbies.size()));
        int expected = 1;
        int actual = myLobby.getGame().getId();
        assertTrue(expected == actual, String.format("Expected that id of Oca lobby is %d but got %d", expected, actual));
    }

    void testFindParchis() {
        List<Lobby> lobbies = ls.getAllParchis();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 2).collect(Collectors.toList()).get(0);
        int expectedLobbySize = 3;
        int actualLobbySize = lobbies.size();
        assertTrue(expectedLobbySize == actualLobbySize, String.format("Expected amount of parchis lobbies to be %d but got %d", expectedLobbySize, actualLobbySize));
        int expectedGameId = 2;
        int actualGameId = myLobby.getGame().getId();
        assertTrue(expectedGameId == actualGameId, String.format("Expected gameId of parchis lobby to be %d but got %d", expectedGameId, actualGameId));
    }

    void testFindPlayerLobby() {
        Collection<User> user = ls.findPlayersLobby(1);
        Set<Integer> ids = user.stream().map(u -> u.getId()).collect(Collectors.toSet());
        assertEquals(new HashSet<>(Arrays.asList(4,5)), ids);
    }

    void testFindAll() {
        List<Lobby> lobbies = ls.getAllLobbies();
        Lobby myLobby = lobbies.stream().filter(e -> e.getId() == 7).collect(Collectors.toList()).get(0);
        Lobby myOtherLobby = lobbies.stream().filter(e -> e.getId() == 2).collect(Collectors.toList()).get(0);
        assertTrue(lobbies.size() == 7);
        assertTrue(myLobby.getGame().getId() == 1);
        assertTrue(myOtherLobby.getGame().getId() == 2);

    }

    void testDeleteLobby() {
        ls.deleteLobby(1);
        Lobby lobby = ls.getLobbyById(1);
        assertEquals(null, lobby, String.format("Expected lobby to be null after deletion but was %d", lobby));
    }

    void testGetLobbyById() {
        Lobby lobby = ls.getLobbyById(1);
        GameEnum oca = ls.oca();
        assertEquals(1, lobby.getId());
        assertEquals(oca, lobby.getGame());
    }

    void testOca() {
        GameEnum oca = ls.oca();
        assertEquals(oca.getId(), 1);
        assertEquals(oca.getName(), "Oca");
    }

    void testParchis() {
        GameEnum parchis = ls.parchis();
        assertEquals(parchis.getId(), 2);
        assertEquals(parchis.getName(), "Parchis");
    }

    void testSave() {
        Lobby l = new Lobby();
        GameEnum oca = ls.oca();
        User user1 = us.findUsername("pepito");
        User user2 = us.findUsername("luke1");
        l.setGame(oca);
        l.setId(8);
        l.setPlayers(Arrays.asList(user1, user2));
        l.setHost(user1);
        l.setMatches(new HashSet<>());
        ls.save(l);
        Lobby retrieved = ls.getLobbyById(8);
        assertEquals(8, retrieved.getId(), String.format("Expected id of new lobby to be %d but was", 8, retrieved.getId()));
        assertEquals(oca, retrieved.getGame(), String.format("Expected game of new lobby to be %s but was %s", oca, retrieved.getGame()));
    }
    
}
