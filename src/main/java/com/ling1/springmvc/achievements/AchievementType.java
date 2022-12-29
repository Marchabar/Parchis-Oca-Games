package com.ling1.springmvc.achievements;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ling1.springmvc.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

    @Entity
    @Getter
    @Setter
    @Table(name = "achievementtypes")
    public class AchievementType extends NamedEntity {

    }

