package com.ling1.springmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;
import com.ling1.springmvc.user.UserStatusEnum;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestUserService {

    @Autowired
    UserService userService;

    @MockBean
    SessionRegistry sr;

    @Test
    public void testGetAllUsers()
    {
        List<User> users = userService.getAllUsers();

        // check whether matches exist in db (only pick 2 matches):
        User testUserA  = users.stream().filter(user -> user.getLogin().equals("pepito")).collect(Collectors.toList()).get(0);
        User testUserB  = users.stream().filter(user -> user.getLogin().equals("Xx_casa777rexpro_xX")).collect(Collectors.toList()).get(0);

        assertTrue(users.size() == 19,
                String.format("Expected number of users: %d but got: %d", 17, users.size()));
        assertEquals(testUserA.getLogin(),"pepito");
        assertEquals(testUserB.getLogin(), "Xx_casa777rexpro_xX");
    }
    @Test
    public void testDeleteUser()
    {
        List<User> usersBefore = userService.getAllUsers();
        userService.deleteUser(12);
        // to check whether the correct user has been deleted
        User userAfterDelete = userService.getUserById(12);
        assertEquals(null, userAfterDelete);
        // Compare if a user has been deleted
        assertTrue(usersBefore.size()-1 == userService.getAllUsers().size(),
                String.format("Expected number of users: %d but got: %d", userService.getAllUsers().size(), usersBefore.size()-1));

    }
    @Test
    public void ntestTryToDeleteNotPresentUser()
    {
        assertThrows(EmptyResultDataAccessException.class,()->userService.deleteUser(99));
    }
  
    @Test
    public void getUserById()
    {
        User user = userService.getUserById(6);
        assertNotEquals(null, user);
        assertEquals("josemicrack", user.getLogin());
    }
    @Test
    public void testFindStatus()
    {
        // get user status enums from method
       Collection<UserStatusEnum> userStatusEnums = userService.findStatus();
       // get string out of enum
       Collection<String> userStatusStr =userStatusEnums.stream().map(
            e -> e.getName()).collect(Collectors.toSet());

        ArrayList<String> exFindStatus = new ArrayList<>(Arrays.asList("Offline","Online"));
        assertTrue(userStatusStr.containsAll(exFindStatus));
        assertEquals(userStatusStr.size(), exFindStatus.size());

    }
    @Test
    public void testFindStatusById()
    {
        UserStatusEnum userStatusEnumA = userService.findStatusById(1);
        UserStatusEnum userStatusEnumB = userService.findStatusById(2);

        assertEquals("Online",userStatusEnumA.getName());
        assertEquals("Offline",userStatusEnumB.getName());
      
    }
    @Test
    public void ntestTryToFindStatusById()
    {
        UserStatusEnum userStatusEnumA = userService.findStatusById(99);
        assertEquals(null,userStatusEnumA);

    }
    @Test
    public void testFindUsername()
    {
        User user  = userService.findUsername("pisten");
        assertNotEquals(null,user);
        assertEquals("pisten",user.getLogin());
    }
    @Test
    public void ntestTryToFindUsernameNotPresent()
    {
        User user  = userService.findUsername("franz");
        assertEquals(null,user);
    }
    @Test
    public void testCheckNameHasNoBlankSpaces()
    {
        assertEquals(true, userService.checkNameHasNoBlankSpaces("pedro"));   
    }
    @Test
    public void ntestCheckNameHasNoBlankSpaces()
    {
        assertEquals(false, userService.checkNameHasNoBlankSpaces("pedro ku"));   
    }
    @Test
    // if a user is a host of a lobby, he or she cannot be deleted
    public void testAddUser()
    {
        User user = new User();
        user.setPassword("aha123");
        user.setRole("member");
        user.setLogin("hansZimmer");
        UserStatusEnum status = userService.findStatusById(1);
        user.setUserStatus(status);
        userService.save(user);

    }
    @Test
    public void ntestToAddUserWithSameUserName()
    {
        User user = new User();
        user.setPassword("aha123");
        user.setRole("member");
        user.setLogin("pepito");
        UserStatusEnum status = userService.findStatusById(1);
        user.setUserStatus(status);
        assertThrows(DataIntegrityViolationException.class, ()->userService.save(user));
    }
}
