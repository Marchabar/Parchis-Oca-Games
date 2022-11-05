package com.japarejo.springmvc.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japarejo.springmvc.lobby.GameEnum;

@Service
public class MatchService {

    private MatchRepository matchRepo;

    @Autowired
    public MatchService(MatchRepository matchRepo){
        this.matchRepo = matchRepo;
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


    
}
