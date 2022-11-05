package com.japarejo.springmvc.matchStatsOca;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchStatsOcaRepository extends CrudRepository<MatchStatsOca, Integer>{
    List<MatchStatsOca> findAll();
}
