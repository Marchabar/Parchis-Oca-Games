package com.ling1.springmvc;

import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.chip.Chip;
import com.ling1.springmvc.chip.ChipRepository;
import com.ling1.springmvc.chip.ChipService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestChipService {

    @Autowired
    ChipService chipService;

    @Autowired
    MatchService matchService;

    @MockBean
    SessionRegistry sr;

    @Test
    void testFindById()
    {
        Chip chip = chipService.findById(5);
        assertNotEquals(null,chip);
        assertEquals(5, chip.getId());
    }

    @Test
    void testSave()
    {
        Chip chip = new Chip();
        chip.setAbsolutePosition(55);
        chip.setRelativePosition(36);
        PlayerColor color = new PlayerColor();
        color.setName("someColor");
        color.setRgb("#f43e3e");
        chip.setChipColor(color);

        chipService.save(chip);


        Chip chipFound = chipService.findById(41); //we know that previously 40 were in the data base
        assertNotEquals(null,chip);
        assertEquals("someColor", chipFound.getChipColor().getName());

    }

    @Test
    void testFindChipInRel()
    {
        Match match = matchService.findMatchByPlayer(11);
        assertNotEquals(null,match);
        
        List<Chip> chips = chipService.findChipInRel(100, match);
        assertNotEquals(null,chips);
        
        assertEquals(6, chips.size());
    }

    @Test
    void testBarrierRebound(){
        Match match = matchService.findMatchByPlayer(10);
        assertNotEquals(null,match);
        
        Chip chip = chipService.findById(5);
        assertNotEquals(null,chip);
        
        assertEquals(21, chipService.barrierRebound(21, match, chip));
    }

}