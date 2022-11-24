package com.ling1.springmvc.ocatile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaTileService {	
	
	
	@Autowired
	private OcaTileRepository ocaTileRepo;
	
	
	@Transactional(readOnly = true)
	public List<OcaTile> getAllTiles(){
	    return ocaTileRepo.findAll();
	}
	

	@Transactional
	public void deleteTile(Integer id) {
	    ocaTileRepo.deleteById(id);
	}
	

	@Transactional(readOnly = true)
    public OcaTile getOcaTileById(int id) {
	    Optional<OcaTile> result=ocaTileRepo.findById(id);
	    return result.isPresent()?result.get():null;         
    }

	@Transactional(readOnly = true)
	public Collection<TileType> findOcaTileTypes() throws DataAccessException {
		return ocaTileRepo.findOcaTileTypes();
	}

	@Transactional(readOnly = true)
	public TileType findTileTypeByPosition(Integer pos) throws DataAccessException {
		return ocaTileRepo.findTileTypeByPosition(pos);
	}

	@Transactional 
	public OcaTile save(OcaTile l) {
	    return ocaTileRepo.save(l);
	}


	
}
