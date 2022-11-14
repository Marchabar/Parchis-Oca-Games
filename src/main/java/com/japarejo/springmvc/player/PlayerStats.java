package com.japarejo.springmvc.player;

import com.japarejo.springmvc.match.Match;
import com.japarejo.springmvc.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;


@Getter
@Setter
@Entity

public class PlayerStats{
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(unique=true, nullable=false)
  private int id;

  @PositiveOrZero //can be 0, if game quit
  @Column(name="numTurnsPlayer")
  private Integer numTurnsPlayer;

  @PositiveOrZero //can be 0, if game quit
  @Column(name="numDiceRolls")
  private Integer numDiceRolls;

  @ManyToOne
	@JoinColumn(name ="playerColor_id")
	private PlayerColor playerColor;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

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
