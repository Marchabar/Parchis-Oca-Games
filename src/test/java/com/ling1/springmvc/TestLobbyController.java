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

import com.ling1.springmvc.chip.ChipService;
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
    private ChipService cs;

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
    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    Lobby lobby1;
    Lobby lobby2;


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
        this.user1 = user1;

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
        this.user2 = user2;
        
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
        this.user3 = user3;

        User user4 = new User();
        user4.setId(3);
        user4.setLogin("lol");
        user4.setPassword("123");
        user4.setRole("member");
        user4.setUserStatus(us2stat);
        user4.setPrefColor(red);
        this.user4 = user4;

        User user5 = new User();
        user5.setId(3);
        user5.setLogin("stupid");
        user5.setPassword("123");
        user5.setRole("member");
        user5.setUserStatus(us2stat);
        user5.setPrefColor(red);
        this.user5 = user5;

        Lobby lobby1 = new Lobby();
        lobby1.setId(TEST_LOBBY_ID);
        lobby1.setPlayers(Lists.newArrayList());
        lobby1.setMatches(Sets.newHashSet());
        lobby1.setHost(user1);
        lobby1.setKickedPlayers(Lists.newArrayList());
        lobby1.setPlayers(Lists.newArrayList());
        this.lobby1 = lobby1;

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
        this.lobby2 = lobby2;

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
    void testGetDeleteLobbyDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/delete/555"))
            .andExpect(status().isFound());
    }

    @Test
    void testGetEditLobby() throws Exception {
        mockMvc.perform(get("/lobbies/edit/{id}",TEST_LOBBY_ID))
            .andExpect(status().isOk())
            .andExpect(model().attribute("lobby",hasProperty("game", is(this.oca))));
    }

    @Test
    void testGetEditLobbyDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/edit/555"))
            .andExpect(status().isOk());
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
    void testPostEditLobbyFailNotYours() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(this.user2);
        mockMvc.perform(post("/lobbies/edit/{id}",TEST_LOBBY_ID_2)
            .with(csrf())
            .param("games", "Oca"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby with id 2 is not yours!")));       
    }

    @Test
    void testPostEditLobbyFailDoesNotExist() throws Exception {
        mockMvc.perform(post("/lobbies/edit/{id}",3)
            .with(csrf())
            .param("games", "Oca"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message",is("Lobby with id 3 not found!")))
            .andExpect(view().name("Lobbies/LobbiesListing"));
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
    void testInsideLobby() throws Exception{
        mockMvc.perform(get("/lobbies/1"))
            .andExpect(model().attributeExists("lobby"))
            .andExpect(model().attributeExists("players"))
            .andExpect(model().attributeExists("loggedUser"))
            .andExpect(model().attributeExists("now"))
            .andExpect(status().isOk())
            .andExpect(view().name("Lobbies/InsideLobby"));
    }

    @Test
    void testInsideLobbyFailKicked() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(this.user4);
        lobby1.setKickedPlayers(Lists.newArrayList(this.user4));
        mockMvc.perform(get("/lobbies/1"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("You have been kicked from this lobby and can no longer join")))
            .andExpect(view().name("redirect:/lobbies/oca"));
    }

    @Test
    void testInsideLobbyFailFull() throws Exception {
        given(this.lobbyService.findPlayersLobby(1)).willReturn(Lists.newArrayList(this.user1, this.user2, this.user3, this.user4));
        given(this.userService.findUsername(anyString())).willReturn(this.user5);
        lobby1.setPlayers(Lists.newArrayList(this.user1, this.user2, this.user3, this.user4));
        mockMvc.perform(get("/lobbies/1"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message",is("Lobby is full!")))
            .andExpect(view().name("redirect:/lobbies/oca"));
    }

    @Test
    void testInsideLobbyFailRedirect() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(this.user2);
        lobby1.setKickedPlayers(Lists.newArrayList(this.user2));
        mockMvc.perform(get("/lobbies/1"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testInsideLobbyFailDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/555"))
            .andExpect(status().isFound());
    }

    @Test
    void testGetMatchesInsideLobby() throws Exception {
        mockMvc.perform(get("/lobbies/1/matches"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("matches"))
            .andExpect(view().name("Matches/MatchesListing"));
    }

    @Test
    void testGetMatchInsideLobbyDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/555/matches"))
            .andExpect(status().isOk());

            //TODO extend
    }

    @Test
    void testKickPlayer() throws Exception {
        mockMvc.perform(get("/lobbies/2/kick/2"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "nic kicked"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testKickPlayerFailAlreadyKicked() throws Exception {
        lobby1.setKickedPlayers(Lists.newArrayList(this.user2));
        mockMvc.perform(get("/lobbies/1/kick/2"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "nic is already banned"))
            .andExpect(view().name("redirect:/lobbies/1"));
    }

    @Test
    void testKickPlayerFailNotTheHost() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(this.user4);
        mockMvc.perform(get("/lobbies/2/kick/2"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "You are not the host"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testKickPlayerFailNotInTheLobby() throws Exception {
        when(userService.getUserById(4)).thenReturn(this.user4);
        mockMvc.perform(get("/lobbies/1/kick/4"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "lol is not in this lobby anymore"))
            .andExpect(view().name("redirect:/lobbies/1"));
    }

    @Test
    void testKickPlayerFailNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/555/kick/4"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCreateMatches() throws Exception {
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isFound())
            .andExpect(model().attributeDoesNotExist("message"))
            .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void testGetCreateMatchesFailNotTheHost() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(this.user4);
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "You are not the host of the lobby and therefore cannot start the game"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testGetCreateMatchesFailColors() throws Exception {
        this.lobby2.setPlayers(Lists.newArrayList(this.user3, user4));
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "At least 2 players have the same color, choose different colors"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testGetCreateMatchesFailPlayers() throws Exception {
        this.lobby2.setPlayers(Lists.newArrayList());
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "Not enough players to start the game"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testGetCreateMatchesFailDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/2/createMatch"))
            .andExpect(status().isOk());
            // TODO extend
    }

    @Test
    void testGetAllMatches() throws Exception {
        mockMvc.perform(get("/lobbies/1/matches"))
            .andExpect(model().attributeExists("matches"))
            .andExpect(status().isOk())
            .andExpect(view().name("Matches/MatchesListing"));
    }

    @Test
    void testGetAllMatchesFailDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/555/matches"))
            .andExpect(status().isOk());

            //TODO extend
    }

    @Test
    void testJoinMatchWithColor() throws Exception {
        mockMvc.perform(get("/lobbies/1/YELLOW"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/lobbies/1"));
    }

    @Test
    void testJoinMatchWithColorFail() throws Exception {
        this.lobby2.setPlayers(Lists.newArrayList(this.user3));
        given(this.userService.findUsername(anyString())).willReturn(this.user4);
        mockMvc.perform(get("/lobbies/2/RED"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "RED is already selected"))
            .andExpect(view().name("redirect:/lobbies/2"));
    }

    @Test
    void testJoinMatchWithColorDoesNotExist() throws Exception {
        mockMvc.perform(get("/lobbies/555/RED"))
            .andExpect(status().isFound());
    }
}
