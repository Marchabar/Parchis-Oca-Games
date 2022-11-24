package com.ling1.springmvc.ocatile;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ling1.springmvc.model.NamedEntity;

@Entity
@Table(name = "tiletypes")
public class TileType extends NamedEntity {

}
