package com.japarejo.springmvc;

import com.japarejo.springmvc.match.Match;
import com.japarejo.springmvc.match.MatchRepository;
import com.japarejo.springmvc.match.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestMatchService {

    @Autowired
    MatchService service;

    @Test
    public void TestMatchService() {
        testFindMatchesByLobbyId();
        testFindAll();
    }

    void testFindMatchesByLobbyId() {

        // get all matches of one lobby
        Collection<Match> matchesLobbyA = service.findMatchesByLobbyId(1);
        Collection<Match> matchesLobbyB = service.findMatchesByLobbyId(2);
        Collection<Match> matchesLobbyC = service.findMatchesByLobbyId(5);

        assertTrue(matchesLobbyA.size() == 1,
                String.format("Matches expected for lobby 1: %d but got: %d", 1, matchesLobbyA.size()));
        assertTrue(matchesLobbyB.size() == 1,
                String.format("Matches expected for lobby 2: %d but got: %d", 1, matchesLobbyB.size()));
        assertTrue(matchesLobbyC.size() == 1,
                String.format("Matches expected for lobby 5: %d but got: %d", 1, matchesLobbyC.size()));

    }

    void testFindAll() {
        Collection<Match> matches = service.findAll();

        // check whether matches exist in db (only pick 2 matches):
        Match testMatchA = matches.stream().filter(e -> e.getId() == 1).collect(Collectors.toList()).get(0);
        Match testMatchB = matches.stream().filter(e -> e.getId() == 3).collect(Collectors.toList()).get(0);

        assertTrue(matches.size() == 3,
                String.format("Number of matches expected: %d but got: %d", 3, matches.size()));
        assertTrue(testMatchA.getId() == 1,
                String.format("Test match expected: %d but got: %d", 1,testMatchA.getId()));
        assertTrue(testMatchB.getId() == 3,
                String.format("Test match expected: %d but got: %d", 3, testMatchB.getId()));

    }
}
