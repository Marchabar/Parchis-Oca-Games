package com.japarejo.springmvc.match;

import javax.persistence.*;
import javax.validation.constraints.Min;

import com.japarejo.springmvc.player.PlayerStats;
import com.japarejo.springmvc.player.PlayerStats;

import org.hibernate.annotations.ManyToAny;

import com.japarejo.springmvc.lobby.GameEnum;
import com.japarejo.springmvc.lobby.Lobby;
import com.japarejo.springmvc.model.BaseEntity;
import com.japarejo.springmvc.user.User;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@MappedSuperclass
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
    @Min(0)
    private Integer numTurns;

    @OneToOne(optional=true)
	private PlayerStats winner;

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

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<PlayerStats> playerStats;
    

    
}

