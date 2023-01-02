package com.ling1.springmvc.match;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.ling1.springmvc.lobby.GameEnum;
import com.ling1.springmvc.lobby.Lobby;
import com.ling1.springmvc.player.PlayerStats;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Match {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @ManyToOne
	@JoinColumn(name ="game_id")
	private GameEnum game;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer numTurns;

    @OneToOne(optional=true)
	private PlayerStats winner;

    @Column(nullable = false)
    private Integer lastRoll;

    @Column
    @PositiveOrZero
    private Integer numMatchKills;
    
    @Column
    @PositiveOrZero
    private Integer numMatchBarriers;

    @Column
    @PositiveOrZero
    private Integer numMatchSpecialTiles;

    @Column
    @PositiveOrZero
    private Integer totalDistanceGooses;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby; 

    @OneToOne(optional=true)
    private PlayerStats playerToPlay;

    @OneToMany
    private Collection<PlayerStats> playerStats;

<<<<<<< HEAD
<<<<<<< HEAD
    
=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
    @Column
    private Integer cheaterCounter;

    @Column
    private String Event;
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0

    
}
