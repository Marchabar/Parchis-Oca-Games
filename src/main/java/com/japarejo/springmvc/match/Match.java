package com.japarejo.springmvc.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ManyToAny;

import com.japarejo.springmvc.lobby.GameEnum;
import com.japarejo.springmvc.lobby.Lobby;
import com.japarejo.springmvc.model.BaseEntity;
import com.japarejo.springmvc.user2.User2;

import lombok.Getter;
import lombok.Setter;

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
    @Min(0)
    private Integer numTurns;

    @JoinColumn(nullable = false)
    private String winner;

    @Column
    private Integer numMatchKills;
    @Column
    private Integer numMatchBarriers;
    @Column
    private Integer numMatchSpecialTiles;
    @Column
    private Integer totalDistanceGooses;

    /*
    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby; */

    

    
}
