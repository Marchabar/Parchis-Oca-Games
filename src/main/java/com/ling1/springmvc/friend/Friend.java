package com.ling1.springmvc.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Friend {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @Column(nullable = false)
    private Integer idUser1;

    @Column(nullable = false)
    private Integer idUser2;
}
