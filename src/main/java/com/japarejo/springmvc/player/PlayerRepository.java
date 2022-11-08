package com.japarejo.springmvc.player;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerMatchStats, Integer>{

    @Query("SELECT s FROM PlayerMatchStats s where s.match.id = ?1")
    List<PlayerMatchStats> findAllStatsForMatch(Integer matchID);

    @Query("SELECT s FROM PlayerMatchStats s where s.user.id = ?1")
    List<PlayerMatchStats> findAllStatsForUser(Integer userID);

    @Query("SELECT s FROM PlayerMatchStats s where s.user.id = ?1 AND s.match.id = ?2")
    PlayerMatchStats findStatsForUserAndMatch(Integer userID, Integer matchID);
    
}
