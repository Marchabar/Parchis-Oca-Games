package com.japarejo.springmvc.user2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private User2Repository userRepository;

    @Autowired
    public UserService(User2Repository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly=true)
    List<User2> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void save(User2 user){
        userRepository.save(user);
    }
}
