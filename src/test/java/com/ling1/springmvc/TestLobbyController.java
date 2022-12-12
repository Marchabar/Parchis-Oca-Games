package com.ling1.springmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.user.UserStatusEnum;

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
    final int TEST_LOBBY_ID_2 = 2;
    final int TEST_USER_ID = 1;
    private GameEnum oca;
    private GameEnum parchis;


    @BeforeEach
    void setup() {
        PlayerColor yellow = new PlayerColor();
        yellow.setName("YELLOW");
        User user1 = new User();
        user1.setId(TEST_USER_ID);
        user1.setLogin("pedro");
        user1.setPassword("123");
        user1.setRole("admin");
        UserStatusEnum us1stat = new UserStatusEnum();
        us1stat.setName("Online");
        user1.setUserStatus(us1stat);
        user1.setPrefColor(yellow);

        PlayerColor blue = new PlayerColor();
        blue.setName("BLUE");
        User user2 = new User();
        user2.setId(2);
        user2.setLogin("nic");
        user2.setPassword("123");
        user2.setRole("member");
        UserStatusEnum us2stat = new UserStatusEnum();
        us2stat.setName("Online");
        user2.setUserStatus(us2stat);
        user2.setPrefColor(blue);
        
        PlayerColor red = new PlayerColor();
        red.setName("RED");
        User user3 = new User();
        user3.setId(3);
        user3.setLogin("mnm");
        user3.setPassword("123");
        user3.setRole("member");
        UserStatusEnum us3stat = new UserStatusEnum();
        us3stat.setName("Online");
        user3.setUserStatus(us2stat);
        user3.setPrefColor(red);

        Lobby lobby1 = new Lobby();
        lobby1.setId(TEST_LOBBY_ID);
        lobby1.setPlayers(Lists.newArrayList());
        lobby1.setMatches(Sets.newHashSet());
        lobby1.setHost(user1);
        lobby1.setKickedPlayers(Lists.newArrayList());
        lobby1.setPlayers(Lists.newArrayList());
        GameEnum oca = new GameEnum();
        oca.setName("Oca");
        this.oca = oca;
        lobby1.setGame(oca);

        Lobby lobby2 = new Lobby();
        lobby2.setId(TEST_LOBBY_ID_2);
        lobby2.setPlayers(Lists.newArrayList());
        lobby2.setMatches(Sets.newHashSet());
        lobby2.setHost(user1);
        lobby2.setKickedPlayers(Lists.newArrayList());
        lobby2.setPlayers(Lists.newArrayList(user3, user2));
        GameEnum parchis = new GameEnum();
        parchis.setName("Parchis");
        this.parchis = parchis;

        given(this.lobbyService.getAllLobbies()).willReturn(Lists.newArrayList(lobby1,lobby2));
        given(this.lobbyService.getLobbyById(TEST_LOBBY_ID)).willReturn(lobby1);
        given(this.lobbyService.getLobbyById(TEST_LOBBY_ID_2)).willReturn(lobby2);
        given(this.userService.findUsername(anyString())).willReturn(user1);
        given(this.userService.getUserById(2)).willReturn(user2);
        given(this.playerService.findColors()).willReturn(Lists.newArrayList(blue, red, yellow));
        when(this.matchService.save(any())).thenAnswer(i -> {
            Match m = (Match) i.getArguments()[0];
            m.setId(1);
            return m;
        });
    }

    @Test
    void testShowLobbiesListing() throws Exception {
            mockMvc.perform(get("/lobbies"))
                .andExpect(model().attributeExists("lobbies"))
                .andExpect(status().isOk())
                .andExpect(view().name("Lobbies/LobbiesListing"));
    }

    @Test
    void testShowOcaListing() throws Exception {
        mockMvc.perform(get("/lobbies/oca"))
            .andExpect(model().attributeExists("lobbiesOca"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/OcaListing"));
    }

    @Test
    void testShowParchisListing() throws Exception{
        mockMvc.perform(get("/lobbies/parchis"))
            .andExpect(model().attributeExists("lobbiesParchis"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/ParchisListing"));
    }

    @Test
    void testGetDeleteLobby() throws Exception {
        mockMvc.perform(get("/lobbies/delete/{id}",TEST_LOBBY_ID))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby removed successfully")));
    }

    @Test
    void testGetDeleteLobbyFail() throws Exception {
        //TODO implement
    }

    @Test
    void testGetEditLobby() throws Exception {
        mockMvc.perform(get("/lobbies/edit/{id}",TEST_LOBBY_ID))
            .andExpect(status().isOk())
            .andExpect(model().attribute("lobby",hasProperty("game", is(this.oca))));
    }

    @Test
    void testPostEditLobby() throws Exception {
        mockMvc.perform(post("/lobbies/edit/{id}",TEST_LOBBY_ID_2)
            .with(csrf())
            .param("games", "Oca"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby saved succesfully!")));
    }

    @Test
    void testGetCreateLobby() throws Exception {
        mockMvc.perform(get("/lobbies/create"))
                .andExpect(model().attributeExists("lobby"))
                .andExpect(status().isOk())
                .andExpect(view().name("Lobbies/EditLobby"));
    }

    @Test
    void testSaveNewLobby() throws Exception {
        mockMvc.perform(post("/lobbies/create")
            .with(csrf())
            .param("games", "Oca"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby saved succesfully!")));
    }

    @Test
    void testCreateOca() throws Exception {
        mockMvc.perform(get("/lobbies/createOca"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lobbies/1"));
        };

    @Test
    void testCreateParchis() throws Exception {
        mockMvc.perform(get("/lobbies/createParchis"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lobbies/1"));
    }

    @Test
    void testShowMatchesByLobbyId() throws Exception{
        mockMvc.perform(get("/lobbies/1"))
            .andExpect(model().attributeExists("lobby"))
            .andExpect(model().attributeExists("players"))
            .andExpect(model().attributeExists("loggedUser"))
            .andExpect(model().attributeExists("now"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/InsideLobby"));
    }

    @Test
    void testKickPlayer() throws Exception {
        mockMvc.perform(get("/lobbies/2/kick/2"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "nic kicked"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testKickPlayerFail() throws Exception {
        //TODO implement
    }

    @Test
    void testGetCreateMatches() throws Exception {
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isFound())
            .andExpect(model().attributeDoesNotExist("message"))
            .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void testGetCreateMatchesFail() throws Exception {
        //TODO implement
    }

    @Test
    void testGetAllMatches() throws Exception {
        mockMvc.perform(get("/lobbies/1/matches"))
            .andExpect(model().attributeExists("matches"))
            .andExpect(status().isOk())
            .andExpect(view().name("Matches/MatchesListing"));
    }

    @Test
    void testJoinMatchWithColor() throws Exception {
        mockMvc.perform(get("/lobbies/1/YELLOW"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/lobbies/1"));
    }

    @Test
    void testJoinMatchWithColorFail() throws Exception {
        //TODO implement
    }
}
