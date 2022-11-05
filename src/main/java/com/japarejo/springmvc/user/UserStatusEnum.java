package com.japarejo.springmvc.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.japarejo.springmvc.model.NamedEntity;

@Entity
@Table(name="status")
public class UserStatusEnum extends NamedEntity{

}
