package com.japarejo.springmvc.user2;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User2Repository extends CrudRepository<User2, Integer>{
    @Query("SELECT status FROM UserStatusEnum status")
    List<UserStatusEnum> findStatus() throws DataAccessException;
    List<User2> findAll();
}
