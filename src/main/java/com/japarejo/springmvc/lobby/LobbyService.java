package com.japarejo.springmvc.lobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LobbyService {	
	
	
	@Autowired
	private LobbyRepository lobbyRepo;
	
	
	@Transactional(readOnly = true)
	List<Lobby> getAllLobbies(){
	    return lobbyRepo.findAll();
	}

	@Transactional(readOnly = true)
	List<Lobby> getAllOca(){
	    return lobbyRepo.findOca();
	}
	@Transactional(readOnly = true)
	List<Lobby> getAllParchis(){
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

	@Transactional 
	public void save(Lobby l) {
	    lobbyRepo.save(l);
	}


	
}
