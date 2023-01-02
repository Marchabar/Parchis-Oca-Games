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
<<<<<<< HEAD
=======
    public Integer winsUser(String name) throws DataAccessException {
        return playerRepo.winsUser(name);
    }
    
    @Transactional(readOnly = true)
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
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
}
