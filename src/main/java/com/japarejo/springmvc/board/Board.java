package com.japarejo.springmvc.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.japarejo.springmvc.member.Member;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the ORGANO database table.
 * 
 */
@Entity
@Getter
@Setter
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private long id;

	@Column(nullable=false, length=100)
	private String shortname;

	@Column(nullable=false, length=100)
	private String description;


	@ManyToMany(targetEntity=Member.class,fetch=FetchType.EAGER,mappedBy = "boards")
	private List<Member> members;
	
}
