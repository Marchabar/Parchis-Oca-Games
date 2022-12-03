package com.ling1.springmvc.ocatile;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcaTileRepository extends CrudRepository<OcaTile, Integer> {
    List<OcaTile> findAll() throws DataAccessException;
    @Query("SELECT type FROM TileType type")
	List<TileType> findOcaTileTypes() throws DataAccessException;
    @Query("SELECT tile FROM OcaTile tile WHERE tile.id = ?1")
    OcaTile findTileTypeByPosition(Integer pos) throws DataAccessException;
    @Query("SELECT tile FROM OcaTile tile WHERE tile.type.id = 2")
    List<OcaTile> allOca() throws DataAccessException;
    @Query("SELECT tile FROM OcaTile tile WHERE tile.type.id = 3")
    List<OcaTile> allBridge() throws DataAccessException;
    @Query("SELECT tile FROM OcaTile tile WHERE tile.type.id = 6")
    List<OcaTile> allDice() throws DataAccessException;
}
