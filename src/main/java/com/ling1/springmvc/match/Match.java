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
    private Integer numMatchKills;
    @Column
    private Integer numMatchBarriers;
    @Column
    private Integer numMatchSpecialTiles;
    @Column
    private Integer totalDistanceGooses;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby; 

    @OneToOne(optional=true)
    private PlayerStats playerToPlay;

    @OneToMany
    private Collection<PlayerStats> playerStats;

    

    
}
