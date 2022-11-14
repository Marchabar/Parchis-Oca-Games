package com.ling1.springmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.match.Match;
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

    @Test
    public void TestUserService() {

        testGetAllUsers();
        testDeleteUser();
        testFindUsername();
        testTryToFindUsernameNotPresent();
        testFindStatus();
        testFindStatusById();
        testAddUser();
        testTryToDeleteNotPresentUser();
        //testToAddUserWithSameId();
    }

    public void testGetAllUsers()
    {
        List<User> users = userService.getAllUsers();

        // check whether matches exist in db (only pick 2 matches):
        User testUserA  = users.stream().filter(user -> user.getLogin().equals("pepito")).collect(Collectors.toList()).get(0);
        User testUserB  = users.stream().filter(user -> user.getLogin().equals("Xx_casa777rexpro_xX")).collect(Collectors.toList()).get(0);

        assertTrue(users.size() == 14,
                String.format("Expected number of users: %d but got: %d", 14, users.size()));
        assertEquals(testUserA.getLogin(),"pepito");
        assertEquals(testUserB.getLogin(), "Xx_casa777rexpro_xX");
    }

    public void testFindStatus()
    {
        // get user status enums from method
       Collection<UserStatusEnum> userStatusEnums = userService.findStatus();
       // get string out of enum
       Collection<String> userStatusStr =userStatusEnums.stream().map(
            e -> e.getName()).collect(Collectors.toSet());

        ArrayList<String> exFindStatus = new ArrayList<>(Arrays.asList("Offline","Online"));
        assertTrue(userStatusStr.containsAll(exFindStatus));

    }
    public void testFindStatusById()
    {
        UserStatusEnum userStatusEnumA = userService.findStatusById(1);
        UserStatusEnum userStatusEnumB = userService.findStatusById(2);

        assertEquals("Online",userStatusEnumA.getName());
        assertEquals("Offline",userStatusEnumB.getName());
    }
    public void testFindUsername()
    {
        User user  = userService.findUsername("pisten");
        assertNotEquals(null,user);
        assertEquals("pisten",user.getLogin());
    }
    public void testTryToFindUsernameNotPresent()
    {
        User user  = userService.findUsername("franz");
        assertEquals(null,user);
    }
    public void testDeleteUser()
    {
        List<User> usersBefore = userService.getAllUsers();
        userService.deleteUser(3);
        // to check whether the correct user has been deleted
        User userAfterDelete = userService.getUserById(3);
        assertEquals(null, userAfterDelete);
        // Compare if a user has been deleted
        assertTrue(usersBefore.size()-1 == userService.getAllUsers().size(),
                String.format("Expected number of users: %d but got: %d", userService.getAllUsers().size(), usersBefore.size()-1));

    }
    public void testTryToDeleteNotPresentUser()
    {
        assertThrows(EmptyResultDataAccessException.class,()->userService.deleteUser(19));
    }
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
    /*
    public void testToAddUserWithSameId()
    {
        User user = new User();
        user.setId(1);
        user.setPassword("aha123");
        user.setRole("member");
        user.setLogin("hansZimmer");
        UserStatusEnum status = userService.findStatusById(1);
        user.setUserStatus(status);
        userService.save(user);


    }*/

}
