package com.ling1.springmvc.achievements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
    
    private AchievementRepository achievementRepository;

    @Autowired 
    public AchievementService(AchievementRepository achievementRepository){
        this.achievementRepository = achievementRepository;
    }

    @Transactional(readOnly=true)
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Achievement findAchievementByName(String name) {
        Optional<Achievement> result = achievementRepository.findAchievementByName(name);
        return result.isPresent()?result.get():null;
    }

    @Transactional(readOnly = true)
    public List<AchievementType> findTypes(){
        return achievementRepository.findTypes();
    }
    
    @Transactional
    public void save(Achievement achievement){
        achievementRepository.save(achievement);
    }
}
