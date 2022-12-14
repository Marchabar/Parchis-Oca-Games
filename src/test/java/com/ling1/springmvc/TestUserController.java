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
import java.util.ArrayList;
import java.util.List;

import com.ling1.springmvc.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestUserController {

    @MockBean
    UserService userService;

    @MockBean
    UserStatusFormatter form; //needed for enum validation

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
        user2.setRole("mamber");
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
    }/*
    @Test // Test not neeed curretnly since delete function of user disabled.
    // TODO problem here actual value 404 - makes sense since /delete/1/ does not exist
    void testGetDeleteUser() throws Exception {
        mockMvc.perform(get("/users/delete/{id}",TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("User removed successfully")));
    }#*/
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
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","luis")
                .param("password","555")
                .param("role","admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("User saved successfully!")));
    }
    @Test
    // TODO it will be a binding error if the name is empty and the @NOTEMPTY is used
    void ntestPostEditUser() throws Exception {

        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                        .with(csrf())
                        .param("login","")
                        .param("password","555")
                        .param("role","admin"))
                //.andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof Exception))
                .andExpect(mvcResult -> assertNotEquals("",mvcResult.getResponse().getErrorMessage()));
                //.andExpect(model().attribute("message",is("User saved successfully!")));
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
}

