package com.ling1.springmvc.ocaBoard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;

import com.ling1.springmvc.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class OcaPiece extends BaseEntity {

    String color;
    @Range(min=0, max=7)
    int xPosition;
    @Range(min=0, max=7)
    int yPosition;

    @ManyToOne
    OcaBoard board;

    public Integer getPositionXInPixels(Integer size){
        return (xPosition)*size;
    }
    public Integer getPositionYInPixels(Integer size){
        return (yPosition)*size;
    }
    
}
