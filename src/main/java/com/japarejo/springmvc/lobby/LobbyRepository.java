package com.japarejo.springmvc.lobby;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Integer> {
    List<Lobby> findAll();
}
