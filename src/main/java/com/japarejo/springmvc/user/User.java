package com.japarejo.springmvc.user;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@Getter
@Setter
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=10)
	private long id;

	@Column(nullable=false, length=100, unique=true)
	private String login;

	@Column(nullable=false, length=100)
	private String password;

	@Column(nullable=false, length=100)
	private String role;

}