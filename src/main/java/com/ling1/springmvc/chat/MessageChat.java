package com.ling1.springmvc.chat;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import com.ling1.springmvc.match.Match;
import com.ling1.springmvc.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MessageChat {
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

    @Column
    @NotEmpty
    private String description;

    @Column
    @NotEmpty
    private String time;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
