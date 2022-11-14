package com.ling1.springmvc.lobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/lobbies")
public class LobbyController {

    public static final String LOBBIES_LISTING="Lobbies/LobbiesListing";
    public static final String LOBBY_EDIT="Lobbies/EditLobby";
    public static final String OCA_LISTING="Lobbies/OcaListing";
    public static final String PARCHIS_LISTING="Lobbies/ParchisListing";
    public static final String LOBBY_INSIDE="Lobbies/InsideLobby";


    //MATCHES DATA
    public static final String MATCHES_LISTING = "Lobbies/MatchesListing";
    public static final String MATCH_EDIT = "Lobbies/EditMatch";


    @Autowired
    LobbyService lobbyService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;



	@ModelAttribute("games")
	public Collection<GameEnum> populateGameTypes() {
		return this.lobbyService.findGameTypes();
	}

     @GetMapping
     public ModelAndView showLobbiesListing() {
         ModelAndView result=new ModelAndView(LOBBIES_LISTING);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
         for (Lobby lobby: lobbyService.getAllLobbies()){
            if(lobby.getPlayers()!=null){
            if (lobby.getPlayers().contains(loggedUser)){
                Collection<User> newPlayers = lobby.getPlayers();
                newPlayers.remove(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
            }
            if (!lobby.getPlayers().contains(lobby.getHost()) ){
                lobbyService.deleteLobby(lobby.getId());
            }
        }
         }
         result.addObject("lobbies",lobbyService.getAllLobbies());
         return result;
     }
     @GetMapping("/oca")
     public ModelAndView showOcaListing() {
         ModelAndView result=new ModelAndView(OCA_LISTING);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
         for (Lobby lobby: lobbyService.getAllLobbies()){
            if (lobby.getPlayers().contains(loggedUser)){
                Collection<User> newPlayers = lobby.getPlayers();
                newPlayers.remove(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
            }
            if (!lobby.getPlayers().contains(lobby.getHost()) ){
                if(!lobby.getPlayers().isEmpty()){
                    lobby.setHost(lobby.getPlayers().stream().findFirst().get());
                    }
                    else if(lobby.getMatches().isEmpty()) {
                        lobbyService.deleteLobby(lobby.getId());
                    }
                    else{
                        lobby.setHost(null);
                    }
                    lobbyService.save(lobby);
                }
         }
         result.addObject("lobbiesOca",lobbyService.getAllOca());
         return result;
     }
     @GetMapping("/parchis")
     public ModelAndView showParchisListing() {
         ModelAndView result=new ModelAndView(PARCHIS_LISTING);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
         for (Lobby lobby: lobbyService.getAllLobbies()){
            if (lobby.getPlayers().contains(loggedUser)){
                Collection<User> newPlayers = lobby.getPlayers();
                newPlayers.remove(loggedUser);
                lobby.setPlayers(newPlayers);
                lobbyService.save(lobby);
            }
            if (!lobby.getPlayers().contains(lobby.getHost()) ){
                if(!lobby.getPlayers().isEmpty()){
                    lobby.setHost(lobby.getPlayers().stream().findFirst().get());
                    }
                    else if(lobby.getMatches().isEmpty()) {
                        lobbyService.deleteLobby(lobby.getId());
                    }
                    else{
                        lobby.setHost(null);
                    }
                    lobbyService.save(lobby);
                }
         }
         result.addObject("lobbiesParchis",lobbyService.getAllParchis());
         return result;
     }

     @GetMapping("/delete/{id}")
     public ModelAndView deleteLobby(@PathVariable("id") int id) {
        GameEnum game = lobbyService.getLobbyById(id).getGame();
        String urlPath =null;
        if (game.getName().contains("O")){
            urlPath = "oca";
        }
        else urlPath = "parchis";

        lobbyService.deleteLobby(id);
         ModelAndView result= new ModelAndView("redirect:/lobbies/"+urlPath);
         result.addObject("message", "Lobby removed successfully");
         return result;
     }
     
     
     @GetMapping("/edit/{id}")
     public ModelAndView editLobby(@PathVariable("id") int id) {
         ModelAndView result=new ModelAndView(LOBBY_EDIT);
         Lobby lobby=lobbyService.getLobbyById(id);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
         if(lobby!=null){
            if(loggedUser==lobby.getHost() || loggedUser.getRole().equals("admin")){
             result.addObject("lobby", lobby);
             result.addObject("game", lobby.getGame().toString().toLowerCase());
            }
            else{
                result=new ModelAndView("redirect:/lobbies/" +lobby.getId().toString());
            }
         }
         else{
             result=showLobbiesListing();
            }                      
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
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             User loggedUser = userService.findUsername(authentication.getName());
             if(lobbyToUpdate!=null ) {
                if(loggedUser==lobbyToUpdate.getHost() || loggedUser.getRole().equals("admin")){
                 lobbyToUpdate.setGame(lobby.getGame());                
                 lobbyService.save(lobbyToUpdate);
                 if(!loggedUser.getRole().equals("admin")){
                 result=new ModelAndView("redirect:/lobbies/" +lobby.getId().toString());
                 }
                 else{
                    result=new ModelAndView("redirect:/lobbies/" +lobby.getGame().toString().toLowerCase());   
                 }
                 result.addObject("message", "Lobby saved succesfully!");
                }else{
                    result=new ModelAndView("redirect:/lobbies/" +lobby.getId().toString());             
                 result.addObject("message", "Lobby with id "+id+" is not yours!");
                }
             }else {
                 result=showLobbiesListing();             
                 result.addObject("message", "Lobby with id "+id+" not found!");
             }
         }                                                
         return result;
     }
     
      @GetMapping("/create")
     public ModelAndView createLobby() {
         ModelAndView result=new ModelAndView(LOBBY_EDIT);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User loggedUser = userService.findUsername(authentication.getName());
        for (Lobby checkLobby : lobbyService.getAllLobbies()){
            if (checkLobby.getPlayers().contains(loggedUser)){
                result=new ModelAndView("redirect:/lobbies/" +checkLobby.getId());
                return result;
            }
         }
         Lobby lobby=new Lobby();
         result.addObject("lobby", lobby);                                  
         return result;
     }
     
     
     @PostMapping("/create")
     public ModelAndView saveNewLobby(@Valid Lobby lobby,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(LOBBY_EDIT);
             result.addAllObjects(br.getModel());         
         }else {             
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            for (Lobby checkLobby : lobbyService.getAllLobbies()){

                if (checkLobby.getPlayers().contains(loggedUser)){
                    result=new ModelAndView("redirect:/lobbies/" +checkLobby.getId());
                    return result;
             }
            }             
            lobby.setHost(loggedUser);
            Collection<User> newPlayers = new ArrayList<User>();
            newPlayers.add(loggedUser);
            lobby.setPlayers(newPlayers);
             lobbyService.save(lobby);
             result=new ModelAndView(LOBBY_INSIDE);
             result.addObject("message", "Lobby saved succesfully!");    
             result.addObject("lobby", lobby);
             result.addObject("players", newPlayers);             
         }                                                
         return result;
     }

     @GetMapping("/createOca")
     public ModelAndView createOca(@Valid Lobby lobby2, BindingResult br) {        
        ModelAndView result = null; 
        if(br.hasErrors()) {
             result = new ModelAndView(LOBBY_INSIDE);
             result.addAllObjects(br.getModel());         
         }else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            for (Lobby checkLobby : lobbyService.getAllLobbies()){
                if (checkLobby.getPlayers().contains(loggedUser)){
                    result=new ModelAndView("redirect:/lobbies/" +checkLobby.getId());
                    return result;
                }
             
            }
            if(lobbyService.getAllLobbies().stream().filter(x->x.getPlayers().isEmpty()).count()==0){
            Lobby lobby = new Lobby();
            for (Lobby checkLobby : lobbyService.getAllLobbies()){
                if (checkLobby.getPlayers().contains(loggedUser)){
                    result=new ModelAndView("redirect:/lobbies/" +checkLobby.getId());
                    return result;
                }
             }
            lobby.setGame(lobbyService.oca());
            lobby.setHost(loggedUser);
            Collection<User> newPlayers = new ArrayList<User>();
            newPlayers.add(loggedUser);
            lobby.setPlayers(newPlayers);
             lobbyService.save(lobby);
             result= new ModelAndView("redirect:/lobbies/" +lobby.getId().toString());
             result.addObject("lobby", lobby);
             result.addObject("players", newPlayers);          
         }
         else{
                Lobby reusedOcaLobby = lobbyService.getLobbyById(lobbyService.getAllLobbies().stream()
                .filter(x->x.getPlayers().isEmpty()).findFirst().get().getId());
                reusedOcaLobby.setGame(lobbyService.oca());
                lobbyService.save(reusedOcaLobby);
                result= new ModelAndView("redirect:/lobbies/" +lobbyService.getAllLobbies().stream()
                .filter(x->x.getPlayers().isEmpty()).findFirst().get().getId());
             } 
        }                                   
         return result;
     }
     @GetMapping("/createParchis")
     public ModelAndView createParchis(@Valid Lobby lobby2, BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(LOBBY_INSIDE);
             result.addAllObjects(br.getModel());         
         }else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedUser = userService.findUsername(authentication.getName());
            for (Lobby checkLobby : lobbyService.getAllLobbies()){

                if (checkLobby.getPlayers().contains(loggedUser)){
                    result=new ModelAndView("redirect:/lobbies/" +checkLobby.getId());
                    return result;
                }

            }
            if(lobbyService.getAllLobbies().stream().filter(x->x.getPlayers().isEmpty()).count()==0){
                Lobby lobby = new Lobby();
               

                lobby.setGame(lobbyService.parchis());
                lobby.setHost(loggedUser);
                Collection<User> newPlayers = new ArrayList<User>();
                newPlayers.add(loggedUser);
                lobby.setPlayers(newPlayers);
                 lobbyService.save(lobby);
                 result= new ModelAndView("redirect:/lobbies/" +lobby.getId().toString());
                 result.addObject("lobby", lobby);
                 result.addObject("players", newPlayers);          
             }
             else{
                Lobby reusedParchisLobby = lobbyService.getLobbyById(lobbyService.getAllLobbies().stream()
                .filter(x->x.getPlayers().isEmpty()).findFirst().get().getId());
                reusedParchisLobby.setGame(lobbyService.parchis());
                lobbyService.save(reusedParchisLobby);
                result= new ModelAndView("redirect:/lobbies/" +lobbyService.getAllLobbies().stream()
                .filter(x->x.getPlayers().isEmpty()).findFirst().get().getId());
             }                   
            }                            
         return result;
     }
     @GetMapping("/{id}")
     public ModelAndView insideLobby(@PathVariable("id") int id) {
        
        ModelAndView result=new ModelAndView(LOBBY_INSIDE);
        Lobby lobby=lobbyService.getLobbyById(id);
        Collection<User> players= lobbyService.findPlayersLobby(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        for (Lobby checklobby: lobbyService.getAllLobbies()){
            if (checklobby.getPlayers().contains(loggedUser)){
                Collection<User> newPlayers = checklobby.getPlayers();
                newPlayers.remove(loggedUser);
                checklobby.setPlayers(newPlayers);
                lobbyService.save(checklobby);
            }
        }
        if(lobby!=null && players!=null){
            result.addObject("lobby", lobby);
            result.addObject("players", players);
            if(players.size()>=4){
                if(lobby.getGame().getName().contains("Oca")){
                    result=showOcaListing();
                }
                else{
                    result=showParchisListing();
                }
                result.addObject("message", "Lobby is full!");     
           }
            if(players.size()==0) {
                lobby.setHost(loggedUser);
                players.add(loggedUser);
                lobby.setPlayers(players);
                lobbyService.save(lobby);
            }else if(players.size()<=3 &&!players.contains(loggedUser)){
                players.add(loggedUser);
                lobby.setPlayers(players);
                lobbyService.save(lobby);
            }
    }
        else{
        result=showLobbiesListing();
       } 
         return result;
     }

     // MATCHES

     @GetMapping("/{id}/matches")
    public ModelAndView showMatchesByLobbyId(@PathVariable("id") Integer id){
        ModelAndView result= new ModelAndView(MATCHES_LISTING);
        result.addObject("matches", matchService.findMatchesByLobbyId(id));
        return result;
    }

    /*@GetMapping("/create")
     public ModelAndView createMatch() {
         ModelAndView result=new ModelAndView(MATCH_EDIT);        
         result.addObject("match",new Match());   
         return result;
     }
     
     
     @PostMapping("/create")
     public ModelAndView saveNewMatch(@Valid Match match,BindingResult br) {        
         ModelAndView result=null;
         if(br.hasErrors()) {
             result=new ModelAndView(MATCH_EDIT);
             result.addAllObjects(br.getModel());         
         }else {                          
             matchService.save(match);
             result=showMatchesListing();
             result.addObject("message", "Match saved succesfully!");             
         }                                                
         return result;
     }*/
     

     
}
