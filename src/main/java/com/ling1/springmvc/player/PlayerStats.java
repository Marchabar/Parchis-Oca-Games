package com.ling1.springmvc.player;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.ling1.springmvc.user.User;


@Getter
@Setter
@Entity

public class PlayerStats{
  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

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
  private Integer turnsStuck;
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
    @Column
    private Integer numberOfInns;
    
}
