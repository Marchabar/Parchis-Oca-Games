package com.ling1.springmvc.match;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.player.PlayerRepository;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;

@Service
public class MatchService {

    private MatchRepository matchRepo;
    private PlayerRepository playerRepo;

    @Autowired
    public MatchService(MatchRepository matchRepo, PlayerRepository playerRepo){
        this.matchRepo = matchRepo;
        this.playerRepo = playerRepo;
    }

    @Transactional(readOnly = true)
    public List<Match> findAll(){
        return matchRepo.findAll();
    } 

    public void save(Match match){
        matchRepo.save(match);
    }

    @Transactional(readOnly = true)
    public List<Match> findMatchesByLobbyId(Integer id){
        return matchRepo.findMatchesByLobbyId(id);
    }
    @Transactional(readOnly = true)
    public Match getMatchById(int id) {
	    Optional<Match> result=matchRepo.findById(id);
	    return result.isPresent()?result.get():null;         
    }
    @Transactional(readOnly = true)
    public Match activeMatchOf(User user) {
	    for (Match m : matchRepo.findAll()){
            for (PlayerStats ps : m.getPlayerStats()){
                if (ps.getUser()==user && m.getWinner()==null) return m;
            }
        }
        return null;
    }
    @Transactional(readOnly = true)
    public PlayerStats findPlayer(Integer match_id,Integer player_id){
        Match m = matchRepo.findById(match_id).isPresent()?matchRepo.findById(match_id).get():null;
        if (m!=null){
            for (PlayerStats ps :m.getPlayerStats()){
                if (ps.getUser()==playerRepo.findById(player_id).get().getUser()){
                    return ps;
                }
            }
        }
        return null;
    }
    
}
