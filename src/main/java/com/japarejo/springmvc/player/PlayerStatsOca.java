package com.japarejo.springmvc.player;

import javax.validation.constraints.PositiveOrZero;

@PositiveOrZero
public class PlayerStatsOca extends PlayerMatchStats{

    private Integer position;
    private Integer numberOfGooses;
    private Integer numberOfPlayerWells;
    private Integer numberOfLabyrinths;
    private Integer numberOfPlayerPrisons;
    private Integer numberOfPlayerDeaths;

}
