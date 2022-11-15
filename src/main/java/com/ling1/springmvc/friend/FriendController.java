package com.ling1.springmvc.friend;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/friends")
public class FriendController {

    public static final String MYFRIENDS_LISTING="Friends/MyFriendsListing";
    public static final String FRIENDS_LISTING="Friends/FriendsListing";
    public static final String FRIEND_EDIT="Friends/EditFriend";
    public static final String SEND_REQUEST="Friends/SendRequest";

    private FriendService friendService;
    private UserService userService;

    @Autowired
    public FriendController(FriendService friendService, UserService userService){
        this.friendService=friendService;
        this.userService=userService;
    }

    @GetMapping
    public ModelAndView showFriendsListing(){
        ModelAndView result = new ModelAndView(FRIENDS_LISTING);
        result.addObject("friends", friendService.getAllFriends());
        return result;
    }

    @GetMapping("myfriends")
    public ModelAndView showMyFriendsListing(){
        ModelAndView result = new ModelAndView(MYFRIENDS_LISTING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
        result.addObject("friends", friendService.getMyFriends(loggedUser));
        result.addObject("loggedUser", loggedUser);
        return result;
    }
    @GetMapping("myfriends/accept/{id}")
    public ModelAndView acceptFriend(@PathVariable("id") int id){
        ModelAndView result = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
        Friend friendshipToUpdate = friendService.getFriendById(id);
        if ((friendshipToUpdate.getUser1()==loggedUser || friendshipToUpdate.getUser2()==loggedUser)
                &&friendshipToUpdate.getSolicitingUser()!=loggedUser){
            friendshipToUpdate.setAccept(true);
            friendshipToUpdate.setDateF(LocalDate.now());
            friendService.save(friendshipToUpdate);
        }
        result = new ModelAndView("redirect:/friends/myfriends");
        return result;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteFriend(@PathVariable("id") int id){
        friendService.deleteFriend(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser.getRole().equals("admin")){
        ModelAndView result = showFriendsListing();
        result.addObject("message", "Friend removed successfully");
        return result;
        }
        else {
            ModelAndView result= new ModelAndView("redirect:/friends/myfriends");
            return result;
        }
        
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editFriend(@PathVariable("id") int id){
        ModelAndView result=new ModelAndView(FRIEND_EDIT);
        Friend friend = friendService.getFriendById(id);
        if(friend!=null){
            result.addObject("friend", friend);
        } else {
            result = showFriendsListing();
        }
        return result;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") int id, @Valid Friend friend, BindingResult br){
        ModelAndView result = null;
        if(br.hasErrors()){
            result=new ModelAndView(FRIEND_EDIT);
            result.addAllObjects(br.getModel());
        } else {
            Friend friendToUpdate = friendService.getFriendById(id);

            if(friendToUpdate!=null){
                BeanUtils.copyProperties(friend, friendToUpdate, "id");
                friendService.save(friendToUpdate);
                result=showFriendsListing();
                result.addObject("message", "Friend saved successfully!");
            } else {
                result = showFriendsListing();
                result.addObject("message", "Friend with id "+id+" not found");
            }
        }
        return result;
    }   

    @GetMapping("/create")
    public ModelAndView createFriend(){
        ModelAndView result = new ModelAndView(SEND_REQUEST);
        Friend friend = new Friend();
        result.addObject("friend", friend);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewFriend(@Valid Friend friend, BindingResult br){
        ModelAndView result = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
        if(br.hasErrors()){
            result=new ModelAndView(FRIEND_EDIT);
            result.addObject(br.getModel());
        } else {
            User friendToAdd = userService.findUsername(friend.getUser2().getLogin());
            if(friendToAdd==null){
                result=showMyFriendsListing();
                result.addObject("message", "No user named "+ friend.getUser2().getLogin());
                return result;
            }
            if(friendToAdd == loggedUser){
                result=showMyFriendsListing();
                result.addObject("message", "Cannot friend yourself");
                return result;
            }
            if(friendService.getFriendship(loggedUser, friendToAdd)!=null){
                result=showMyFriendsListing();
                if (friendService.getFriendship(loggedUser, friendToAdd).getAccept()==false)
                result.addObject("message", "Request already sent");
                else{
                    result.addObject("message", "Already friends");  
                }
                return result;
            }
            else{
            friend.setUser2(userService.findUsername(friend.getUser2().getLogin()));
            friend.setAccept(false);
            friend.setDateF(null);
            friend.setSolicitingUser(loggedUser);
            friend.setUser1(loggedUser);
            friendService.save(friend);
            result=showFriendsListing();
            result.addObject("message", "Friend saved successfully");
            }
        }
        return result;
    }


}
