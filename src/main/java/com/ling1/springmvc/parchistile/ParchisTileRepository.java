package com.ling1.springmvc.parchistile;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParchisTileRepository extends CrudRepository<ParchisTile, Integer> {
    List<ParchisTile> findAll() throws DataAccessException;
    @Query("SELECT type FROM ParchisTileType type")
	List<ParchisTileType> findParchisTileTypes() throws DataAccessException;
    @Query("SELECT tile FROM ParchisTile tile WHERE tile.id = ?1")
    ParchisTile findTileTypeByPosition(Integer pos) throws DataAccessException;

}
