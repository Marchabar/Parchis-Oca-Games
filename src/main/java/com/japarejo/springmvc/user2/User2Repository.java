package com.japarejo.springmvc.user2;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User2Repository extends CrudRepository<User2, Integer>{
    List<User2> findAll();
}
