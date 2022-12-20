package com.ling1.springmvc;

import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestMatchService {

    @Autowired
    MatchService matchService;

    @Autowired
    UserService userService;

    @Test
    void testFindAll() {
        Collection<Match> matches = matchService.findAll();

        // check whether matches exist in db (only pick 2 matches):
        Match testMatchA = matches.stream().filter(e -> e.getId() == 1).collect(Collectors.toList()).get(0);
        Match testMatchB = matches.stream().filter(e -> e.getId() == 3).collect(Collectors.toList()).get(0);

        assertTrue(matches.size() == 5,
                String.format("Number of matches expected: %d but got: %d", 5, matches.size()));
        assertTrue(testMatchA.getId() == 1,
                String.format("Test match expected: %d but got: %d", 1,testMatchA.getId()));
        assertTrue(testMatchB.getId() == 3,
                String.format("Test match expected: %d but got: %d", 3, testMatchB.getId()));

    }

    @Test
    void testFindMatchesByLobbyId() {

        // get all matches of one lobby
        Collection<Match> matchesLobbyA = matchService.findMatchesByLobbyId(1);
        Collection<Match> matchesLobbyB = matchService.findMatchesByLobbyId(3);
        Collection<Match> matchesLobbyC = matchService.findMatchesByLobbyId(4);

        assertTrue(matchesLobbyA.size() == 2,
                String.format("Matches expected for lobby 1: %d but got: %d", 2, matchesLobbyA.size()));
        assertTrue(matchesLobbyB.size() == 1,
                String.format("Matches expected for lobby 3: %d but got: %d", 1, matchesLobbyB.size()));
        assertTrue(matchesLobbyC.size() == 2,
                String.format("Matches expected for lobby 4: %d but got: %d", 2, matchesLobbyC.size()));

    }
    @Test
    void ntestTryToFindMatchesByLobbyId() {

        // get all matches of one lobby
        Collection<Match> matchesLobbyA = matchService.findMatchesByLobbyId(99);
        assertNotNull(matchesLobbyA);
        assertEquals(0,matchesLobbyA.size());
    }

    @Test
    void testActiveMatchOf(){
        User user = userService.getUserById(3);
        assertNotNull(user);
        Match activeMatch = matchService.activeMatchOf(user);
        Match targetMatch = matchService.getMatchById(5);

        assertEquals(targetMatch,activeMatch);
    }
    @Test
    void ntestActiveMatchOfNotFound(){
        User user = userService.getUserById(1);
        assertNotNull(user);
        Match activeMatch = matchService.activeMatchOf(user);
        assertNull(activeMatch);
    }
    @Test
    void ntestActiveMatchOfNotFoundWithUserNull(){
        User user = userService.getUserById(99);
        assertNull(user);
        Match activeMatch = matchService.activeMatchOf(user); //user will be null
        assertNull(activeMatch);
    }
   @Test
    void testGetMatchById(){
        Match match = matchService.getMatchById(1);
        assertNotNull(match);
        assertEquals(1,match.getId());
   }
    @Test
    void ntestGetMatchByIdNotFound(){
        Match match = matchService.getMatchById(99);
        assertEquals(null,match);
    }

    @Test
    void testFindPlayer(){
        PlayerStats playerStats = matchService.findPlayer(3,5);
        assertNotNull(playerStats);
        assertEquals("pepito",playerStats.getUser().getLogin());

        PlayerStats playerStats2 = matchService.findPlayer(4,7);
        assertNotNull(playerStats2);
        assertEquals("luke1",playerStats2.getUser().getLogin());
    }
    @Test
    void ntestFindPlayerNotFound(){
        PlayerStats playerStats = matchService.findPlayer(88,99);
        assertEquals(null, playerStats);
    }

    @Test
    void testFindMatchByPlayer(){
        Match match = matchService.findMatchByPlayer(4);
        assertNotEquals(null,match);
        assertEquals(2,match.getId());

        Match match2 = matchService.findMatchByPlayer(8);
        assertNotEquals(null,match2);
        assertEquals(4,match2.getId());
    }
    @Test
    void ntestFindMatchByPlayerNotFound(){
        Match match = matchService.findMatchByPlayer(99);
        assertEquals(null,match);
   }

}
