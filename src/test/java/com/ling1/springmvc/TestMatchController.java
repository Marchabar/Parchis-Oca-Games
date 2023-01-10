package com.ling1.springmvc;
import com.ling1.springmvc.chat.MessageChat;
import com.ling1.springmvc.chat.MessageChatService;
import com.ling1.springmvc.chip.Chip;
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
import org.hibernate.internal.ExceptionConverterImpl;
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
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private PlayerStats otherPlayerStats;
    private List<PlayerStats> playerStatsList;

    private  PlayerColor playerColor;
    private List<PlayerColor> playerColors;
    private Chip chip1;

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
        activeMatch.setCheaterCounter(0);
        activeMatch.setLastRoll(5);

        playerStats = new PlayerStats();
        playerStats.setChips(Lists.newArrayList());

        playerStatsList = Lists.newArrayList(playerStats);

        activeMatch.setPlayerStats(playerStatsList);
        playerStats.setUser(loggedUser); // logged in users turn
        playerStats.setPosition(5);
        playerStats.setNumDiceRolls(1);
        playerStats.setNumberOfChipsOut(3);
        activeMatch.setPlayerToPlay(playerStats);
        playerStats.setTurnsStuck(0);
        playerStats.setNumberOfBarrierRebound(0);
        playerColor = new PlayerColor();
        playerColor.setName("green");

        playerStats.setPlayerColor(playerColor);
        playerColors = Lists.newArrayList(playerColor,playerColor);

        otherPlayerStats = new PlayerStats();
        otherPlayerStats.setUser(otherUser);

        messageChat = new MessageChat();
        messageChat.setId(3);
        messageChats = Lists.newArrayList(messageChat);

        tile = new OcaTile();
        tile.setId(1);

        chip1 = new Chip();
        chip1.setAbsolutePosition(3);
        chip1.setChipColor(playerColor);
        chip1.setRelativePosition(0);

        given(this.playerService.findColors()).willReturn(playerColors);
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

    @Test
    void nTestSendChatMessageTooLong() throws Exception {
        String message = "sevilla".repeat(100);
        mockMvc.perform(post("/matches/{matchId}/chat/send",TEST_MATCH_ID)
                .with(csrf())
                .param("description",message)
                .param("time","21:25:55"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "message too long!"))
                .andExpect(view().name("redirect:/matches/" + TEST_MATCH_ID + "/chat/send"));
    }

    @Test
    void testAdvanceParchis() throws Exception {
        mockMvc.perform(get("/matches/1/advanceParchis"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/matches/1/chooseChip"));
    }

    @Test
    void nTestAdvanceParchisNotYourTurn() throws Exception {
        activeMatch.setWinner(playerStats);
        mockMvc.perform(get("/matches/1/advanceParchis"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "It's not your turn"))
                .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void testChooseChip() throws Exception {
        activeMatch.setLastRoll(4);
        Chip chip1 = new Chip();
        chip1.setAbsolutePosition(5);
        chip1.setChipColor(playerColor);
        chip1.setRelativePosition(20);
        activeMatch.getPlayerToPlay().setChips(
            Lists.newArrayList(
                chip1
            )
        );
        mockMvc.perform(get("/matches/1/chooseChip"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(
                    "usersInside", 
                    "messagesChat", 
                    "allParchisTiles", 
                    "loggedUser", 
                    "match"))
                .andExpect(view().name("Matches/ChooseChip"));
    }

    @Test
    void testChooseChipTakeOutChip() throws Exception {
        Chip chip1 = new Chip();
        chip1.setAbsolutePosition(3);
        chip1.setChipColor(playerColor);
        chip1.setRelativePosition(0);
        activeMatch.getPlayerToPlay().setChips(
            Lists.newArrayList(
                chip1
            )
        );
        mockMvc.perform(get("/matches/1/chooseChip"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/matches/1"));

        assertEquals("pedro took out a chip!",activeMatch.getEvent());
    }

    @Test
    void nTestChooseChipMatchDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/chooseChip"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void nTestChooseChipNoChipsSkip() throws Exception {
        activeMatch.getPlayerToPlay().setChips(
            Lists.newArrayList()
        );
        mockMvc.perform(get("/matches/1/chooseChip"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/matches/1"));

        assertEquals("pedro has no chips to play! Turn skipped!", activeMatch.getEvent());
    }

    @Test
    void nTestChooseChipNoChipsRoll() throws Exception {
        activeMatch.setLastRoll(6);
        activeMatch.getPlayerToPlay().setChips(
            Lists.newArrayList()
        );
        mockMvc.perform(get("/matches/1/chooseChip"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/matches/1"));

        assertEquals("pedro has no chips to play, but can roll again!", activeMatch.getEvent());
    }

    @Test
    void nTestChooseChipNotYourTurn() throws Exception {
        activeMatch.setPlayerToPlay(otherPlayerStats);

        mockMvc.perform(get("/matches/1/chooseChip"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "It's not your turn"))
                .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void testChooseChipById() throws Exception {
        given(cs.findById(1)).willReturn(chip1);
        playerStats.setChips(Lists.newArrayList(chip1));

        mockMvc.perform(get("/matches/1/chooseChip/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void nTestChooseChipByIdNotYourChip() throws Exception {
        given(cs.findById(1)).willReturn(chip1);
        playerStats.setChips(Lists.newArrayList());

        mockMvc.perform(get("/matches/1/chooseChip/1"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "It's not your chip"))
                .andExpect(view().name("redirect:/matches/1/chooseChip"));
    }

    @Test
    void nTestChooseChipByIdChipAlreadyWon() throws Exception {
        chip1.setAbsolutePosition(71);
        given(cs.findById(1)).willReturn(chip1);
        playerStats.setChips(Lists.newArrayList(chip1));

        mockMvc.perform(get("/matches/1/chooseChip/1"))
        .andExpect(status().isFound())
        .andExpect(model().attribute("message", "This chip is already won"))
        .andExpect(view().name("redirect:/matches/1/chooseChip"));
    }

    @Test
    void nTestChooseChipByIdNotYourTurn() throws Exception {
        activeMatch.setPlayerToPlay(otherPlayerStats);

        mockMvc.perform(get("/matches/1/chooseChip/1"))
        .andExpect(status().isFound())
        .andExpect(model().attribute("message", "It's not your turn"))
        .andExpect(view().name("redirect:/matches/1"));
    }

    @Test
    void nTestChooseChipByIdMatchDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/555/chooseChip/1"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "Match does not exist"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void nTestChooseChipByIdChipDoesNotExist() throws Exception {
        mockMvc.perform(get("/matches/1/chooseChip/555"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message", "It's not your chip"))
                .andExpect(view().name("redirect:/matches/1/chooseChip"));
    }

}
