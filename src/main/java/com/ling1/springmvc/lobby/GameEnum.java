package com.ling1.springmvc.lobby;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ling1.springmvc.model.NamedEntity;


@Entity
@Table(name = "games")
public class GameEnum extends NamedEntity {

}
