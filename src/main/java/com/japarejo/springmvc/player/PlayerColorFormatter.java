package com.japarejo.springmvc.player;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PlayerColorFormatter implements Formatter<PlayerColor> {

	private final PlayerService playerService;

	@Autowired
	public PlayerColorFormatter(PlayerService playerService) {
		this.playerService = playerService;
	}

	@Override
	public String print(PlayerColor playerColor, Locale locale) {
		return playerColor.getName();
	}

	@Override
	public PlayerColor parse(String text, Locale locale) throws ParseException {
		Collection<PlayerColor> findGameTypes = this.playerService.findColors();
		for (PlayerColor type : findGameTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("color not found: " + text, 0);
	}

}
