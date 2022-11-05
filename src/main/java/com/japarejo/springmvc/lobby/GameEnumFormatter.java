package com.japarejo.springmvc.lobby;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class GameEnumFormatter implements Formatter<GameEnum> {

	private final LobbyService lobbyService;

	@Autowired
	public GameEnumFormatter(LobbyService lobbyService) {
		this.lobbyService = lobbyService;
	}

	@Override
	public String print(GameEnum gameType, Locale locale) {
		return gameType.getName();
	}

	@Override
	public GameEnum parse(String text, Locale locale) throws ParseException {
		Collection<GameEnum> findGameTypes = this.lobbyService.findGameTypes();
		for (GameEnum type : findGameTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("game not found: " + text, 0);
	}

}
