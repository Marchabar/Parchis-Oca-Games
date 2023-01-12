package com.ling1.springmvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ling1.springmvc.user.*;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
// isCollectionContained
import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerColorFormatter;
import com.ling1.springmvc.player.PlayerService;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestUserController {
    
    @MockBean
    UserService userService;

    @MockBean
    MatchService matchService;

    @MockBean
    FriendService friendService;

    @MockBean
    LobbyService lobbyService;

    @MockBean
    UserStatusFormatter form; //needed for enum validation

    @MockBean
    PlayerColorFormatter colorFormatter; //needed for enum validation

    @MockBean
    PlayerService ps;


    @Autowired
    private MockMvc mockMvc;

    private static final int TEST_USER_ID = 1;

    private User user1;
    private User user2;
    private List<User> userlist; // used for validation in the testGetShowUsersListing method

    @BeforeEach
    void setup() throws ParseException {
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
        user2.setLogin("sandra");
        user2.setPassword("123");
        user2.setRole("member");
        UserStatusEnum us2stat = new UserStatusEnum();
        us2stat.setName("Online");
        user2.setUserStatus(us2stat);

        userlist = Lists.newArrayList(user1,user2);

        given(this.userService.getAllUsers()).willReturn(Lists.newArrayList(user1,user2));
        given(this.userService.getUserById(1)).willReturn(user1);
        given(this.userService.findStatus()).willReturn(Lists.newArrayList(us1stat));
        given(this.form.parse(anyString(),anyObject())).willReturn(us1stat); //needed for formatter in user
    }


    @Test
    void testGetShowUsersListing() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",is(userlist)))
                .andExpect(view().name("Users/UsersListing"));
    }

    @Test
    void testGetShowMyProfile() throws Exception{
        given(this.userService.findUsername(anyString())).willReturn(user1);
        mockMvc.perform(get("/users/myProfile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("Users/MyProfile"));
    }

    @Test
    void testGetShowProfile() throws Exception{
        given(this.userService.findUsername(anyString())).willReturn(user1);
        mockMvc.perform(get("/users/profile/{username}",user1.getLogin()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("Users/MyProfile"));
    }
    @Test
    void testGetShowProfileAreFriends() throws Exception{
        given(this.friendService.areFriends(any(),any())).willReturn(true);
        given(this.userService.findUsername(anyString())).willReturn(user2,user1);
        mockMvc.perform(get("/users/profile/{username}",user1.getLogin()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("Users/Profile"));
    }
    @Test
    void ntestGetShowProfileNotAccessable() throws Exception{
        given(this.userService.findUsername(anyString())).willReturn(user2,user1);
        mockMvc.perform(get("/users/profile/{username}",user1.getLogin()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("message","You cannot access this profile!"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void testGetEditUser() throws Exception {
        UserStatusEnum e = new UserStatusEnum();
        e.setName("Online");
        mockMvc.perform(get("/users/edit/{id}",TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",hasProperty("login", is("pedro"))))
                .andExpect(model().attribute("user",hasProperty("password", is("123"))))
                .andExpect(model().attribute("user",hasProperty("role", is("admin"))))
                .andExpect(model().attribute("user",hasProperty("userStatus", is(user1.getUserStatus()))));
    }
    @Test
    void testPostEditUser() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(user1);
        given(this.userService.checkNameHasNoBlankSpaces(anyString())).willReturn(true);
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","luis")
                .param("password","555")
                .param("role","admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("message",is("User updated successfully")));
    }
    @Test
    void ntestPostEditUserBlankSpace() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(user1);
        given(this.userService.checkNameHasNoBlankSpaces(anyString())).willReturn(false);
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","luis jaja")
                .param("password","555")
                .param("role","admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("Users/EditUser"))
                .andExpect(model().attribute("message",is("Username can not contain blank spaces!")));
    }
    @Test
    void ntestPostEditUserNotFound() throws Exception {
        // define logged user
        given(this.userService.findUsername(anyString())).willReturn(user2); 
        given(this.userService.getUserById(1)).willReturn(null);
        given(this.userService.checkNameHasNoBlankSpaces(anyString())).willReturn(true);
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","gffgf")
                .param("password","555")
                .param("role","admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(model().attribute("message",is("User with id "+TEST_USER_ID+" not found!")));
    }
    @Test
    void ntestPostEditUser() throws Exception {
        given(this.userService.findUsername(anyString())).willReturn(user1); 
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                        .with(csrf())
                        .param("login","")
                        .param("password","555")
                        .param("role","admin"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","login"));
    }
    @Test
    void testGetEditPassword() throws Exception
    {
        mockMvc.perform(get("/users/editPassword/{id}",TEST_USER_ID))
        .andExpect(model().attributeExists("user"))
        .andExpect(status().isOk())
        .andExpect(view().name("Users/EditPassword"));
       
    }
    @Test
    void testPostEditPassword() throws Exception {

        UserStatusEnum enums = new UserStatusEnum();
        enums.setName("Online");
        PlayerColor color = new PlayerColor();
        color.setName("green");

        given(this.userService.findUsername(anyString())).willReturn(user1); 
        given(this.userService.getUserById(TEST_USER_ID)).willReturn(user1);
        given(this.colorFormatter.parse(anyString(), any())).willReturn(color);
        mockMvc.perform(post("/users/editPassword/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","franz")
                .param("password","555")
                .param("role","admins")
                .param("prefColor",color.getName())
                .param("userStatus",enums.getName()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message","User updated successfully"))
                .andExpect(view().name("welcome"));
    }
    @Test
    void ntestPostEditPasswordUserNoRole() throws Exception {

        UserStatusEnum enums = new UserStatusEnum();
        enums.setName("Online");
        PlayerColor color = new PlayerColor();
        color.setName("green");
        user1.setRole("player");

        given(this.userService.findUsername(anyString())).willReturn(user1); 
        given(this.userService.getUserById(TEST_USER_ID)).willReturn(user1);
        given(this.colorFormatter.parse(anyString(), any())).willReturn(color);
        mockMvc.perform(post("/users/editPassword/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","franz")
                .param("password","555")
                .param("role","admins")
                .param("prefColor",color.getName())
                .param("userStatus",enums.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("message","Not valid role"))
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    void testGetCreateUser()throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk())
                .andExpect(view().name("Users/EditUser"));
    }
    @Test
    void testPostSaveNewUser() throws Exception {
        UserStatusEnum enums = new UserStatusEnum();
        enums.setName("Online");
        mockMvc.perform(post("/users/create")
                .with(csrf())
                .param("login","alejandro")
                .param("password","465")
                .param("role","member")
                .param("userStatus",enums.getName()))
                .andExpect(model().attribute("message",is("User saved successfully")))
                .andExpect(status().isOk());
    }
    @Test
    void testGetRegisterUser() throws Exception{
        mockMvc.perform(get("/users/register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk());
    }
    @Test
    void testPostSaveNewRegisteredUser() throws Exception{
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .param("login", "realdtrump")
                        .param("password", "MAGA"))
                .andExpect(status().isOk());
    }
    @Test
    void nTestPostSaveNewRegisteredUser() throws Exception{
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .param("login", "pepito")
                        .param("password", "MAGA"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDeleteUser() throws Exception{
        given(this.userService.getUserById(2)).willReturn(user2);
        mockMvc.perform(get("/users/delete/{id}",2))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("message",is("User " + user2.getLogin() + " removed successfully")))
                .andExpect(view().name("redirect:/users"));
    }
    @Test
    void ntestGetDeleteUserNotFound() throws Exception{
        given(this.userService.getUserById(99)).willReturn(null);
        mockMvc.perform(get("/users/delete/{id}",99))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("message",is("User not found")))
                .andExpect(view().name("redirect:/users"));
    }
}

