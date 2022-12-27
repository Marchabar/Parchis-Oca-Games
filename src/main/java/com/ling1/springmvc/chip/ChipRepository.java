package com.ling1.springmvc.chip;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipRepository extends CrudRepository<Chip, Integer>{
    List<Chip> findAll();



}
