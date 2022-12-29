package com.ling1.springmvc.parchistile;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class ParchisTileTypeFormatter implements Formatter<ParchisTileType> {

	private final ParchisTileService parchisTileService;

	@Autowired
	public ParchisTileTypeFormatter(ParchisTileService parchisTileService) {
		this.parchisTileService = parchisTileService;
	}

	@Override
	public String print(ParchisTileType tileType, Locale locale) {
		return tileType.getName();
	}

	@Override
	public ParchisTileType parse(String text, Locale locale) throws ParseException {
		Collection<ParchisTileType> findtileTypes = this.parchisTileService.findParchisTileTypes();
		for (ParchisTileType type : findtileTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("parchistiletype not found: " + text, 0);
	}

}
