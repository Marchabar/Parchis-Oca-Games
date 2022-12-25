package com.ling1.springmvc.chip;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.ling1.springmvc.player.PlayerColor;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chip {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @Column(nullable = false)
    private Integer relativePosition;

    @Column(nullable = false)
    private Integer absolutePosition;

    @ManyToOne
	@JoinColumn(name ="prefColor_id")
	private PlayerColor chipColor;
    
}
