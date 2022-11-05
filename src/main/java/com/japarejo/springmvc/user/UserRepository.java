package com.japarejo.springmvc.user;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    @Query("SELECT status FROM UserStatusEnum status")
    List<UserStatusEnum> findStatus() throws DataAccessException;
    List<User> findAll();
}
