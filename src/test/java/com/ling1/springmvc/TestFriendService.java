package com.ling1.springmvc;

import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestFriendService {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @MockBean
    SessionRegistry sr;

    @Test
    void testGetAllFriends(){
        List<Friend> friends = friendService.getAllFriends();
        assertNotEquals(null,friends);
        assertEquals(13,friends.size());
    }
    @Test
    void testDeleteFriend(){
        List<Friend> friends = friendService.getAllFriends();
        assertNotEquals(null,friends);
        friendService.deleteFriend(3);
        List<Friend> friendsAfterDeleting = friendService.getAllFriends();
        assertEquals(friendsAfterDeleting.size(),friends.size()-1);
    }
    @Test
    void ntestDeleteFriendNotPresent(){
        assertThrows(EmptyResultDataAccessException.class,()->friendService.deleteFriend(99));
    }
    @Test
    void testGetFriendById(){
        Friend friend = friendService.getFriendById(3);
        assertNotEquals(null,friend);
        assertEquals(3,friend.getId());

    }
    @Test
    void ntestTryToGetFriendByIdNotPresent(){
        Friend friend = friendService.getFriendById(99);
        assertEquals(null,friend);
    }
    @Test
    void testGetMyFriends(){
        List<User> users = userService.getAllUsers();
        assertNotEquals(null,users);
        List<Friend> friends = friendService.getMyFriends(users.get(0));
        assertNotEquals(null,friends);
        assertNotEquals(0,friends.size()); //if user.get(0) -> if user has no friends, friends list is empty
        List<Integer> exUserIds = Arrays.asList(new Integer[]{6,7,8,9});
        List<Integer> userIds = friends.stream().map(e -> e.getUser2().getId()).collect(Collectors.toList());
        assertTrue(userIds.containsAll(exUserIds));

    }
    @Test
    void ntestTryToGetMyFriendsNotPresent(){
        List<User> users = userService.getAllUsers();
        assertNotEquals(null,users);
        List<Friend> friends = friendService.getMyFriends(users.get(1));
        assertNotEquals(null,friends);
        List<Integer> exUserIds = Arrays.asList(new Integer[]{6,7,8,9});
        List<Integer> userIds = friends.stream().map(e -> e.getUser2().getId()).collect(Collectors.toList());
        assertFalse(userIds.containsAll(exUserIds));

    }
    @Test
    void testGetFriendship(){
        List<User> users = userService.getAllUsers();
        assertNotEquals(null,users);
        Friend friend = friendService.getFriendship(users.get(0), users.get(7));
        assertNotEquals(null,friend);
        assertEquals("2022-03-08",friend.getDateF().toString());
    }
    @Test
    void ntestTryToGetFriendship(){
        List<User> users = userService.getAllUsers();
        assertNotEquals(null,users);
        Friend friend = friendService.getFriendship(users.get(2), users.get(7));
        assertEquals(null,friend);
    }
    @Test
    void testAreFriends(){
        User userA = userService.getUserById(1);
        User userB = userService.getUserById(4);
        assertNotEquals(null,userA);
        assertNotEquals(null,userB);
        assertEquals(true, friendService.areFriends(userA, userB)); 
    }
    @Test
    void testAreFriendsSameUser(){
        User userA = userService.getUserById(1);
        assertNotEquals(null,userA);
        assertEquals(true, friendService.areFriends(userA, userA)); 
    }
    @Test
    void ntestAreNotFriends(){
        User userA = userService.getUserById(1);
        User userB = userService.getUserById(2);
        assertNotEquals(null,userA);
        assertNotEquals(null,userB);
        assertEquals(false, friendService.areFriends(userA, userB)); 
    }
    @Test
    void ntestAreFriendsUserNotFound(){
        User userA = userService.getUserById(99);
        User userB = userService.getUserById(2);
        assertEquals(false, friendService.areFriends(userA, userB)); 
    }
    @Test
    void testGetLobbiesWithFriendsAvailable()
    {
        User userA = userService.getUserById(1);
        friendService.getLobbiesWithFriendsAvailable(userA);
        List<Friend> friends = friendService.getMyFriends(userA);

    }
}
