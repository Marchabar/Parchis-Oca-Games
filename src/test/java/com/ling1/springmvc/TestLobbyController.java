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
import com.ling1.springmvc.lobby.LobbyController;
import com.ling1.springmvc.lobby.LobbyService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LobbyController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@WithMockUser(roles="ADMIN")
public class TestLobbyController {
    
    @MockBean
    LobbyService lobbyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLobbyController() throws Exception {
        testShowLobbiesListing();
        testShowOcaListing();
        testShowParchisListing();
        testDeleteLobby();
        testEditLobby();
        testCreateLobby();
        testCreateParchis();
        testGetInsideLobby();
        testShowMatchesByLobbyId();
    }

    void testShowLobbiesListing() {
        //TODO implement
    }

    void testShowOcaListing() {
        //TODO implement
    }

    void testShowParchisListing() {
        //TODO implement
    }

    void testDeleteLobby() {
        //TODO implement
    }

    void testEditLobby() {
        //TODO implement
    }

    void testCreateLobby() {
        //TODO implement
    }

    void testSaveNewLobby() {
        //TODO implement
    }

    void testCreateOca() {
        //TODO implement
    }

    void testCreateParchis() {
        //TODO implement
    }

    void testGetInsideLobby() {
        //TODO implement
    }

    void testShowMatchesByLobbyId() {
        //TODO implement
    }
}
