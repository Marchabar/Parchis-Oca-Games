package com.ling1.springmvc.achievements;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
    List<Achievement> findAll();
    @Query("SELECT ach FROM Achievement ach WHERE ach.name= ?1")
    Optional<Achievement> findAchievementByName(String name);
}
