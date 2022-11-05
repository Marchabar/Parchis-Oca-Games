package com.japarejo.springmvc.lobby;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.japarejo.springmvc.match.Match;
import com.japarejo.springmvc.user2.User2;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Lobby {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name ="game_id")
	private GameEnum game;

	@OneToMany
	private Collection<User2> players;

	@OneToOne(optional=true)
	private User2 host;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lobby")
	private Set<Match> matches;
}
