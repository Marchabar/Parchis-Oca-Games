package com.ling1.springmvc.parchistile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisTileService {	
	
	
	@Autowired
	private ParchisTileRepository parchisTileRepo;
	
	
	@Transactional(readOnly = true)
	public List<ParchisTile> getAllTiles(){
	    return parchisTileRepo.findAll();
	}
	

	@Transactional
	public void deleteTile(Integer id) {
	    parchisTileRepo.deleteById(id);
	}
	

	@Transactional(readOnly = true)
    public ParchisTile getOcaTileById(int id) {
	    Optional<ParchisTile> result=parchisTileRepo.findById(id);
	    return result.isPresent()?result.get():null;         
    }

	@Transactional(readOnly = true)
	public Collection<ParchisTileType> findParchisTileTypes() throws DataAccessException {
		return parchisTileRepo.findParchisTileTypes();
	}

	@Transactional(readOnly = true)
	public ParchisTile findTileTypeByPosition(Integer pos) throws DataAccessException {
		return parchisTileRepo.findTileTypeByPosition(pos);
	}

	@Transactional 
	public ParchisTile save(ParchisTile l) {
	    return parchisTileRepo.save(l);
	}

}
