package com.ling1.springmvc.player;


import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends CrudRepository<PlayerStats, Integer>{
    //Repository calls for other methods
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

    //General ranking
    @Query("SELECT m.winner.user.login FROM Match m GROUP BY m.winner.user.login ORDER BY count(m) DESC")
    List<String> rankingByName(); 
    @Query("SELECT count(m.winner) FROM Match m GROUP BY m.winner.user.login ORDER BY count(m) DESC")
    List<Integer> countWinners();
    @Query("SELECT count(m.winner) FROM Match m GROUP BY m.winner.user.login HAVING m.winner.user.login = ?1")
    Integer winsUser(String name);

    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numDiceRolls) DESC")
    List<String> rankingBydiceRolls(); 
    @Query("SELECT SUM(p.numDiceRolls) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numDiceRolls) DESC")
    List<Integer> countnumdiceRolls();

    //Oca ranking
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY 3*SUM(p.numberOfPlayerWells)+4*SUM(p.numberOfPlayerPrisons)+2*SUM(p.numberOfInns) DESC")
    List<String> rankingByNameTurnStuck(); 
    @Query("SELECT 3*SUM(p.numberOfPlayerWells)+4*SUM(p.numberOfPlayerPrisons)+2*SUM(p.numberOfInns) FROM PlayerStats p GROUP BY p.user.login ORDER BY 3*SUM(p.numberOfPlayerWells)+4*SUM(p.numberOfPlayerPrisons)+2*SUM(p.numberOfInns) DESC")
    List<Integer> countTurnStuck();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfGooses) DESC")
    List<String> rankingByGoose(); 
    @Query("SELECT SUM(p.numberOfGooses) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfGooses) DESC")
    List<Integer> countGoose();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerWells) DESC")
    List<String> rankingByWell(); 
    @Query("SELECT SUM(p.numberOfPlayerWells) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerWells) DESC")
    List<Integer> countWell();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfLabyrinths) DESC")
    List<String> rankingByLabyrinth(); 
    @Query("SELECT SUM(p.numberOfLabyrinths) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfLabyrinths) DESC")
    List<Integer> countLabyrinth();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerPrisons) DESC")
    List<String> rankingByPrison(); 
    @Query("SELECT SUM(p.numberOfPlayerPrisons) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerPrisons) DESC")
    List<Integer> countPrison();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerDeaths) DESC")
    List<String> rankingByDeath(); 
    @Query("SELECT SUM(p.numberOfPlayerDeaths) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfPlayerDeaths) DESC")
    List<Integer> countDeath();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfInns) DESC")
    List<String> rankingByInn(); 
    @Query("SELECT SUM(p.numberOfInns) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfInns) DESC")
    List<Integer> countInn();
    //Parchis ranking
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfCheats) DESC")
    List<String> rankingByCheats(); 
    @Query("SELECT SUM(p.numberOfCheats) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfCheats) DESC")
    List<Integer> countCheats();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfChipsOut) DESC")
    List<String> rankingByChipsOut(); 
    @Query("SELECT SUM(p.numberOfChipsOut) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfChipsOut) DESC")
    List<Integer> countChipsOut();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfBarriersFormed) DESC")
    List<String> rankingByBarriersFormed(); 
    @Query("SELECT SUM(p.numberOfBarriersFormed) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfBarriersFormed) DESC")
    List<Integer> countBarriersFormed();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfEndChips) DESC")
    List<String> rankingByEndChips(); 
    @Query("SELECT SUM(p.numberOfEndChips) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfEndChips) DESC")
    List<Integer> countEndChips();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfBarrierRebound) DESC")
    List<String> rankingByBarrierRebound(); 
    @Query("SELECT SUM(p.numberOfBarrierRebound) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfBarrierRebound) DESC")
    List<Integer> countBarrierRebound();
    @Query("SELECT p.user.login FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfChipsEaten) DESC")
    List<String> rankingByChipsEaten(); 
    @Query("SELECT SUM(p.numberOfChipsEaten) FROM PlayerStats p GROUP BY p.user.login ORDER BY SUM(p.numberOfChipsEaten) DESC")
    List<Integer> countChipsEaten();
    
}
