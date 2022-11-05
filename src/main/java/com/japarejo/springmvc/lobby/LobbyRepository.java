package com.japarejo.springmvc.lobby;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Integer> {
    @Query("SELECT game FROM GameEnum game")
	List<GameEnum> findLobbyTypes() throws DataAccessException;
    List<Lobby> findAll();
}
