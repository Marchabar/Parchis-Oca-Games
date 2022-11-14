package com.ling1.springmvc.lobby;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ling1.springmvc.user.User;

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Integer> {
    @Query("SELECT game FROM GameEnum game")
	List<GameEnum> findLobbyTypes() throws DataAccessException;
    @Query("SELECT lobby FROM Lobby lobby WHERE lobby.game = 1")
    List<Lobby> findOca() throws DataAccessException;
    @Query("SELECT lobby FROM Lobby lobby WHERE lobby.game = 2")
    List<Lobby> findParchis() throws DataAccessException;
    @Query("SELECT o.players FROM Lobby o WHERE o.id = ?1")
    Collection<User> findPlayerLobby(@Param("wa") int wa) throws DataAccessException;
    @Query("SELECT game FROM GameEnum game WHERE game.id = 1")
    GameEnum oca() throws DataAccessException;
    @Query("SELECT game FROM GameEnum game WHERE game.id = 2")
    GameEnum parchis() throws DataAccessException;
    List<Lobby> findAll();
}
