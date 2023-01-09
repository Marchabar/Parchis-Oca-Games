package com.ling1.springmvc.player;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class PlayerService {

    @Autowired
	private PlayerRepository playerRepo;


    @Transactional
    public PlayerStats save(PlayerStats stats) {
        return playerRepo.save(stats);
    }

    @Transactional
    public List<PlayerStats> findAll() {
        return playerRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<PlayerStats> giveAllStatsForPlayer(int user_id) {
        return playerRepo.findAllStatsForUser(user_id);
    }

    @Transactional(readOnly = true)
    public List<PlayerColor> findColors() throws DataAccessException {
        return playerRepo.findColorsTypes();
    }

    @Transactional(readOnly = true)
	public PlayerColor red() throws DataAccessException {
		return playerRepo.red();
	}

    @Transactional(readOnly = true)
	public PlayerColor blue() throws DataAccessException {
		return playerRepo.blue();
	}

    @Transactional(readOnly = true)
	public PlayerColor green() throws DataAccessException {
		return playerRepo.green();
	}

    @Transactional(readOnly = true)
	public PlayerColor yellow() throws DataAccessException {
		return playerRepo.yellow();
	}

    @Transactional(readOnly = true)
    public List<String> winnersByName() throws DataAccessException{
        return playerRepo.rankingByName();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> numberWins() throws DataAccessException{
        return playerRepo.countWinners();
    }

    @Transactional(readOnly = true)
    public Integer winsUser(String name) throws DataAccessException {
        Integer result = playerRepo.winsUser(name);
        if(result == null){
            return 0;
        } else{
            return result;
        }
    }
    
    @Transactional(readOnly = true)
    public List<String> rankingByNameTurnStuck() throws DataAccessException{
        return playerRepo.rankingByNameTurnStuck();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countTurnStuck() throws DataAccessException{
        return playerRepo.countTurnStuck();
    }

    @Transactional(readOnly = true)
    public List<String> rankingByGoose() throws DataAccessException{
        return playerRepo.rankingByGoose();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countGoose() throws DataAccessException{
        return playerRepo.countGoose();
    }

    @Transactional(readOnly = true)
    public List<String> rankingByWell() throws DataAccessException{
        return playerRepo.rankingByWell();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countWell() throws DataAccessException{
        return playerRepo.countWell();
    }

    @Transactional(readOnly = true)
    public List<String> rankingByLabyrinth() throws DataAccessException{
        return playerRepo.rankingByLabyrinth();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countLabyrinth() throws DataAccessException{
        return playerRepo.countLabyrinth();
    }

    @Transactional(readOnly = true)
    public List<String> rankingByPrison() throws DataAccessException{
        return playerRepo.rankingByPrison();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countPrison() throws DataAccessException{
        return playerRepo.countPrison();
    }

    @Transactional(readOnly = true)
    public List<String> rankingByDeath() throws DataAccessException{
        return playerRepo.rankingByDeath();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countDeath() throws DataAccessException{
        return playerRepo.countDeath();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByInn() throws DataAccessException{
        return playerRepo.rankingByInn();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countInn() throws DataAccessException{
        return playerRepo.countInn();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByCheats() throws DataAccessException{
        return playerRepo.rankingByCheats();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countCheats() throws DataAccessException{
        return playerRepo.countCheats();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByChipsOut() throws DataAccessException{
        return playerRepo.rankingByChipsOut();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countChipsOut() throws DataAccessException{
        return playerRepo.countChipsOut();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByBarriersFormed() throws DataAccessException{
        return playerRepo.rankingByBarriersFormed();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countBarriersFormed() throws DataAccessException{
        return playerRepo.countBarriersFormed();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByEndChips() throws DataAccessException{
        return playerRepo.rankingByEndChips();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countEndChips() throws DataAccessException{
        return playerRepo.countEndChips();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByBarrierRebound() throws DataAccessException{
        return playerRepo.rankingByBarrierRebound();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countBarrierRebound() throws DataAccessException{
        return playerRepo.countBarrierRebound();
    }
    @Transactional(readOnly = true)
    public List<String> rankingByChipsEaten() throws DataAccessException{
        return playerRepo.rankingByChipsEaten();
    }
    
    @Transactional(readOnly = true)
    public List<Integer> countChipsEaten() throws DataAccessException{
        return playerRepo.countChipsEaten();
    }

}
