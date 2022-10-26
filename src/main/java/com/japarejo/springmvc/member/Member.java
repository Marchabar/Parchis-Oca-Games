package com.japarejo.springmvc.member;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.japarejo.springmvc.board.Board;

import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=10)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Board> boards;
		
}
