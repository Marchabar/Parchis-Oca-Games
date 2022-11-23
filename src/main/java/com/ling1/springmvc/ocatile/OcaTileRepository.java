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
    @Query("SELECT type FROM TileType type WHERE type.id = ?1")
    TileType findTileTypeByPosition(Integer pos) throws DataAccessException;

}
