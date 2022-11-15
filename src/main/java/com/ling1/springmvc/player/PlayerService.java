package com.ling1.springmvc.player;

import java.util.Collection;
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

    @Transactional(readOnly = true)
    public List<PlayerStats> giveAllStatsForPlayer(int user_id) {
        return playerRepo.findAllStatsForUser(user_id);
    }

    @Transactional(readOnly = true)
    public Collection<PlayerColor> findColors() throws DataAccessException {
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
    
    
}