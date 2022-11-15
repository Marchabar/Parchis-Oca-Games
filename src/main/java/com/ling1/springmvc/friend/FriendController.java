package com.ling1.springmvc.friend;

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
        return result;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteFriend(@PathVariable("id") int id){
        friendService.deleteFriend(id);
        ModelAndView result = showFriendsListing();
        result.addObject("message", "Friend removed successfully");
        return result;
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
        ModelAndView result = new ModelAndView(FRIEND_EDIT);
        Friend friend = new Friend();
        result.addObject("friend", friend);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewFriend(@Valid Friend friend, BindingResult br){
        ModelAndView result = null;
        if(br.hasErrors()){
            result=new ModelAndView(FRIEND_EDIT);
            result.addObject(br.getModel());
        } else {
            friendService.save(friend);
            result=showFriendsListing();
            result.addObject("message", "Friend saved successfully");
        }
        return result;
    }


}
