package com.japarejo.springmvc.lobby;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.japarejo.springmvc.model.NamedEntity;


@Entity
@Table(name = "games")
public class GameEnum extends NamedEntity {

}
