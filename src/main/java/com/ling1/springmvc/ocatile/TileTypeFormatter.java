package com.ling1.springmvc.ocatile;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class TileTypeFormatter implements Formatter<TileType> {

	private final OcaTileService ocaTileService;

	@Autowired
	public TileTypeFormatter(OcaTileService ocaTileService) {
		this.ocaTileService = ocaTileService;
	}

	@Override
	public String print(TileType tileType, Locale locale) {
		return tileType.getName();
	}

	@Override
	public TileType parse(String text, Locale locale) throws ParseException {
		Collection<TileType> findtileTypes = this.ocaTileService.findOcaTileTypes();
		for (TileType type : findtileTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("tiletype not found: " + text, 0);
	}

}
