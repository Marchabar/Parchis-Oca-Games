package com.ling1.springmvc;

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
    MatchService service;

    @Test
    void testFindMatchesByLobbyId() {

        // get all matches of one lobby
        Collection<Match> matchesLobbyA = service.findMatchesByLobbyId(1);
        Collection<Match> matchesLobbyB = service.findMatchesByLobbyId(3);
        Collection<Match> matchesLobbyC = service.findMatchesByLobbyId(4);

        assertTrue(matchesLobbyA.size() == 2,
                String.format("Matches expected for lobby 1: %d but got: %d", 2, matchesLobbyA.size()));
        assertTrue(matchesLobbyB.size() == 1,
                String.format("Matches expected for lobby 3: %d but got: %d", 1, matchesLobbyB.size()));
        assertTrue(matchesLobbyC.size() == 2,
                String.format("Matches expected for lobby 4: %d but got: %d", 2, matchesLobbyC.size()));

    }
    @Test
    void testTryToFindMatchesByLobbyId() {

        // get all matches of one lobby
        Collection<Match> matchesLobbyA = service.findMatchesByLobbyId(99);
        assertNotNull(matchesLobbyA);
        assertEquals(0,matchesLobbyA.size());
    }
    @Test
    void testFindAll() {
        Collection<Match> matches = service.findAll();

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
}
