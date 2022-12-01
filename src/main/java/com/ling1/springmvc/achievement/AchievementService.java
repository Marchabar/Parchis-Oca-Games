package com.ling1.springmvc.achievement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    
    @Autowired
    private AchievementRepository rep;

    List<Achievement> getAllAchievements() {
        List<Achievement> result = new ArrayList<Achievement>();
        rep.findAll().forEach(result::add);
        return result;
    }

    void deleteAchievementById(int id) {
        rep.deleteById(id);
    }

    Achievement save(Achievement achievement) {
        return rep.save(achievement);
    }
}
