package com.ling1.springmvc.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.lobby.LobbyService;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private SessionRegistry sessionRegistry;
    private LobbyService lobbyService;

    @Autowired
    public UserService(UserRepository userRepository,SessionRegistry sessionRegistry, LobbyService lobbyService){
        this.userRepository = userRepository;
        this.sessionRegistry=sessionRegistry;
        this.lobbyService = lobbyService;
    }
    
    @Transactional(readOnly=true)
    public List<String> getUsersFromSessionRegistry() {
        List<String> sr = sessionRegistry.getAllPrincipals().stream()
          .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
          .map(Object::toString)
          .collect(Collectors.toList());
        
        List<String> usernames = new ArrayList<>();
        for(String s: sr){
            String[] stringSplitted=s.split(" ");
            List<String> stringSplittedList=Arrays.asList(stringSplitted);
            String nameDirty = stringSplittedList.get(stringSplittedList.indexOf("Username:")+1);
            String name = nameDirty.substring(0,nameDirty.length()-1);
            usernames.add(name);
        }
        return usernames;
    }

    @Transactional
    public void changeUsersStatus(List<String> getUsersFromSessionRegistry){
        if(getUsersFromSessionRegistry!=null){
            for(String username: userRepository.findAll().stream().map(x->x.getLogin()).collect(Collectors.toList())){
                if(getUsersFromSessionRegistry.contains(username) || lobbyService.getAllUsersInLobbies().contains(username)){
                    userRepository.findUsername(username).setUserStatus(userRepository.findStatusById(1));
                } else {
                    userRepository.findUsername(username).setUserStatus(userRepository.findStatusById(2));
                }
            }
        } 
    }

    @Transactional(readOnly=true)
    public List<User> getAllUsers(){
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
