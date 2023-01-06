package com.ling1.springmvc.user;

import java.util.List;
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
import javax.validation.constraints.NotEmpty;

import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerStats;
import com.ling1.springmvc.friend.*;
import com.ling1.springmvc.lobby.Lobby;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @Column(unique=true, nullable = false)
    @NotEmpty
    private String login;
    
    @Column(nullable = false)
    @NotEmpty
    private String password;

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private UserStatusEnum userStatus;

    @Column(nullable = false)
    @NotEmpty
    private String role;

    @ManyToOne
	@JoinColumn(name ="prefColor_id")
	private PlayerColor prefColor;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "user1")
    private Set<Friend> friendships1;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "user2")
    private Set<Friend> friendships2;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "user")
    private Set<PlayerStats> playerStats;
}
