package com.ling1.springmvc;
import com.ling1.springmvc.chat.MessageChat;
import com.ling1.springmvc.chat.MessageChatService;
import com.ling1.springmvc.chip.ChipService;
import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchController;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.ocatile.OcaTile;
import com.ling1.springmvc.ocatile.OcaTileService;
import com.ling1.springmvc.ocatile.TileType;
import com.ling1.springmvc.parchistile.ParchisTileService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.user.UserStatusEnum;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MatchController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
        excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestMatchController {

    private final static int TEST_MATCH_ID = 1;
    private final static int TEST_USER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendService friendService;
    @MockBean
    private UserService userService;

    @MockBean
    private MatchService matchService;
    @MockBean
    private MessageChatService messageChatService;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private OcaTileService ocaTileService;

    @MockBean
    private ParchisTileService pts;

    @MockBean
    private ChipService cs;


    private OcaTile tile;

    private User loggedUser;
    private User otherUser;
    private Match activeMatch;
    private List<MessageChat> messageChats;
    private MessageChat messageChat;
    private PlayerStats playerStats;
    private List<PlayerStats> playerStatsList;

    private  PlayerColor playerColor;
    private List<PlayerColor> playerColors;

    @BeforeEach
    void setup()
    {
        loggedUser = new User();
        loggedUser.setId(TEST_USER_ID);
        loggedUser.setLogin("pedro");
        loggedUser.setPassword("123");
        loggedUser.setRole("admin");
        UserStatusEnum us1stat = new UserStatusEnum();
        us1stat.setName("Online");
        loggedUser.setUserStatus(us1stat);

        otherUser = new User();
        otherUser.setId(2);
        otherUser.setLogin("sandra");
        otherUser.setPassword("456");
        otherUser.setRole("member");
        UserStatusEnum us2stat = new UserStatusEnum();
        us1stat.setName("Online");
        otherUser.setUserStatus(us2stat);

        activeMatch = new Match();
        activeMatch.setId(1);

        playerStats = new PlayerStats();

        playerStatsList = Lists.newArrayList(playerStats);

        activeMatch.setPlayerStats(playerStatsList);
        playerStats.setUser(loggedUser); // logged in users turn
        playerStats.setPosition(5);
        playerStats.setNumDiceRolls(1);
        activeMatch.setPlayerToPlay(playerStats);
        playerStats.setTurnsStuck(0);
        playerColor = new PlayerColor();
        playerColor.setName("green");

        playerStats.setPlayerColor(playerColor);
        playerColors = Lists.newArrayList(playerColor,playerColor);

        messageChat = new MessageChat();
        messageChat.setId(3);
        messageChats = Lists.newArrayList(messageChat);

        tile = new OcaTile();
        tile.setId(1);

        given(this.userService.findUsername(anyString())).willReturn(loggedUser); //return logged in user
        given(this.messageChatService.findMatchById(TEST_MATCH_ID)).willReturn(activeMatch); //return match
        given(this.messageChatService.findByMatch(TEST_MATCH_ID)).willReturn(Lists.newArrayList(messageChat)); //return list of message chats
        given(this.matchService.getMatchById(TEST_MATCH_ID)).willReturn(activeMatch); //return the current match

    }
    @Test
    void testGetMatchInside() throws Exception //Test User win
    {
        OcaTile ocaTile = new OcaTile();
        TileType tileType = new TileType();
        tileType.setName("NORMAL");
        ocaTile.setType(tileType);
        List<OcaTile> ocaTiles = Lists.newArrayList(ocaTile);
        playerColors = Lists.newArrayList(playerColor,playerColor,playerColor,playerColor);
        playerStats.setNumberOfPlayerWells(2);
        playerStats.setNumberOfPlayerDeaths(4);
        playerStats.setNumberOfPlayerPrisons(1);
        playerStats.setNumberOfLabyrinths(3);
        playerStats.setNumberOfGooses(2);

        given(this.ocaTileService.findTileTypeByPosition(any())).willReturn(ocaTile);
        given(this.playerService.findColors()).willReturn(playerColors);
        given(this.ocaTileService.getAllTiles()).willReturn(ocaTiles);
        given(this.ocaTileService.allOcas()).willReturn(ocaTiles);

        mockMvc.perform(get("/matches/{matchId}",TEST_MATCH_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedUser",is(loggedUser)))
                .andExpect(model().attribute("match",is(activeMatch)))
                .andExpect(model().attribute("prevPlayer",is(playerStats)))
                .andExpect(model().attribute("allTiles",is(ocaTiles)))
                .andExpect(model().attribute("allOcas",is(ocaTiles)))
                .andExpect(model().attribute("maxGoose",is(2)))
                .andExpect(model().attribute("maxWell",is(2)))
                .andExpect(model().attribute("maxLabyrinth",is(3)))
                .andExpect(model().attribute("maxPrison",is(1)))
                .andExpect(model().attribute("maxDeath",is(4)));

    }

    @Test
    void ntestGetMatchInsideDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(("redirect:/")))
                .andExpect(model().attribute("message",is("The match you tried to join is not active or finished")));
    }



    @Test
    void ntestGetMatchInsideNotSpectate() throws Exception 
    {
        //user which is not a friend cannot spectate the match
        OcaTile ocaTile = new OcaTile();
        TileType tileType = new TileType();
        tileType.setName("NORMAL");
        ocaTile.setType(tileType);
        playerStats.setUser(otherUser); // logged in users turn
        given(this.ocaTileService.findTileTypeByPosition(any())).willReturn(ocaTile);
        given(this.playerService.findColors()).willReturn(playerColors);

        mockMvc.perform(get("/matches/{matchId}",TEST_MATCH_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(("redirect:/")))
                .andExpect(model().attribute("message",is("You are not friend of " + otherUser.getLogin() +" and cannot spectate the match")));
    }
    // Business rules testing: what happens if end up at Bridge, etc. Just Bridge and Oca tested since the others behave the same
    @Test
    void testGetMatchAdvanceBridge() throws Exception
    {
        OcaTile ocaTile = new OcaTile();
        TileType tileType = new TileType();
        tileType.setName("NORMAL");
        ocaTile.setType(tileType);

        given(this.ocaTileService.findTileTypeByPosition(any())).willReturn(ocaTile);
        given(this.playerService.findColors()).willReturn(playerColors);

        mockMvc.perform(get("/matches/{matchId}/advanceOca",TEST_MATCH_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID));

    }
    @Test
    void testGetMatchAdvanceOca() throws Exception
    {
        OcaTile ocaTile = new OcaTile();
        TileType tileType = new TileType();
        tileType.setName("OCA");
        ocaTile.setType(tileType);
        playerStats.setNumberOfGooses(0);

        given(this.ocaTileService.findTileTypeByPosition(any())).willReturn(ocaTile);
        given(this.playerService.findColors()).willReturn(playerColors);

        mockMvc.perform(get("/matches/{matchId}/advanceOca",TEST_MATCH_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID));

    }
    @Test
    void testGetMatchAdvanceOcaEnd() throws Exception
    {
        OcaTile ocaTile = new OcaTile();
        TileType tileType = new TileType();
        tileType.setName("OCA");
        ocaTile.setType(tileType);
        playerStats.setNumberOfGooses(0);

        OcaTile ocaTileNext = new OcaTile();
        TileType tileTypeNext = new TileType();
        tileTypeNext.setName("END");
        ocaTileNext.setType(tileTypeNext);

        given(this.ocaTileService.findTileTypeByPosition(any())).willReturn(ocaTile).willReturn(ocaTileNext); //first tile oca then till END
        given(this.playerService.findColors()).willReturn(playerColors);

        mockMvc.perform(get("/matches/{matchId}/advanceOca",TEST_MATCH_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID));

    }
    @Test
    void ntestGetMatchAdvanceNotYourTurn() throws Exception
    {
        playerStats.setUser(otherUser); // other users turn.
        activeMatch.setPlayerToPlay(playerStats);
        mockMvc.perform(get("/matches/{matchId}/advanceOca",TEST_MATCH_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID))
                .andExpect(model().attribute("message",is("It's not your turn")));
    }

    @Test
    void ntestGetMatchAdvancOcaMatchDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/advanceOca"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void ntestGetMatchAdvancParchiseMatchDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/advanceParchis"))
            .andExpect(status().isFound())
            .andExpect(model().attribute("message", "Match does not exist"))
            .andExpect(view().name("redirect:/"));
    }

    @Test
    void testGetMatchChat() throws Exception
    {
        mockMvc.perform(get("/matches//{matchId}/chat",TEST_MATCH_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messagesChat"))
                .andExpect(model().attributeExists("matchId"))
                .andExpect(model().attribute("matchId",is(1)))
                .andExpect(model().attribute("messagesChat",is(messageChats)))
                .andExpect(view().name("Chats/MessagesListing"));
    }

    @Test
    void nTestGetMatchChatDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/chat"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void testGetCreateMessage() throws Exception
    {
        mockMvc.perform(get("/matches//{matchId}/chat/send",TEST_MATCH_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newMessageChat"))
                .andExpect(model().attributeExists("matchId"))
                .andExpect(model().attribute("matchId",is(1)))
                .andExpect(view().name("Chats/EditMessage"));
    }

    @Test
    void nTestGetCreateMessageDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/chat/send"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void testPostSaveNewMessage() throws Exception
    {
        mockMvc.perform(post("/matches/{matchId}/chat/send",TEST_MATCH_ID)
                .with(csrf())
                .param("description","this is a message from me")
                .param("time","21:25:55"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID));

    }

    @Test
    void nTestPostSaveNewMessageDoesNotExist() throws Exception
    {
        mockMvc.perform(post("/matches/555/chat/send")
                .with(csrf())
                .param("description","this is a message from me")
                .param("time","21:25:55"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }
}
