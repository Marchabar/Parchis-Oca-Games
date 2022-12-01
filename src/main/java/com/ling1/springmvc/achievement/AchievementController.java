package com.ling1.springmvc.achievement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    private static final String ACHIEVEMENT_LISTING = "Achievements/ListAllAchievements";

    @Autowired
    AchievementService aService;

    @GetMapping
    public ModelAndView showAllAvailableAchievements(){
        ModelAndView result = new ModelAndView(ACHIEVEMENT_LISTING);
        List<Achievement> achievements = aService.getAllAchievements();
        result.addObject("achievements", achievements);
        return result;
    }
    
}
