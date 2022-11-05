package com.japarejo.springmvc.lobby;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lobbies")
public class LobbyController {

    public static final String LOBBIES_LISTING="LobbiesListing";
    public static final String LOBBY_EDIT="EditLobby";

    @Autowired
    LobbyService lobbyService;
    
	@ModelAttribute("games")
	public Collection<GameEnum> populateGameTypes() {
		return this.lobbyService.findGameTypes();
	}

     @GetMapping
     public ModelAndView showLobbiesListing() {
         ModelAndView result=new ModelAndView(LOBBIES_LISTING);
         result.addObject("lobbies",lobbyService.getAllLobbies());
         return result;
     }

     @GetMapping("/delete/{id}")
     public ModelAndView deleteLobby(@PathVariable("id") int id) {
         lobbyService.deleteLobby(id);
         ModelAndView result=showLobbiesListing();
         result.addObject("message", "Lobby removed successfully");
         return result;
     }
     
     
     @GetMapping("/edit/{id}")
     public ModelAndView editLobby(@PathVariable("id") int id) {
         ModelAndView result=new ModelAndView(LOBBY_EDIT);
         Lobby lobby=lobbyService.getLobbyById(id);
         if(lobby!=null)
             result.addObject("lobby", lobby);
         else{
             result=showLobbiesListing();    }                      
         return result;
     }
     
     @PostMapping("/edit/{id}")
     public ModelAndView editLobby(@PathVariable("id") int id, @Valid Lobby lobby,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(LOBBY_EDIT);
             result.addAllObjects(br.getModel());         
         }else {
             Lobby lobbyToUpdate=lobbyService.getLobbyById(id);
             
             if(lobbyToUpdate!=null) {
                 BeanUtils.copyProperties(lobby, lobbyToUpdate,"id");                 
                 lobbyService.save(lobbyToUpdate);
                 result=showLobbiesListing();
                 result.addObject("message", "Lobby saved succesfully!");
             }else {
                 result=showLobbiesListing();             
                 result.addObject("message", "Lobby with id "+id+" not found!");
             }
         }                                                
         return result;
     }
     /*
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
