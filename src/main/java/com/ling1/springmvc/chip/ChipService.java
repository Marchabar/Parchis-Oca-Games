package com.ling1.springmvc.chip;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.player.PlayerStats;

@Service
public class ChipService {
    
    @Autowired
	private ChipRepository chipRepo;

    @Transactional 
	public Chip save(Chip l) {
	    return chipRepo.save(l);
	}
	public Chip findById(Integer id) {
		return chipRepo.findById(id).get();
	}
	public Collection<Chip> findChipInRel(Integer relPos, Match m){
		Collection<Chip> chipsInRel = new ArrayList<>();
		for (PlayerStats ps : m.getPlayerStats()){
			for (Chip c :ps.getChips()){
				if (c.getRelativePosition()==relPos){
					chipsInRel.add(c);
				}
			}
		}
		return chipsInRel;
	}
}
