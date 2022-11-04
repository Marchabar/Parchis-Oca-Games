package com.japarejo.springmvc.user2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User2 {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

    @Column(unique=true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private UserStatusEnum userStatus;

    @Column(nullable = false)
    @Min(0)
    @Max(1)
    private Integer admin;

}
