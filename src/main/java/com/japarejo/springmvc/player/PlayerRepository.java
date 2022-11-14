package com.japarejo.springmvc.player;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerStats, Integer>{

    @Query("SELECT s FROM PlayerStats s where s.user.id = ?1")
    List<PlayerStats> findAllStatsForUser(Integer userID);
    List<PlayerStats> findAll();
    @Query("SELECT color FROM PlayerColor color")
    List<PlayerColor> findColorsTypes() throws DataAccessException;

}
