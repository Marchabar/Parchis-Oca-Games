package com.japarejo.springmvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.japarejo.springmvc.lobby.GameEnum;
import com.japarejo.springmvc.lobby.LobbyRepository;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TestUS6 {

    @Autowired(required = false)
    private LobbyRepository lr;

    @Test
    public void testUS6(){
        repositoryExists();
        testFindLobbyTypes();
    }

    void repositoryExists() {
        assertNotNull(lr,"The repository was not injected into the tests, its autowired value was null");
    }

    void testFindLobbyTypes() {
        List<GameEnum> games = lr.findLobbyTypes();
        List<String> gameNames = games.stream().map((e) -> e.getName()).collect(Collectors.toList());
        assertTrue(gameNames.contains("Oca"), "Game does contain Oca");
        assertTrue(gameNames.contains("Parchis"), "Game does contain Parchis");
    }

    
}
