package com.ling1.springmvc.achievement;

import org.springframework.stereotype.Repository;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    
}
