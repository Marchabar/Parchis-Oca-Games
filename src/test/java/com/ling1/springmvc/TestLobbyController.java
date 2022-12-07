package com.ling1.springmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.lobby.GameEnum;
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyController;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.user.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LobbyController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration=SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestLobbyController {
    
    @MockBean
    LobbyService lobbyService;

    @MockBean
    MatchService matchService;

    @MockBean
    UserService userService;

    @MockBean 
    PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    final int TEST_LOBBY_ID = 1;
    private GameEnum oca;
    private GameEnum parchis;

    @Test
    public void testLobbyController() throws Exception {
        testShowLobbiesListing();
        testShowOcaListing();
        testShowParchisListing();
        testGetDeleteLobby();
        testEditLobby();
        testCreateLobby();
        testCreateParchis();
        testGetInsideLobby();
        testShowMatchesByLobbyId();
    }

    @BeforeEach
    void setup() {
        Lobby lobby1 = new Lobby();
        lobby1.setPlayers(Lists.newArrayList());
        lobby1.setMatches(Sets.newHashSet());
        GameEnum oca = new GameEnum();
        oca.setName("Oca");
        this.oca = oca;
        lobby1.setGame(oca);
        Lobby lobby2 = new Lobby();
        lobby2.setPlayers(Lists.newArrayList());
        lobby2.setMatches(Sets.newHashSet());
        GameEnum parchis = new GameEnum();
        parchis.setName("Parchis");
        this.parchis = parchis;

        given(this.lobbyService.getAllLobbies()).willReturn(Lists.newArrayList(lobby1,lobby2));
        given(this.lobbyService.getLobbyById(TEST_LOBBY_ID)).willReturn(lobby1);
    }

    void testShowLobbiesListing() throws Exception {
            mockMvc.perform(get("/lobbies"))
                .andExpect(model().attributeExists("lobbies"))
                .andExpect(status().isOk())
                .andExpect(view().name("Lobbies/LobbiesListing"));
    }

    void testShowOcaListing() throws Exception {
        mockMvc.perform(get("/lobbies/oca"))
            .andExpect(model().attributeExists("lobbiesOca"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/OcaListing"));
    }

    void testShowParchisListing() throws Exception{
        mockMvc.perform(get("/lobbies/parchis"))
            .andExpect(model().attributeExists("lobbiesParchis"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/ParchisListing"));
    }

    void testGetDeleteLobby() throws Exception {
        mockMvc.perform(get("/lobbies/delete/{id}",TEST_LOBBY_ID))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby removed successfully")));
    }

    void testEditLobby() throws Exception{
        mockMvc.perform(get("/lobbies/edit/{id}",TEST_LOBBY_ID))
            .andExpect(status().isOk())
            .andExpect(model().attribute("lobby",hasProperty("game", is(this.oca))));
    }

    void testCreateLobby() {
        //TODO implement
    }

    void testSaveNewLobby() {
        //TODO implement
    }

    void testCreateOca() {
        //TODO implement
    }

    void testCreateParchis() {
        //TODO implement
    }

    void testGetInsideLobby() {
        //TODO implement
    }

    void testShowMatchesByLobbyId() {
        //TODO implement
    }
}
