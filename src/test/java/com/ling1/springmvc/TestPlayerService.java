package com.ling1.springmvc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

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
    void testSave() {
        PlayerStats stats = new PlayerStats();
        User user = us.findUsername("luke1");
        stats.setUser(user);
        stats.setPlayerColor(ps.red());
        ps.save(stats);
        List<PlayerStats> allStatsForPlayer = ps.giveAllStatsForPlayer(4);
        int expected = 3;
        int actual = allStatsForPlayer.size();
        assertTrue(expected == actual, String.format("Expected the size of allStatsForPlayer to be %d after save of new stat but was %d", expected, actual));
    }

    @Test
    void testGiveAllStatsForPlayer() {
        List<PlayerStats> all = ps.giveAllStatsForPlayer(2);
        int expected = 2;
        int actual = all.size();
        assertTrue(expected == actual, String.format("Expected size of allStatsForPlayer to be %d but was %d", expected, actual));
    }
    
}
