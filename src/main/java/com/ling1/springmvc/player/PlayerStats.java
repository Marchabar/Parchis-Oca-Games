package com.ling1.springmvc.player;

import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.ling1.springmvc.chip.Chip;
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
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
<<<<<<< HEAD
<<<<<<< HEAD

  private Integer numberOfInns;

=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
  private Integer numberOfInns;

  
  @OneToMany
  private List<Chip> chips;
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
}
