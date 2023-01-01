package com.ling1.springmvc.achievements;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;


@Component
public class AchievementTypeFormatter implements Formatter<AchievementType> {

    private final AchievementService achievementService;

    @Autowired
    public AchievementTypeFormatter(AchievementService achievementService){
        this.achievementService = achievementService;
    }

    @Override
    public String print(AchievementType object, Locale locale) {
        return object.getName();
    }

    @Override
    public AchievementType parse(String text, Locale locale) throws ParseException {
        List<AchievementType> findTypes = this.achievementService.findTypes();
        for (AchievementType type: findTypes){
            if(type.getName().equals(text)){
                return type;
            }
        }
        throw new ParseException("Status not found: "+text,0);
    }
    
}
