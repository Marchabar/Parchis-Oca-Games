package com.ling1.springmvc.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.user.User;

@Service
public class FriendService {
    
    private FriendRepository friendRepository;
    private LobbyService LobbyService;

    @Autowired
    public FriendService(FriendRepository friendRepository, LobbyService lobbyService){
        this.friendRepository = friendRepository;
        this.LobbyService = lobbyService;
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
    @Transactional(readOnly = true)
    public List<Friend> getMyFriends(User user){
        List<Friend> result = friendRepository.findMyFriends(user);
        return result;
    }

    @Transactional(readOnly = true)
    public Friend getFriendship(User user1, User user2){
        Friend result = friendRepository.findFriendship(user1, user2);
        return result;
    }

    @Transactional(readOnly = true)
    public Boolean areFriends(User user1, User user2){
        if (user1 == user2){
            return true;
        }
        Friend f = friendRepository.findFriendship(user1, user2);
        if (f==null){
            return false;
        }
        else{
            return f.getAccept();
        }
        
    }

    @Transactional(readOnly = true)
    public List<Integer> getLobbiesWithFriendsAvailable(User user){
        List<Friend> friends = friendRepository.findMyFriends(user);
        List<Integer> lobbiesWithFriends = new ArrayList<>();
        for(Friend friend : friends){
            Integer counter=0;
            for (Lobby lb: LobbyService.getAllLobbies()){
                if(((lb.getPlayers().contains(friend.getUser1()) && friend.getUser1()!=user) || (lb.getPlayers().contains(friend.getUser2()) && friend.getUser2()!=user)) && lb.getPlayers().size()!=4){
                    lobbiesWithFriends.add(lb.getId());
                    counter=1;
                }
            }
            if (counter!=1){
                lobbiesWithFriends.add(counter);
            } else {
                counter=0;
            }
        }
        return lobbiesWithFriends;
    }

    @Transactional
    public void save(Friend friend){
        friendRepository.save(friend);
    }
    
}
