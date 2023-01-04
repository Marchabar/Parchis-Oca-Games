package com.ling1.springmvc.player;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.ling1.springmvc.chip.Chip;
import com.ling1.springmvc.user.User;

@Entity
@Getter
@Setter

public class PlayerStats {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Integer id;

  @PositiveOrZero // can be 0, if game quit
  @Column(name = "numDiceRolls")
  private Integer numDiceRolls;

  @ManyToOne
  @JoinColumn(name = "playerColor_id")
  private PlayerColor playerColor;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @PositiveOrZero
  @Column(name = "position")
  private Integer position;

  @PositiveOrZero
  @Column(name = "turnsStuck")
  private Integer turnsStuck;

  @Column
  @PositiveOrZero
  private Integer numberOfGooses;

  @Column
  @PositiveOrZero
  private Integer numberOfPlayerWells;

  @Column
  @PositiveOrZero
  private Integer numberOfLabyrinths;

  @Column
  @PositiveOrZero
  private Integer numberOfPlayerPrisons;

  @Column
  @PositiveOrZero
  private Integer numberOfPlayerDeaths;
  
  @Column
  @PositiveOrZero
  private Integer numberOfInns;

  
  @OneToMany
  private List<Chip> chips;

  @Column
  @PositiveOrZero
  private Integer numberOfCheats;

  @Column
  @PositiveOrZero
  private Integer numberOfChipsOut;

  @Column
  @PositiveOrZero
  private Integer numberOfBarriersFormed;

  @Column
  @PositiveOrZero
  private Integer numberOfEndChips;
  
  @Column
  @PositiveOrZero
  private Integer numberOfBarrierRebound;

    
  @Column
  @PositiveOrZero
  private Integer numberOfChipsEaten;
}
