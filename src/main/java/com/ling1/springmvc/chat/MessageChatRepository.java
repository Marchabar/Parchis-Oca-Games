package com.ling1.springmvc.chat;

import org.springframework.stereotype.Repository;

import com.ling1.springmvc.match.Match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface MessageChatRepository extends CrudRepository<MessageChat, Integer>{
    List<MessageChat> findAll();

    @Query("SELECT m from MessageChat m where m.match.id = ?1")
    List<MessageChat> findByMatchId(Integer matchId);

    @Query("SELECT m from Match m where m.id = ?1")
    Match findMatchById(Integer matchId);
    
}
