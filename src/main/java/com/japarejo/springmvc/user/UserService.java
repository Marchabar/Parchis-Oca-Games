package com.japarejo.springmvc.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly=true)
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getUserById(int id){
        Optional<User> result=userRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional(readOnly = true)
    public Collection<UserStatusEnum> findStatus() throws DataAccessException{
        return userRepository.findStatus();
    }

    @Transactional(readOnly = true)
    public UserStatusEnum findStatusById(Integer id) throws DataAccessException{
        return userRepository.findStatusById(id);
    }

    @Transactional(readOnly = true)
    public User findUsername(String wa) throws DataAccessException{
        return userRepository.findUsername(wa);
    }


    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}
