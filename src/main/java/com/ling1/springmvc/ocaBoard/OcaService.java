package com.ling1.springmvc.ocaBoard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaService {

    @Autowired
    private OcaRepository ocaRepo;

    @Transactional(readOnly = true)
    public Optional<OcaBoard> findById(Integer id){
        return ocaRepo.findById(id);
    }
    
}
