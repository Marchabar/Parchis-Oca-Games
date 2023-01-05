package com.ling1.springmvc;

import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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


    @Test
    void testGetAllFriends(){
        List<Friend> friends = friendService.getAllFriends();
        assertNotEquals(null,friends);
        assertEquals(8,friends.size());
    }
    @Test
    void testGetFriendById(){
        Friend friend = friendService.getFriendById(3);
        assertNotEquals(null,friend);
        assertEquals(3,friend.getId());

    }
    @Test
    void testTryToGetFriendByIdNotPresent(){
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
    void testTryToGetMyFriendsNotPresent(){
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
    void testTryToGetFriendship(){
        List<User> users = userService.getAllUsers();
        assertNotEquals(null,users);
        Friend friend = friendService.getFriendship(users.get(2), users.get(7));
        assertEquals(null,friend);
    }
}
