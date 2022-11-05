package com.japarejo.springmvc.match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.japarejo.springmvc.lobby.GameEnum;


@Repository
public interface MatchRepository extends CrudRepository<Match, Integer>{
    List<Match> findAll();


    
}
