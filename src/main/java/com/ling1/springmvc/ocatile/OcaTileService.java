package com.ling1.springmvc.ocatile;

import java.util.ArrayList;
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
	public OcaTile findTileTypeByPosition(Integer pos) throws DataAccessException {
		return ocaTileRepo.findTileTypeByPosition(pos);
	}

	@Transactional 
	public OcaTile save(OcaTile l) {
	    return ocaTileRepo.save(l);
	}
	@Transactional 
	public Integer nextOca(Integer pos) {
	    for (OcaTile t : ocaTileRepo.allOca()){
			if (t.getId()> pos){
				return t.getId();
			}
		}
		return ocaTileRepo.findAll().get(ocaTileRepo.findAll().size()-1).getId();
	}
	@Transactional 
	public List<OcaTile> allOcas() {
	    return ocaTileRepo.allOca();
	}
	@Transactional 
	public Integer otherBridge(Integer pos) {
	    for (OcaTile t : ocaTileRepo.allBridge()){
			if (t.getId()!=pos){
				return t.getId();
			}
		}
		return null;
	}
	public Integer otherDice(Integer pos) {
	    for (OcaTile t : ocaTileRepo.allDice()){
			if (t.getId()!=pos){
				return t.getId();
			}
		}
		return null;
	}
	public List<String> tilesFromPosition(Collection<Integer> positions){
		List<String> tilesInOrder = new ArrayList<String>();
		for (Integer i : positions){
			tilesInOrder.add(ocaTileRepo.findTileTypeByPosition(i).getType().getName());
		}
		return tilesInOrder;
	}
}
