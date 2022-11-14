package com.japarejo.springmvc.player;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.japarejo.springmvc.model.NamedEntity;

@Entity
@Table(name = "colors")
public class PlayerColor extends NamedEntity {

}


