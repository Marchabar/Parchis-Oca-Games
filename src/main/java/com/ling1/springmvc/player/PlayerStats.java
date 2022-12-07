package com.ling1.springmvc.player;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

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
  @PositiveOrZero
  @Column
  private Integer numberOfGooses;
  @PositiveOrZero
  @Column
  private Integer numberOfPlayerWells;
  @PositiveOrZero
  @Column
  private Integer numberOfLabyrinths;
  @PositiveOrZero
  @Column
  private Integer numberOfPlayerPrisons;
  @PositiveOrZero
  @Column
  private Integer numberOfPlayerDeaths;
  @PositiveOrZero
  @Column
  private Integer numberOfInns;

}
