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
    
}
