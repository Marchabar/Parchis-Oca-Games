package com.ling1.springmvc.parchistile;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ParchisTile {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision = 10)
	private Integer id;

    @ManyToOne
	@JoinColumn(name ="tiletype_id")
	private ParchisTileType type;

	private Boolean safe;
    
}
