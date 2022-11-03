package com.japarejo.springmvc.lobby;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lobbies")
public class LobbyController {

    public static final String LOBBIES_LISTING="LobbiesListing";
    //public static final String ROOM_EDIT="EditRoom";

    @Autowired
    LobbyService lobbyService;
    
     @GetMapping
     public ModelAndView showLobbiesListing() {
         ModelAndView result=new ModelAndView(LOBBIES_LISTING);
         result.addObject("lobbies",lobbyService.getAllLobbies());
         return result;
     }
     /* 
     @GetMapping("/delete/{id}")
     public ModelAndView deleteRoom(@PathVariable("id") int id) {
         lobbyService.deleteRoom(id);
         ModelAndView result=showRoomsListing();
         result.addObject("message", "Room removed successfully");
         return result;
     }
     
     
     @GetMapping("/edit/{id}")
     public ModelAndView editRoom(@PathVariable("id") int id) {
         ModelAndView result=new ModelAndView(ROOM_EDIT);
         Lobby room=lobbyService.getRoomById(id);
         if(room!=null)
             result.addObject("room", room);
         else
             result=showRoomsListing();                          
         return result;
     }
     
     @PostMapping("/edit/{id}")
     public ModelAndView editRoom(@PathVariable("id") int id, @Valid Lobby room,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(ROOM_EDIT);
             result.addAllObjects(br.getModel());         
         }else {
             Lobby roomToUpdate=lobbyService.getRoomById(id);
             
             if(roomToUpdate!=null) {
                 BeanUtils.copyProperties(room, roomToUpdate,"id");                 
                 lobbyService.save(roomToUpdate);
                 result=showRoomsListing();
                 result.addObject("message", "Room saved succesfully!");
             }else {
                 result=showRoomsListing();             
                 result.addObject("message", "Room with id "+id+" not found!");
             }
         }                                                
         return result;
     }
     
     @GetMapping("/create")
     public ModelAndView createRoom() {
         ModelAndView result=new ModelAndView(ROOM_EDIT);
         Lobby room=new Lobby();         
         result.addObject("room", room);                                  
         return result;
     }
     
     
     @PostMapping("/create")
     public ModelAndView saveNewRoom(@Valid Lobby room,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(ROOM_EDIT);
             result.addAllObjects(br.getModel());         
         }else {                          
             lobbyService.save(room);
             result=showRoomsListing();
             result.addObject("message", "Room saved succesfully!");             
         }                                                
         return result;
     }
     */
     
}
