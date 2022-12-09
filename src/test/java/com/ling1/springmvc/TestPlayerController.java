package com.ling1.springmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.assertj.core.util.Lists;

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerController;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PlayerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestPlayerController {

    @MockBean
    PlayerService playerService;

    @MockBean
    UserService userService;

    @MockBean
    MatchService matchService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId(1);

        PlayerStats ps1 = new PlayerStats();
        ps1.setNumDiceRolls(3);
        ps1.setId(1);
        ps1.setNumberOfGooses(2);
        ps1.setNumberOfInns(1);
        ps1.setNumberOfLabyrinths(1);
        ps1.setNumberOfPlayerDeaths(1);
        ps1.setNumberOfPlayerPrisons(1);
        ps1.setNumberOfPlayerWells(1);
        ps1.setPlayerColor(new PlayerColor());
        ps1.setPosition(2);
        ps1.setTurnsStuck(1);
        ps1.setUser(new User());

        when(this.userService.findUsername(anyString())).thenReturn(user);
        when(this.userService.getUserById(anyInt())).thenReturn(user);
        when(this.matchService.findMatchByPlayer(any())).thenReturn(new Match());
        when(this.playerService.giveAllStatsForPlayer(anyInt())).thenReturn(Lists.newArrayList(ps1));
        when(this.playerService.findAll()).thenReturn(Lists.newArrayList(ps1));
    }

    @Test
    public void testPlayerHistory() throws Exception {
        mockMvc.perform(get("/playerstats/history"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("stats"))
            .andExpect(model().attributeExists("matches"))
            .andExpect(status().isOk())
            .andExpect(view().name("Stats/PlayerListing"));
    }

    @Test
    public void testGlobalHistory() throws Exception {
        mockMvc.perform(get("/playerstats/global/history"))
            .andExpect(model().attributeExists("stats"))
            .andExpect(model().attributeExists("matches"))
            .andExpect(status().isOk())
            .andExpect(view().name("Stats/GlobalListing"));
    }

    @Test
    public void testPlayerStats() throws Exception {
        mockMvc.perform(get("/playerstats"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("stat"))
            .andExpect(status().isOk())
            .andExpect(view().name("Stats/PlayerRecord"));
    }

    @Test
    public void testGlobalStats() throws Exception {
        mockMvc.perform(get("/playerstats/global"))
            .andExpect(model().attributeExists("stat"))
            .andExpect(status().isOk())
            .andExpect(view().name("Stats/GlobalRecord"));
    }

    @Test
    public void testRanking() throws Exception {
        mockMvc.perform(get("/playerstats/ranking"))
            .andExpect(model().attributeExists("winners"))
            .andExpect(model().attributeExists("wins"))
            .andExpect(status().isOk())
            .andExpect(view().name("Stats/RankingListing"));
    }
    
}
