package com.ling1.springmvc;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import com.ling1.springmvc.configuration.SecurityConfiguration;
import com.ling1.springmvc.player.PlayerController;
import com.ling1.springmvc.player.PlayerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PlayerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestPlayerController {

    @MockBean
    PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPlayerController() throws Exception {
        testPlayerHistory();
        testGlobalHistory();
        testPlayerStats();
        testGlobalStats();
    }

    void testPlayerHistory() {
        //TODO implement
    }

    void testGlobalHistory() {
        //TODO implement
    }

    void testPlayerStats() {
        //TODO implement
    }

    void testGlobalStats() {
        //TODO implement
    }
    
}
