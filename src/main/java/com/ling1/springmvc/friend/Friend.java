package com.ling1.springmvc.friend;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.ling1.springmvc.user.User;

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
    
    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @ManyToOne 
    private User solicitingUser;

    @Column
    private Boolean accept;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateF;
}
