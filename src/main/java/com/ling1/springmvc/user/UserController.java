package com.ling1.springmvc.user;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;

@Controller
@RequestMapping("/users")
public class UserController {
    
    public static final String USERS_LISTING="Users/UsersListing";
    public static final String MYPROFILE_LISTING="Users/MyProfile";
    public static final String PROFILE_LISTING="Users/Profile";
    public static final String USER_EDIT="Users/EditUser";
    public static final String PASSWORD_EDIT="Users/EditPassword";
    public static final String REGISTER_EDIT="Users/RegisterUser";
    public static final String WELCOME = "welcome";

    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    MatchService matchService;
    @Autowired
    LobbyService lobbyService;
    @Autowired
    FriendService friendService;

    @ModelAttribute("status")
    public Collection<UserStatusEnum> populateStatus(){
        return this.userService.findStatus();
    }

    @GetMapping
    public ModelAndView showUsersListing(){
        ModelAndView result = new ModelAndView(USERS_LISTING);
        List<String> getUsersFromSessionRegistry = userService.getUsersFromSessionRegistry();
        userService.changeUsersStatus(getUsersFromSessionRegistry);
        result.addObject("users", userService.getAllUsers());
        return result;
    }

    @GetMapping("/myProfile")
    public ModelAndView showMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        return showProfile(loggedUser.getLogin());
    }

    @GetMapping("/profile/{username}")
    public ModelAndView showProfile(@PathVariable("username") String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        User profUser = userService.findUsername(username);
        ModelAndView result = null;
        if(loggedUser==profUser){
            result = new ModelAndView(MYPROFILE_LISTING);
            result.addObject("user", loggedUser);
        } else if (friendService.areFriends(loggedUser, profUser) || (loggedUser.getRole().equals("admin") && profUser != null)){
            result = new ModelAndView(PROFILE_LISTING);
            result.addObject("user", profUser);
        } else {
            result = new ModelAndView("redirect:/");
            result.addObject("message", "You cannot access this profile!");
        }
        return result;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") int id){
        ModelAndView result=new ModelAndView(USER_EDIT);
        User user=userService.getUserById(id);
        if(user!=null){
            result.addObject("user", user);
        } else {
            result = new ModelAndView("redirect:/users");
            result.addObject("message", "User with id "+id+" not found!");
        }
        return result;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") int id, @Valid User user, BindingResult br) {
        ModelAndView result=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser.getId()!=id && loggedUser.getRole().equals("admin")){
            result = new ModelAndView("redirect:/");
                            result.addObject("message", "You cannot edit another user's info!");
                            return result;
        }
        if(br.hasErrors()){
            result=new ModelAndView(USER_EDIT);
            result.addObject("message",br.getModel());
        } else {
            User userToUpdate=userService.getUserById(id);

            if(userToUpdate!=null){
                BeanUtils.copyProperties(user, userToUpdate,"id");
                if ((userToUpdate.getRole().equals("admin") || userToUpdate.getRole().equals("member"))){
                    if (userService.findUsername(user.getLogin())==null || user.getLogin()==loggedUser.getLogin()){
                        if (userService.checkNameHasNoBlankSpaces(user.getLogin())==true) {
                            userService.save(user);
                            result = new ModelAndView(WELCOME);
                            result.addObject("message", "User updated successfully");
                        } else {
                            result = new ModelAndView(USER_EDIT);
                            result.addObject("message", "Username can not contain blank spaces!");
                        } 
                    } else {
                        result = new ModelAndView(USER_EDIT);
                        result.addObject("message", "Username "+user.getLogin()+" is already taken!");
                    }
                }
                else{
                    result = new ModelAndView("redirect:/users");
                    result.addObject("message", "Not valid role");
                }
            } else {
                result = new ModelAndView("redirect:/users");
                result.addObject("message", "User with id "+id+" not found!");
            }
        }
        return result;
    }

    @GetMapping("/editPassword/{id}")
    public ModelAndView editPassword(@PathVariable("id") int id){
        ModelAndView result=new ModelAndView(PASSWORD_EDIT);
        User user=userService.getUserById(id);
        if(user!=null){
            user.setPassword("");
            result.addObject("user", user);
        } else {
            result = new ModelAndView("redirect:/users");
            result.addObject("message", "User with id "+id+" not found!");
        }
        return result;
    }

    @PostMapping("/editPassword/{id}")
    public ModelAndView editPassword(@PathVariable("id") int id, @Valid User user, BindingResult br) {
        ModelAndView result=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser.getId()!=id && loggedUser.getRole().equals("admin")){
            result = new ModelAndView("redirect:/");
                            result.addObject("message", "You cannot edit another user's info!");
                            return result;
        }
        if(br.hasErrors()){
            result=new ModelAndView(USER_EDIT);
            result.addAllObjects(br.getModel());
            return result;
        } 
            User userToUpdate=userService.getUserById(id);

            if(userToUpdate!=null){
                if ((userToUpdate.getRole().equals("admin") || userToUpdate.getRole().equals("member"))){
                            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                            userService.save(user);
                            result = new ModelAndView(WELCOME);
                            result.addObject("message", "User updated successfully");
                }
                else{
                    result = new ModelAndView("redirect:/users");
                    result.addObject("message", "Not valid role");
                }
            } else {
                result = new ModelAndView("redirect:/users");
                result.addObject("message", "User with id "+id+" not found!");
            }
        
        return result;
    }

    @GetMapping("/create")
    public ModelAndView createUser() {
        ModelAndView result = new ModelAndView(USER_EDIT);
        User user = new User();
        result.addObject("user", user);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewUser(@Valid User user, BindingResult br){
        ModelAndView result = null;
        if(br.hasErrors()){
            result=new ModelAndView(USER_EDIT);
            result.addObject(br.getModel());
        } else {
            userService.save(user);
            result=showUsersListing();
            result.addObject("message", "User saved successfully");
        }
        return result;
    }

    @GetMapping("/register")
    public ModelAndView registerUser() {
        ModelAndView result = new ModelAndView(REGISTER_EDIT);
        User user = new User();
        result.addObject("user", user);
        return result;
    }

    @PostMapping("/register")
    public ModelAndView saveNewRegisteredUser(@Valid User user, BindingResult br){
        ModelAndView result = null;
        UserStatusEnum status = userService.findStatusById(2);
        PlayerColor prefColor = playerService.red();
        user.setRole("member");
        user.setUserStatus(status);
        user.setPrefColor(prefColor);
        if(br.hasErrors()){
            result=new ModelAndView(REGISTER_EDIT);
            result.addObject(br.getModel());
        } else if (userService.findUsername(user.getLogin())==null){
            if (userService.checkNameHasNoBlankSpaces(user.getLogin())==true) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                userService.save(user);
                result = new ModelAndView(WELCOME);
                result.addObject("message", "User registered successfully");
            } else {
                result = new ModelAndView(REGISTER_EDIT);
                result.addObject("message", "Username can not contain blank spaces!");
            } 
        } else {
            result = new ModelAndView(REGISTER_EDIT);
            result.addObject("message", "Username "+user.getLogin()+" is already taken!");
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteFriend(@PathVariable("id") int id){
       
        User userToRemove = userService.getUserById(id);
        if (userService.getAllUsers().contains(userToRemove)){
            for (Lobby l : lobbyService.getAllLobbies()){
                if (l.getKickedPlayers().contains(userToRemove)){
                    l.getKickedPlayers().remove(userToRemove);
                }
                if(l.getPlayers().contains(userToRemove)){
                    l.getPlayers().remove(userToRemove);
                }
                if(l.getHost()==userToRemove){
                    l.setHost(null);
                }
                lobbyService.save(l);
            }
            for (PlayerStats ps : playerService.giveAllStatsForPlayer(userToRemove.getId())){
                Match matchCheck = matchService.findMatchByPlayer(ps.getId());
                if ( matchCheck!=null){
                    matchCheck.getPlayerStats().remove(ps);
                    if (matchCheck.getPlayerToPlay()==ps){
                        matchCheck.setPlayerToPlay(null);
                    }
                    if (matchCheck.getWinner()==ps){
                        matchCheck.setWinner(null);
                    }
                matchService.save(matchCheck);
                }
            }
            userService.deleteUser(userToRemove.getId());
            ModelAndView result= new ModelAndView("redirect:/users");
            result.addObject("message", "User " + userToRemove.getLogin() + " removed successfully");
            return result;
        } else {
            ModelAndView result= new ModelAndView("redirect:/users");
            result.addObject("message", "User not found");
            return result;
        }
    }
}
