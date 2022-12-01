package com.ling1.springmvc.achievement;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ling1.springmvc.model.NamedEntity;

@Entity
@Table(name ="achievement_properties")
public class AchievementProperty extends NamedEntity {
}
