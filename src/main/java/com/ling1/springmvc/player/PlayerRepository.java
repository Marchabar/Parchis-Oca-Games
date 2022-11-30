package com.ling1.springmvc.player;


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
    @Query("SELECT color FROM PlayerColor color WHERE color.id = 1")
    PlayerColor red() throws DataAccessException;
    @Query("SELECT color FROM PlayerColor color WHERE color.id = 2")
    PlayerColor blue() throws DataAccessException;
    @Query("SELECT color FROM PlayerColor color WHERE color.id = 3")
    PlayerColor green() throws DataAccessException;
    @Query("SELECT color FROM PlayerColor color WHERE color.id = 4")
    PlayerColor yellow() throws DataAccessException;
    @Query("SELECT m.winner.user.login FROM Match m GROUP BY m.winner.user.login ORDER BY count(m) DESC")
    List<String> rankingByName(); 
    @Query("SELECT count(m.winner) FROM Match m GROUP BY m.winner.user.login ORDER BY count(m) DESC")
    List<Integer> countWinners();

}
