package com.ling1.springmvc;

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendController;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserController;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.user.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


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


    private static final int TEST_USER_ID = 1;

    private User user1;
    private User user2;
    private User user3;
    private Friend friend1;
    private Friend friend2;
    private List<Friend> friendList;


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
        user2.setRole("mamber");
        UserStatusEnum us2stat = new UserStatusEnum();
        us2stat.setName("Online");
        user2.setUserStatus(us2stat);

        user3 = new User();
        user3.setId(3);
        user3.setLogin("sandra");
        user3.setPassword("123");
        user3.setRole("mamber");
        UserStatusEnum us3stat = new UserStatusEnum();
        us3stat.setName("Online");
        user3.setUserStatus(us3stat);

        friend1 = new Friend();
        friend1.setId(1);
        friend1.setUser1(user1);
        friend1.setUser2(user2);

    }
    void  testGetShowFriendsListing() throws Exception
    {
        //mockMvc.perform(get("/friends")).andExpect();
    }
    @Test
    void testPostEditUser()
    {

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
                .andExpect(view().name("redirect:/friends/myfriends")); //test the redirect

    }
    @Test
    void ntestPostSaveNewFriendNoUserFound() throws Exception {
        // why subbed here? because needed different returns
        given(this.userService.findUsername(anyString())).willReturn(user2).willReturn(null); // second return null since 'felipe' not found
        mockMvc.perform(post("/friends/create")
                        .with(csrf())
                        .param("user2.login","felipe"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("No user named felipe")))
                .andExpect(view().name("Friends/MyFriendsListing")); //test the redirect

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
                .andExpect(view().name("redirect:/friends/myfriends")); //test the redirect

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
                .andExpect(view().name("redirect:/friends/myfriends")); //test the redirect

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
                .andExpect(view().name("redirect:/friends/myfriends")); //test the redirect

    }
}
