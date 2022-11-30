package com.ling1.springmvc.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ling1.springmvc.player.PlayerColor;

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
    private String login;
    
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private UserStatusEnum userStatus;

    @Column(nullable = false)
    private String role;

    @ManyToOne
	@JoinColumn(name ="prefColor_id")
	private PlayerColor prefColor;

}
