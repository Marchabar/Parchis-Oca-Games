package com.ling1.springmvc.chat;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.match.Match;

@Service
public class MessageChatService {

    @Autowired
    private MessageChatRepository messageRepo;

    @Transactional(readOnly = true)
    public List<MessageChat> findAll(){
        return messageRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<MessageChat> findByMatch(Integer matchId){
        return messageRepo.findByMatchId(matchId);
    }

    @Transactional(readOnly = true)
    public Match findMatchById(Integer matchId){
        return messageRepo.findMatchById(matchId);
    }
    
    @Transactional
    public MessageChat save(MessageChat m){
        return messageRepo.save(m);

    }
}
