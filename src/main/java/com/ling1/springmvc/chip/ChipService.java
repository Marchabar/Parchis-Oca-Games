package com.ling1.springmvc.chip;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
