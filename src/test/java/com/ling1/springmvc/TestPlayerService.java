package com.ling1.springmvc;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestPlayerService {


    @Autowired
    private PlayerService ps;

    @Autowired
    private UserService us;

    @Test
    void testPlayerService() {
        testGiveAllStatsForPlayer();
        testSave();
    }

    void testSave() {
        PlayerStats stats = new PlayerStats();
        User user = us.findUsername("pepito");
        stats.setUser(user);
        stats.setPlayerColor(ps.red());
        ps.save(stats);
        List<PlayerStats> all = ps.giveAllStatsForPlayer(1);
        assertEquals(3, all.size());
    }


    void testGiveAllStatsForPlayer() {
        List<PlayerStats> all = ps.giveAllStatsForPlayer(2);
        assertEquals(2, all.size());
    }
    
}
