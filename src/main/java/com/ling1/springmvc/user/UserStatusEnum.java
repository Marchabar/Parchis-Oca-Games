package com.ling1.springmvc.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ling1.springmvc.model.NamedEntity;

@Entity
@Table(name="status")
public class UserStatusEnum extends NamedEntity{

}
