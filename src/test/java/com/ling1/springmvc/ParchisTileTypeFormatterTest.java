package com.ling1.springmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ling1.springmvc.lobby.GameEnum;
import com.ling1.springmvc.lobby.GameEnumFormatter;
import com.ling1.springmvc.lobby.LobbyService;
import com.ling1.springmvc.parchistile.ParchisTileService;
import com.ling1.springmvc.parchistile.ParchisTileType;
import com.ling1.springmvc.parchistile.ParchisTileTypeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParchisTileTypeFormatterTest {

    ParchisTileTypeFormatter formatter;

    @Mock
    ParchisTileService parchisTileService;

    @BeforeEach
	void setup() {
		formatter = new ParchisTileTypeFormatter(parchisTileService);
	}

    @Test
    void testPrint()
    {
        ParchisTileType parchisTileType = new ParchisTileType();
        parchisTileType.setName("ANEWTILE");
        String name = formatter.print(parchisTileType, Locale.ENGLISH);
        assertEquals("ANEWTILE", name);
    }
    @Test
    void testParse() throws ParseException
    {
		Mockito.when(parchisTileService.findParchisTileTypes()).thenReturn(makeParchisTileTypes());   
        ParchisTileType parchisTileType = formatter.parse("Tile123", Locale.ENGLISH);
        assertNotNull(parchisTileType);
        assertEquals("Tile123", parchisTileType.getName());
    }
    @Test
    void ntestParseWrong() throws ParseException
    {
		Mockito.when(parchisTileService.findParchisTileTypes()).thenReturn(makeParchisTileTypes());   
        assertThrows(ParseException.class, ()->formatter.parse("SomethingNotExpected", Locale.ENGLISH));
    }

    private Collection<ParchisTileType> makeParchisTileTypes() {
		Collection<ParchisTileType> parchisTileTypes = new ArrayList<>();
		parchisTileTypes.add(new ParchisTileType() {
			{
				setName("Tile123");
			}
		});
		parchisTileTypes.add(new ParchisTileType() {
			{
				setName("TileABC");
			}
		});
		return parchisTileTypes;
	}

    
}
