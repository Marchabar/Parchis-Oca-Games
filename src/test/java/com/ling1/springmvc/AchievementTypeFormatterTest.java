package com.ling1.springmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ling1.springmvc.achievements.AchievementService;
import com.ling1.springmvc.achievements.AchievementType;
import com.ling1.springmvc.achievements.AchievementTypeFormatter;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AchievementTypeFormatterTest {

    AchievementTypeFormatter achievementTypeFormatter;
   
    @Mock
    AchievementService achievementService;
    
    @BeforeEach
	void setup() {
		achievementTypeFormatter = new AchievementTypeFormatter(achievementService);
	}
    @Test
    void testPrint()
    {
        AchievementType achievementType = new AchievementType();
        achievementType.setName("newAchievement");
        String name = achievementTypeFormatter.print(achievementType, Locale.ENGLISH);
        assertEquals("newAchievement", name);
    }
    @Test
    void testParse() throws ParseException
    {
		Mockito.when(achievementService.findTypes()).thenReturn((List<AchievementType>)makeAchievementTypes());   
        AchievementType achievementType = achievementTypeFormatter.parse("Advance 10", Locale.ENGLISH);
        assertNotNull(achievementType);
        assertEquals("Advance 10", achievementType.getName());
    }
    @Test
    void ntestParseWrong() throws ParseException
    {
		Mockito.when(achievementService.findTypes()).thenReturn((List<AchievementType>)makeAchievementTypes());   
        assertThrows(ParseException.class, ()->achievementTypeFormatter.parse("SomethingNotExpected", Locale.ENGLISH));
    }

    
    private Collection<AchievementType> makeAchievementTypes() {
		Collection<AchievementType> achievementTypes = new ArrayList<>();
		achievementTypes.add(new AchievementType() {
			{
				setName("Advance 10");
			}
		});
		achievementTypes.add(new AchievementType() {
			{
				setName("Best 100");
			}
		});
		return achievementTypes;
	}
    
}
