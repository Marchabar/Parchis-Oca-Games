package com.japarejo.springmvc.user2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User2 getUserById(int id){
        Optional<User2> result=userRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional(readOnly = true)
    public Collection<UserStatusEnum> findStatus() throws DataAccessException{
        return userRepository.findStatus();
    }
    
    @Transactional
    public void save(User2 user){
        userRepository.save(user);
    }
}
