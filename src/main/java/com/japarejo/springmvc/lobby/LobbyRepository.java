package com.japarejo.springmvc.lobby;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Integer> {
    List<Lobby> findAll();
    @Query("SELECT game FROM GameEnum game ORDER BY game.name")
	List<GameEnum> findLobbyTypes() throws DataAccessException;
}
