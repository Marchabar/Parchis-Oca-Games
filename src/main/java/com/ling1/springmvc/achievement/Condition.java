package com.ling1.springmvc.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Condition {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    int threshold;

    @ManyToOne
    @JoinColumn(name ="property_id")
    AchievementProperty property;


    boolean isSatisfied() {
        //TODO implement
        return false;
    }
    
}
