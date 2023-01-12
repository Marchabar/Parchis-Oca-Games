package com.ling1.springmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.achievements.Achievement;
import com.ling1.springmvc.achievements.AchievementService;
import com.ling1.springmvc.achievements.AchievementType;


import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class AchievmentServiceTest
{
    @Autowired
    private AchievementService achievementService;

    @MockBean
    SessionRegistry sr;

    @Test
    void testGetAllAchievements()
    {
        List<Achievement> achievements = achievementService.getAllAchievements();
        assertNotNull(achievements);
        assertEquals(40, achievements.size());
    }
    @Test
    void testFindAchievementByName()
    {
        String searchStringA = "Goose 100";
        String searchStringB = "Maze 20";

        Achievement achievementA = achievementService.findAchievementByName(searchStringA);
        Achievement achievementB = achievementService.findAchievementByName(searchStringB);

        assertNotNull(achievementA);
        assertNotNull(achievementB);

        assertEquals(searchStringA, achievementA.getName());
        assertEquals(searchStringB, achievementB.getName());

    }

    @Test
    void ntestFindAchievementWrongName()
    {
        Achievement achievement = achievementService.findAchievementByName("GIZIE 100");
        assertEquals(null,achievement);

    }

    @Test
    void ntestFindAchievementNameNull()
    {
        Achievement achievement  = achievementService.findAchievementByName(null);
        assertEquals(null,achievement);

    }

    @Test
    void testFindTypes()
    {
        List<AchievementType> achievementTypes = achievementService.findTypes();
        assertNotNull(achievementTypes);
        assertEquals(10, achievementTypes.size());
        assertEquals("GOOSE", achievementTypes.get(2).toString());

    }
    @Test
    void testSaveAchievement()
    {
        AchievementType achievementType = new AchievementType();
        achievementType.setName("Won ALL");

        Achievement achievement = new Achievement();
        achievement.setDescription("you only get if you are pro");
        achievement.setName("Best Achievement Ever");
        achievement.setValue(25);
        achievement.setFileImage("someFileImage");
        achievement.setAchievementType(achievementType);

        List<Achievement> achievementsBefore = achievementService.getAllAchievements();
        assertNotNull(achievementsBefore);
        assertEquals(40, achievementsBefore.size());

        achievementService.save(achievement);

        List<Achievement> achievementAfter = achievementService.getAllAchievements();
        assertNotNull(achievementAfter);
        assertEquals(achievementsBefore.size()+1, achievementAfter.size());

    }
}