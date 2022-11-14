package com.japarejo.springmvc.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class PlayerStatsOca extends PlayerStats{
    @Column
    private Integer position;
    @Column
    private Integer numberOfGooses;
    @Column
    private Integer numberOfPlayerWells;
    @Column
    private Integer numberOfLabyrinths;
    @Column
    private Integer numberOfPlayerPrisons;
    @Column
    private Integer numberOfPlayerDeaths;

}
