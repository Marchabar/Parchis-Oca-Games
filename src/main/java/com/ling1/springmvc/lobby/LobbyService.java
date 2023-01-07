package com.ling1.springmvc.lobby;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.user.User;


@Service
public class LobbyService {	
	
	
	@Autowired
	private LobbyRepository lobbyRepo;
	
	
	@Transactional(readOnly = true)
	public List<Lobby> getAllLobbies(){
	    return lobbyRepo.findAll();
	}

	@Transactional(readOnly = true)
	public List<Lobby> getAllOca(){
	    return lobbyRepo.findOca();
	}
	@Transactional(readOnly = true)
	public List<Lobby> getAllParchis(){
	    return lobbyRepo.findParchis();
	}
	

	@Transactional
	public void deleteLobby(Integer id) {
	    lobbyRepo.deleteById(id);
	}
	

	@Transactional(readOnly = true)
    public Lobby getLobbyById(int id) {
	    Optional<Lobby> result=lobbyRepo.findById(id);
	    return result.isPresent()?result.get():null;         
    }

	@Transactional(readOnly = true)
	public Collection<GameEnum> findGameTypes() throws DataAccessException {
		return lobbyRepo.findLobbyTypes();
	}
	@Transactional(readOnly = true)
	public GameEnum oca() throws DataAccessException {
		return lobbyRepo.oca();
	}
	@Transactional(readOnly = true)
	public GameEnum parchis() throws DataAccessException {
		return lobbyRepo.parchis();
	}
	@Transactional(readOnly = true)
	public Collection<User> findPlayersLobby(int id) throws DataAccessException {
		return lobbyRepo.findPlayerLobby(id);
	}

	@Transactional(readOnly = true)
	public List<String> getAllUsersInLobbies() throws DataAccessException {
		List<Lobby> lobbies = lobbyRepo.findAll();
		Set<User> usersInLobbies = new HashSet<>();
		for(Lobby lobby: lobbies){
			usersInLobbies.addAll(lobby.getPlayers());
		}
		return usersInLobbies.stream().map(x->x.getLogin()).collect(Collectors.toList());
	}

	@Transactional 
	public Lobby save(Lobby l) {
	    return lobbyRepo.save(l);
	}


	
}
