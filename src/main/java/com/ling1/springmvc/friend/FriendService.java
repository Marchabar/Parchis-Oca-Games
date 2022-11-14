package com.ling1.springmvc.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {
    
    private FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository){
        this.friendRepository = friendRepository;
    }

    @Transactional(readOnly = true)
    public List<Friend> getAllFriends(){
        return friendRepository.findAll();
    }

    @Transactional
    public void deleteFriend(Integer id){
        friendRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Friend getFriendById(Integer id){
        Optional<Friend> result = friendRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional
    public void save(Friend friend){
        friendRepository.save(friend);
    }
    
}
