package com.ling1.springmvc;

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendController;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
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

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FriendController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
        excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestFriendController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FriendService friendService;

    @MockBean
    UserService userService;

    @MockBean
    MatchService matchService;

    @MockBean
    LobbyService ls;


    private static final int TEST_USER_ID = 1;
    private static final int TEST_FRIEND_ID = 1;

    private User user1;
    private User user2;
    private User user3;
    private Friend friend1;
    private Friend friend2;
    private List<Friend> friendListofUser;
    private List<Friend> totalFriendList;

    private Match activeMatch;
    private List<Match> activeMatches;

    @BeforeEach
    void setup(){

        user1 = new User();
        user1.setId(TEST_USER_ID);
        user1.setLogin("pedro");
        user1.setPassword("123");
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

        user3 = new User();
        user3.setId(3);
        user3.setLogin("sandra");
        user3.setPassword("123");
        user3.setRole("member");
        UserStatusEnum us3stat = new UserStatusEnum();
        us3stat.setName("Online");
        user3.setUserStatus(us3stat);

        friend1 = new Friend();
        friend1.setId(1);
        friend1.setUser1(user1);
        friend1.setUser2(user2);
        friend1.setAccept(true);
        friend1.setSolicitingUser(user1); //user 1 asks user 2 for the friendship
        friend1.setDateF(LocalDate.of(2022,12,4));
        friendListofUser = Lists.newArrayList(friend1);
        activeMatch = new Match();
        activeMatch.setId(1);
        activeMatches = Lists.newArrayList(activeMatch);

        friend2 = new Friend();
        friend2.setId(2);
        friend2.setUser1(user2);
        friend2.setUser2(user3);
        friend2.setAccept(true);
        friend2.setSolicitingUser(user3);
        friend2.setDateF(LocalDate.of(2022,10,1));

        totalFriendList = Lists.newArrayList(friend1,friend2);

    }
    @Test
    void  testGetShowFriendsListing() throws Exception //show all friends listing (admin)
    {
        given(this.friendService.getAllFriends()).willReturn(totalFriendList);
        mockMvc.perform(get("/friends"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("friends",is(totalFriendList)));
    }
    @Test
    void testGetShowMyFriendsListing() throws Exception //show only my friends
    {
        given(this.userService.findUsername(anyString())).willReturn(user2);
        given(this.friendService.getMyFriends(user2)).willReturn(friendListofUser);
        given(this.matchService.activeMatchOf(user1)).willReturn(activeMatch);
        mockMvc.perform(get("/friends/myfriends"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("pendingRequest",is(false)))
                .andExpect(model().attribute("activeMatches",is(activeMatches)))
                .andExpect(model().attribute("friends",is(friendListofUser)))
                .andExpect(model().attribute("loggedUser",is(user2)))
                .andExpect(model().attribute("AvailableLobbies",is(user2)))
                .andExpect(view().name("Friends/MyFriendsListing"));
    }
    // TODO works, but if id 99 or any not present is entered in URL, server crashes!!!
    // make it more secure, like reload the page if id not found
    @Test
    void testGetaAcceptFriend() throws Exception
    {
        given(this.userService.findUsername(anyString())).willReturn(user2); //logged in user, since in this friendship user 1 asked user 2 to accept, user 2 must be logged in
        given(this.friendService.getFriendById(TEST_FRIEND_ID)).willReturn(friend1);
        mockMvc.perform(get("/friends/myfriends/accept/{id}",TEST_FRIEND_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/friends/myfriends"))
                .andExpect(model().attribute("message",is("Friend accepted successfully")));
    }
    // TODO works, but if id 99 or any not present is entered in URL, server crashes!!!
    @Test
    void testGetDeleteFriend() throws Exception
    {
        given(this.userService.findUsername(anyString())).willReturn(user1); //any string important since authentication.getName() in controller puts null in
        mockMvc.perform(get("/friends/delete/{id}",TEST_FRIEND_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/friends/myfriends"))
                .andExpect(model().attribute("message",is("Friendship not found")));
    }
    // TODO adapt so that only user can delete own friendships not other!!!
    @Test
    void testGetDeleteFriendNotAdmin() throws Exception
    {
        given(this.userService.findUsername(anyString())).willReturn(user2); //any string important since authentication.getName() in controller puts null in
        mockMvc.perform(get("/friends/delete/{id}",TEST_FRIEND_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/friends/myfriends"))
                .andExpect(model().attribute("message",is("Friendship not found")));
    }
    @Test
    void testGetEditFriend() throws Exception
    {
        given(this.friendService.getFriendById(TEST_FRIEND_ID)).willReturn(friend1);
        mockMvc.perform(get("/friends/edit/{id}",TEST_FRIEND_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("friend"))
                .andExpect(model().attribute("friend",hasProperty("user1",hasProperty("id",is(1))))) //just to check a certain value
                .andExpect(view().name("Friends/EditFriend"));
    }
    @Test
    void ntestGetEditFriendNotFound() throws Exception
    {
        given(this.friendService.getFriendById(99)).willReturn(null);
        mockMvc.perform(get("/friends/edit/{id}",99))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("friends")) // since found friend is null --> showFriendsListing() called which has attribute 'friends'
                .andExpect(view().name("Friends/FriendsListing"));
    }
    // TODO ask @Niclas, maybe he has a solution. Test does not work because conversion error: cannot convert string into user
    @Test
    void testPostEditUser() throws Exception
    {
        given(this.friendService.getFriendById(TEST_FRIEND_ID)).willReturn(friend1);
        mockMvc.perform(post("/friends/edit/{id}",TEST_FRIEND_ID)
                .with(csrf())
                .param("user1.id", "1") //why does it work that way? accorindg to jsp just user1
                .param("user2.id","3")
                //.param("user1.","1s")
                //.param("user2","3")
                .param("accept","false")
                .param("dateF","2021/05/11"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("Friend association saved successfully!")));
    }
    @Test
    void testGetCreateFriend() throws Exception {
        mockMvc.perform(get("/friends/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("friend"));
    }
    @Test
    void testPostSaveNewFriend() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user2).willReturn(user1);
        //.willReturn().willReturn() means: first time calles reaturns .. second time called returns...
        mockMvc.perform(post("/friends/create")
                .with(csrf())
                .param("user2.login","anita"))
                .andExpect(status().is3xxRedirection())  //because if a user if found, redirected
                .andExpect(model().attribute("message",is("Friend request sent successfully")))
                .andExpect(view().name("redirect:/")); //test the redirect
    }
    @Test
    void ntestPostSaveNewFriendNoUserFound() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user2).willReturn(null); // second return null since 'felipe' not found
        mockMvc.perform(post("/friends/create")
                        .with(csrf())
                        .param("user2.login","felipe")
                        .param("dateF","2022/08/06"))
                .andExpect(status().isFound())
                .andExpect(model().attribute("message",is("No user named felipe")))
                .andExpect(view().name("redirect:/")); //test the redirect
    }
    @Test
    void ntestPostSaveNewFriendNotFriendYourself() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user2);
        mockMvc.perform(post("/friends/create")
                        .with(csrf())
                        .param("user2.login",user2.getLogin())) //does not matter which name is input, since the .findUsername function is stubbed
                .andExpect(status().is3xxRedirection())  //because if a user if found, redirected
                .andExpect(model().attribute("message",is("Cannot friend yourself")))
                .andExpect(view().name("redirect:/")); //test the redirect

    }
    @Test
    void ntestPostSaveNewFriendRequestAlreadySent() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user1).willReturn(user2);
        given(friendService.getFriendship(user1,user2)).willReturn(friend1);
        friend1.setAccept(false); //to test if request was already sent
        mockMvc.perform(post("/friends/create")
                        .with(csrf())
                        .param("user2.login",user2.getLogin()))
                .andExpect(status().is3xxRedirection())  //because if a user if found, redirected
                .andExpect(model().attribute("message",is("Request already sent")))
                .andExpect(view().name("redirect:/")); //test the redirect

    }
    @Test
    void ntestPostSaveNewFriendAlreadyFriends() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user1).willReturn(user2);
        given(friendService.getFriendship(user1,user2)).willReturn(friend1);
        friend1.setAccept(true); //to test if request was already sent
        mockMvc.perform(post("/friends/create")
                        .with(csrf())
                        .param("user2.login",user2.getLogin()))
                .andExpect(status().is3xxRedirection())  //because if a user if found, redirected
                .andExpect(model().attribute("message",is("Already friends")))
                .andExpect(view().name("redirect:/")); //test the redirect

    }
}
