package com.japarejo.springmvc.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private final PlayerRepository repo;

    @Autowired
    PlayerService(PlayerRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public PlayerMatchStats save(PlayerMatchStats stats) {
        return this.repo.save(stats);
    }

    @Transactional(readOnly = true)
    public PlayerMatchStats findStatsForPlayerAndMatch(int user_id, int match_id) {
        return this.repo.findStatsForUserAndMatch(user_id, match_id);
    }

    @Transactional(readOnly = true)
    public List<PlayerMatchStats> giveAllStatsForPlayer(int user_id) {
        System.out.print(repo);
        return this.repo.findAllStatsForUser(user_id);
    }
    
}
