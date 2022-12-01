package com.ling1.springmvc.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Achievement {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @Column(unique=true, nullable = false)
    private String name;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name ="condition_id")
    private Condition condition;    

    @Column
    private String imgUrl;
}
