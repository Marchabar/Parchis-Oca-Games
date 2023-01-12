package com.ling1.springmvc;

import com.ling1.springmvc.achievements.Achievement;
import com.ling1.springmvc.achievements.AchievementController;
import com.ling1.springmvc.achievements.AchievementService;
import com.ling1.springmvc.achievements.AchievementType;
import com.ling1.springmvc.achievements.AchievementTypeFormatter;
import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendController;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AchievementController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
        excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class AchievementControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private LobbyService lobbyService;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private AchievementService achievementService;
    @MockBean
    private FriendService friendService;

    @MockBean
    AchievementTypeFormatter form; //needed for enum validation

    @Autowired
    private MockMvc mockMvc;

    private User user1;
    private User user2;
    private static final Integer TEST_USER_ID = 1;
    private static final String TEST_USER_NAME = "sanchez";


    private List<PlayerStats> allStats = new ArrayList<PlayerStats>();
    private PlayerStats playerStats;
    private PlayerColor color;
    private Achievement achievement = new Achievement();

    private List<Achievement> allAchievements = new ArrayList<Achievement>();
    private List<Achievement> myAchievements = new ArrayList<Achievement>();

    @BeforeEach
    void setup() {

        user1 = new User();
        user1.setId(TEST_USER_ID);
        user1.setLogin(TEST_USER_NAME);
        user1.setPassword("456");
        user1.setRole("admin");
        UserStatusEnum us1stat = new UserStatusEnum();
        us1stat.setName("Online");
        user1.setUserStatus(us1stat);

        user2 = new User();
        user2.setId(2);
        user2.setLogin("felipe");
        user2.setPassword("123");
        user2.setRole("member");
        UserStatusEnum us2stat = new UserStatusEnum();
        us2stat.setName("Online");
        user2.setUserStatus(us2stat);

        AchievementType achievementType = new AchievementType();
        achievementType.setName("DICE");
        achievementType.setId(1);

        achievement = new Achievement();
        achievement.setDescription("you only get if you are pro");
        achievement.setName("Roller 25");
        achievement.setValue(25);
        achievement.setFileImage("someFileImage");
        achievement.setAchievementType(achievementType);
        allAchievements.add(achievement);

        playerStats = new PlayerStats();
        color = new PlayerColor();
        color.setName("red");


    }
    @Test
    void testGetShowAchievementsListing() throws Exception
    {

        playerStats.setPlayerColor(color);
        playerStats.setNumDiceRolls(30);
        allStats.add(playerStats);

        given(this.userService.findUsername(anyString())).willReturn(user1,user1);
        given(this.playerService.giveAllStatsForPlayer(1)).willReturn(allStats);
        given(this.achievementService.getAllAchievements()).willReturn(allAchievements);

        myAchievements.add(achievement);

        mockMvc.perform(get("/achievements/{username}",TEST_USER_NAME))
        .andExpect(status().isOk())
        .andExpect(model().attribute("achievements", is(allAchievements)))
        .andExpect(model().attribute("myAchievements", is(myAchievements)))
        .andExpect(model().attribute("loggedUser", is(user1)))
        .andExpect(model().attribute("profUser", is(user1)))
        .andExpect(view().name("Achievements/AchievementsListing"));
    }
    @Test
    void testGetShow3AchievementsListing() throws Exception
    {

        playerStats.setPlayerColor(color);
        playerStats.setNumDiceRolls(30);
        playerStats.setNumberOfPlayerWells(50);
        playerStats.setNumberOfPlayerPrisons(3);
        allStats.add(playerStats);

        /* add achievements */

        AchievementType achievementTypeB = new AchievementType();
        achievementTypeB.setName("WELL");
        achievementTypeB.setId(1);

        Achievement achievementB = new Achievement();
        achievementB.setDescription("you only get if you are pro");
        achievementB.setName("pro achievement");
        achievementB.setValue(40);
        achievementB.setFileImage("blablacar");
        achievementB.setAchievementType(achievementTypeB);
        allAchievements.add(achievementB);

        AchievementType achievementTypeC = new AchievementType();
        achievementTypeC.setName("PRISON");
        achievementTypeC.setId(1);

        Achievement achievementC = new Achievement();
        achievementC.setDescription("you only get if you are prison lover");
        achievementC.setName("jail house rock");
        achievementC.setValue(2);
        achievementC.setFileImage("thisisit");
        achievementC.setAchievementType(achievementTypeC);
        allAchievements.add(achievementC);

        /* add achievements */

        given(this.userService.findUsername(anyString())).willReturn(user1,user1);
        given(this.playerService.giveAllStatsForPlayer(1)).willReturn(allStats);
        given(this.achievementService.getAllAchievements()).willReturn(allAchievements);

        myAchievements.add(achievement);
        myAchievements.add(achievementB);
        myAchievements.add(achievementC);


        mockMvc.perform(get("/achievements/{username}",TEST_USER_NAME))
        .andExpect(status().isOk())
        .andExpect(model().attribute("achievements", is(allAchievements)))
        .andExpect(model().attribute("myAchievements", is(myAchievements)))
        .andExpect(model().attribute("loggedUser", is(user1)))
        .andExpect(model().attribute("profUser", is(user1)))
        .andExpect(view().name("Achievements/AchievementsListing"));
    }
    @Test
    void ntestGetShowAchievementsListingNoAchievements() throws Exception
    {
        given(this.userService.findUsername(anyString())).willReturn(user1,user1);
        mockMvc.perform(get("/achievements"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("message",is("No achievements available")))
        .andExpect(view().name("welcome"));
    }

    @Test
    void ntestGetShowAchievementsListingNoPermission() throws Exception
    {
        given(this.userService.findUsername(anyString())).willReturn(user2,user1);
        given(this.playerService.giveAllStatsForPlayer(1)).willReturn(allStats);

        mockMvc.perform(get("/achievements/{username}",TEST_USER_NAME))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attribute("message",is("You cannot access these achievements!")))
        .andExpect(view().name("redirect:/"));
    }

    @Test
    void testGetCreateAchievement()throws Exception
    {
        mockMvc.perform(get("/achievements/create"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("achievement"))
        .andExpect(view().name("Achievements/EditAchievement"));
    }

    @Test
    void testPostreateAchievement()throws Exception, ParseException
    {
        AchievementType achievementType = new AchievementType();
        achievementType.setName("GOOSE");
        achievementType.setId(1);

        given(this.form.parse(anyString(),any())).willReturn(achievementType); //needed for formatter in user

        mockMvc.perform(post("/achievements/create")
        .with(csrf())
        .param("name","")
        .param("fileImage","CustomAchievement")
        .param("description","")
        .param("achievementType","DICE")
        .param("value","100"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attribute("message",is("Achievement saved successfully")))
        .andExpect(view().name("redirect:/achievements"));
    }

    @Test
    void ntestPostreateAchievementAlreadyExists()throws Exception, ParseException
    {
        AchievementType achievementType = new AchievementType();
        achievementType.setName("DICE");
        achievementType.setId(1);

        given(this.form.parse(anyString(),any())).willReturn(achievementType); //needed for formatter in user
        given(this.achievementService.getAllAchievements()).willReturn(allAchievements);


        mockMvc.perform(post("/achievements/create")
        .with(csrf())
        .param("name","")
        .param("fileImage","CustomAchievement")
        .param("description","")
        .param("achievementType","DICE")
        .param("value","25"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("message",is(achievement.getName() + " already exists!")))
        .andExpect(view().name("Achievements/EditAchievement"));
    }
    
}
