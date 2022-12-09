package com.ling1.springmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Test
    public void testUserService() {

        testGetAllUsers();
        testDeleteUser();
        testTryToDeleteNotPresentUser(); // Negative
        testFindUsername();
        testTryToFindUsernameNotPresent(); // Negative
        testFindStatus();
        testFindStatusById();
        testTryToFindStatusById(); // Negative
        testAddUser();
        testToAddUserWithSameUserName(); // Negative --> throws DataIntegrityViolationException
    }
    @Test
    public void testGetAllUsers()
    {
        List<User> users = userService.getAllUsers();

        // check whether matches exist in db (only pick 2 matches):
        User testUserA  = users.stream().filter(user -> user.getLogin().equals("pepito")).collect(Collectors.toList()).get(0);
        User testUserB  = users.stream().filter(user -> user.getLogin().equals("Xx_casa777rexpro_xX")).collect(Collectors.toList()).get(0);

        assertTrue(users.size() == 17,
                String.format("Expected number of users: %d but got: %d", 17, users.size()));
        assertEquals(testUserA.getLogin(),"pepito");
        assertEquals(testUserB.getLogin(), "Xx_casa777rexpro_xX");
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

    }
    @Test
    public void testFindStatusById()
    {
        UserStatusEnum userStatusEnumA = userService.findStatusById(1);
        UserStatusEnum userStatusEnumB = userService.findStatusById(2);
        UserStatusEnum userStatusEnumC = userService.findStatusById(3);

        assertEquals("Online",userStatusEnumA.getName());
        assertEquals("Offline",userStatusEnumB.getName());
        assertEquals("Away",userStatusEnumC.getName());
    }
    @Test
    public void testTryToFindStatusById()
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
    public void testTryToFindUsernameNotPresent()
    {
        User user  = userService.findUsername("franz");
        assertEquals(null,user);
    }
    @Test
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
    @Test
    public void testTryToDeleteNotPresentUser()
    {
        assertThrows(EmptyResultDataAccessException.class,()->userService.deleteUser(19));
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
    public void testToAddUserWithSameUserName()
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
