package com.japarejo.springmvc.lobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	/*
	@Transactional
	public void deleteLobby(Integer id) {
	    lobbyRepo.deleteById(id);
	}
	

	@Transactional(readOnly = true)
    public Lobby getRoomById(int id) {
	    Optional<Lobby> result=lobbyRepo.findById((long) id);
	    return result.isPresent()?result.get():null;         
    }
	*/
	@Transactional 
	public void save(Lobby l) {
	    lobbyRepo.save(l);
	}


	
}
