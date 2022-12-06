package com.ling1.springmvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserStatusEnum;
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

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.user.UserController;
import com.ling1.springmvc.user.UserService;

import org.assertj.core.util.Lists;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestUserController {

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static final int TEST_USER_ID = 1;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setId(TEST_USER_ID);
        user1.setLogin("pedro");
        user1.setPassword("123");
        user1.setRole("admin");
        UserStatusEnum mm = new UserStatusEnum();
        mm.setName("Online");
        user1.setUserStatus(mm);

        user2 = new User();
        user2.setId(2);
        user2.setLogin("sandra");

        given(this.userService.getAllUsers()).willReturn(Lists.newArrayList(user1,user2));
        given(this.userService.getUserById(1)).willReturn(user1);

        //given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(new Owner());
        //given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
    }

    @Test
    void testUserController() throws Exception {
        testGetShowUsersListing();
        testGetDeleteUser();
        testGetEditUser();
        testPostEditUser();
        testGetCreateUser();
        testPostSaveNewUser();
        testPostSaveNewRegisteredUser();
        testGetRegisterUser();
    }
    // TODO ask prof how to deal with lists when expecting values, not done in his examples
    void testGetShowUsersListing() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                //.andExpect(model().attribute("login",hasProperty("pedro")))
                //.andExpect(model().attribute("login",hasProperty("sandra")))
                .andExpect(view().name("Users/UsersListing"));
    }
    // problem here actual value 404 - makes sense since /delete/1/ does not exist
    void testGetDeleteUser() throws Exception {
        mockMvc.perform(get("/users/delete/{id}",TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("User removed successfully")));

    }
    // TODO ask regarding ENUM how to deal with that
    void testGetEditUser() throws Exception {
        UserStatusEnum e = new UserStatusEnum();
        e.setName("Online");
        mockMvc.perform(get("/users/edit/{id}",TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",hasProperty("login", is("pedro"))))
                .andExpect(model().attribute("user",hasProperty("password", is("123"))))
                .andExpect(model().attribute("user",hasProperty("role", is("admin"))))
                //.andExpect(model().attribute("user",hasProperty("userStatus", is(e))))
                ;

    }
    void testPostEditUser() throws Exception {
        mockMvc.perform(post("/users/edit/{id}",TEST_USER_ID)
                .with(csrf())
                .param("login","luis")
                .param("password","555"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message",is("User saved successfully!")));
    }
    void testGetCreateUser()throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk())
                .andExpect(view().name("Users/EditUser"));
    }
    void testPostSaveNewUser() throws Exception {
        UserStatusEnum enums = new UserStatusEnum();
        enums.setName("Online");
        mockMvc.perform(post("/users/create")
                .with(csrf())
                .param("login","alejandro")
                .param("password","465")
                .param("role","member"))
                //.param("userStatus",enums.getName())) // TODO ask regarding enums
                .andExpect(model().attribute("message",is("User saved successfully")))
                .andExpect(status().isOk());
    }

    void testGetRegisterUser() throws Exception{
        mockMvc.perform(get("/users/register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk());
    }
    void testPostSaveNewRegisteredUser() throws Exception{
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .param("login", "realdtrump")
                        .param("password", "MAGA"))
                .andExpect(status().isOk());
    }
}

