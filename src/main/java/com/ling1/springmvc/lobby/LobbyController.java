package com.ling1.springmvc.lobby;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

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

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.match.MatchService;
import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerService;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

@Controller
@RequestMapping("/lobbies")
public class LobbyController {
    public static int NUM_DICES_SIDES = 6;

    public static final String LOBBIES_LISTING="Lobbies/LobbiesListing";
    public static final String LOBBY_EDIT="Lobbies/EditLobby";
    public static final String OCA_LISTING="Lobbies/OcaListing";
    public static final String PARCHIS_LISTING="Lobbies/ParchisListing";
    public static final String LOBBY_INSIDE="Lobbies/InsideLobby";


    //MATCHES DATA
    public static final String MATCHES_LISTING = "Lobbies/MatchesListing";
    public static final String MATCH_EDIT = "Lobbies/EditMatch";
    public static final String INSIDE_MATCH = "Lobbies/InsideMatch";
    public static final String FINISH_MATCH = "Lobbies/FinishedMatch";


    @Autowired
    LobbyService lobbyService;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;
    @Autowired
    PlayerService playerService;



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
            else {
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
                    } else if(lobbyToUpdate.getHost().getLogin() == loggedUser.getLogin()){
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
            if (checklobby.getPlayers().contains(loggedUser) && checklobby.getId()!=id){
                return new ModelAndView("redirect:/lobbies/" +checklobby.getId());
            }
        }
        if(lobby!=null && players!=null){
            result.addObject("lobby", lobby);
            result.addObject("players", players);
            result.addObject("loggedUser", loggedUser);
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
    
    @GetMapping("/{lobbyId}/{matchId}")
    public ModelAndView matchInside(@PathVariable("lobbyId") Integer lobbyId,
     @PathVariable("matchId") Integer matchId){
        Match currentMatch = matchService.getMatchById(matchId);
        ModelAndView result = null;
        if (currentMatch.getWinner()!=null){
        result= new ModelAndView(FINISH_MATCH);
        }
        else{
        result= new ModelAndView(INSIDE_MATCH);
        }
        result.addObject("match", matchService.getMatchById(matchId));
        return result;
    }

    @GetMapping("/{lobbyId}/{matchId}/advance")
    public ModelAndView matchAdvance(@PathVariable("lobbyId") Integer lobbyId,
     @PathVariable("matchId") Integer matchId){
        Match matchToUpdate =matchService.getMatchById(matchId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUsername(authentication.getName());
        if (loggedUser == matchToUpdate.getPlayerToPlay().getUser() && matchToUpdate.getWinner()==null){
            Integer ColorPosition =playerService.findColors().indexOf(matchToUpdate.getPlayerToPlay().getPlayerColor());
            Integer rolledNumber =1+(int)Math.floor(Math.random()*NUM_DICES_SIDES);
            Integer newPos = matchToUpdate.getPlayerToPlay().getPosition()+rolledNumber;
            if (newPos ==63){
                matchToUpdate.setWinner(matchToUpdate.getPlayerToPlay());
            }
            if (newPos>63){
                newPos = 63 -(newPos-63);
            }
            matchToUpdate.getPlayerToPlay().setPosition(newPos);
            matchToUpdate.getPlayerToPlay().setNumDiceRolls(matchToUpdate.getPlayerToPlay().getNumDiceRolls()+1);
            Boolean assignedNextTurn = false;
            while (!assignedNextTurn){
                if(ColorPosition==3) {
                    matchToUpdate.setNumTurns(matchToUpdate.getNumTurns()+1);
                    ColorPosition=0;
                }
                else ColorPosition++; //this code could be done way cleaner with modulus ((ColorPosition+1)%3); yet to discover why it doesn't work
                PlayerColor colorToTry = playerService.findColors().get((ColorPosition));
                for (PlayerStats ps : matchToUpdate.getPlayerStats()){
                    if (ps.getPlayerColor()==colorToTry) {
                        assignedNextTurn = true;
                        matchToUpdate.setPlayerToPlay(ps);
                    }
                }
            }
            matchService.save(matchToUpdate);
            playerService.save(matchToUpdate.getPlayerToPlay());
            ModelAndView result= new ModelAndView("redirect:/lobbies/"+lobbyId+"/"+matchId);
            return result;
        }
        ModelAndView result= new ModelAndView("redirect:/lobbies/"+lobbyId+"/"+matchId);
        result.addObject("message", "not your turn");
        return result;
        
    }
    @GetMapping("/{lobbyId}/createMatch")
     public ModelAndView createMatch(@Valid PlayerStats ps1, @PathVariable("lobbyId") Integer lobbyId) {
        Match createdMatch = new Match();
        Lobby originalLobby = lobbyService.getLobbyById(lobbyId);
        Collection<PlayerStats> newPlayers = new ArrayList<PlayerStats>();
        for (User u : originalLobby.getPlayers()){
            PlayerStats newPlayer = new PlayerStats();
            newPlayer.setUser(u);
            newPlayer.setNumDiceRolls(0);
            newPlayer.setNumTurnsPlayer(0);
            newPlayer.setNumberOfGooses(0);
            newPlayer.setNumberOfLabyrinths(0);
            newPlayer.setNumberOfPlayerDeaths(0);
            newPlayer.setNumberOfPlayerPrisons(0);
            newPlayer.setNumberOfPlayerWells(0);
            newPlayer.setPosition(0);
           
            playerService.save(newPlayer);
            newPlayers.add(newPlayer);
        }
        createdMatch.setGame(lobbyService.oca());
        createdMatch.setLobby(originalLobby);
        createdMatch.setNumTurns(0);
        createdMatch.setPlayerStats(newPlayers);
        matchService.save(createdMatch);
         ModelAndView result= new ModelAndView("redirect:/lobbies/"+lobbyId+"/"+createdMatch.getId());
         return result;
     }
     
     /* 
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
