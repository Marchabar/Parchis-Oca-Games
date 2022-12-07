package com.ling1.springmvc.friend;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

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
    private User User1;

    @ManyToOne
    private User User2;

    @ManyToOne 
    private User solicitingUser;

    @Column
    private Boolean Accept;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotEmpty
    private LocalDate dateF;
}
