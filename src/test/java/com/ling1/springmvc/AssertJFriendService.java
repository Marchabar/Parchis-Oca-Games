package com.ling1.springmvc;

import static org.assertj.core.api.Java6Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class AssertJFriendService {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @Test
    public void testGetAllFriends(){
        List<Friend> friends = friendService.getAllFriends();
        assertThat(friends).size().isEqualTo(8);
    }

    @Test
    public  void testGetFriendById(){
        Friend friend = friendService.getFriendById(3);
        assertThat(friend).isNotNull();
        assertThat(friend.getId()).isEqualTo(3);
    }
    @Test
    public  void testTryToGetFriendByIdNotPresent(){
        Friend friend = friendService.getFriendById(99);
        assertThat(friend).isNull();
    }
    @Test
    public void testGetMyFriends(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotNull();
        List<Friend> friends = friendService.getMyFriends(users.get(0));
        assertThat(friends).isNotNull();
        assertThat(friends).size().isNotEqualTo(0);
        List<Integer> exUserIds = Arrays.asList(new Integer[]{6,7,8,9});
        List<Integer> userIds = friends.stream().map(e -> e.getUser2().getId()).collect(Collectors.toList());
        assertThat(userIds).containsAll(exUserIds);

    }
    @Test
    public void testTryToGetMyFriendsNotPresent(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotNull();
        List<Friend> friends = friendService.getMyFriends(users.get(1));
        assertThat(friends).isNotNull();
        List<Integer> exUserIds = Arrays.asList(new Integer[]{6,7,8,9});
        List<Integer> userIds = friends.stream().map(e -> e.getUser2().getId()).collect(Collectors.toList());
        assertThat(userIds).doesNotContainAnyElementsOf(exUserIds);

    }
    @Test
    public void testGetFriendship(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotNull();
        Friend friend = friendService.getFriendship(users.get(0), users.get(7));
        assertThat(friend).isNotNull();
        assertThat(friend.getDateF().toString()).isEqualTo("2022-03-08");
    }
    @Test
    public void testTryToGetFriendship(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotNull();
        Friend friend = friendService.getFriendship(users.get(2), users.get(7));
        assertThat(friend).isNull();
    }
    
}
