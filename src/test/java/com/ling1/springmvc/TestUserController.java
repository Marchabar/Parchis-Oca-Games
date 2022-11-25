package com.ling1.springmvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testUserController() throws Exception {
        testRegisterUser();
    }

    void testRegisterUser() throws Exception{
        mockMvc.perform(post("/users/register")
                            .with(csrf())
                            .param("login", "realdtrump")
                            .param("password", "MAGA"))
                .andExpect(status().isOk());
    }
}

