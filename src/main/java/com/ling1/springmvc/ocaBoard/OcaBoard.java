package com.ling1.springmvc.ocaBoard;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;

import com.ling1.springmvc.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OcaBoard extends BaseEntity{
    String background;
    @Positive
    int width;
    @Positive
    int height;

    public OcaBoard(){
        this.background = "resources/images/oca.png";
        this.width = 800;
        this.height = 800;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "board", fetch = FetchType.EAGER)
    List<OcaPiece> pieces;
    
}
