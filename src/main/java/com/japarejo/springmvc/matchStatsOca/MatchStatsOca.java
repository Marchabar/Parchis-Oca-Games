package com.japarejo.springmvc.matchStatsOca;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MatchStatsOca {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @Column(unique=true, nullable = false)
    private Integer numMatchSpecialTiles;
    
    @Column(nullable = false)
    private Integer totalDistanceGooses;
}
