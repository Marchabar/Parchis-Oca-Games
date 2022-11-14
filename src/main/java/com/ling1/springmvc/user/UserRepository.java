package com.ling1.springmvc.user;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    @Query("SELECT status FROM UserStatusEnum status")
    List<UserStatusEnum> findStatus();
    @Query("SELECT status FROM UserStatusEnum status WHERE status.id= ?1")
    UserStatusEnum findStatusById(Integer id) throws DataAccessException;
    @Query("SELECT u FROM User u WHERE u.login= ?1")
    User findUsername(@Param("wa") String wa) throws DataAccessException;
    List<User> findAll();
}
