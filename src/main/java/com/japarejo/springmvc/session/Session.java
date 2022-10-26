package com.japarejo.springmvc.session;

import java.io.Serializable;
import javax.persistence.*;

import com.japarejo.springmvc.board.Board;
import com.japarejo.springmvc.room.Room;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fecha;

	private Board board;

	private Room sala;
	
	private SessionType sessionType;

	private Timestamp endTime;

	private Timestamp startTime;

	private Long legislature;	

}