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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameEnumFormatterTest {

    GameEnumFormatter gameEnumFormatter;

    @Mock
    LobbyService lobbyService;

    @BeforeEach
	void setup() {
		gameEnumFormatter = new GameEnumFormatter(lobbyService);
	}

    @Test
    void testPrint()
    {
        GameEnum gameEnum = new GameEnum();
        gameEnum.setName("GAME");
        String name = gameEnumFormatter.print(gameEnum, Locale.ENGLISH);
        assertEquals("GAME", name);
    }
    @Test
    void testParse() throws ParseException
    {
		Mockito.when(lobbyService.findGameTypes()).thenReturn(makeGameTypes());   
        GameEnum gameEnum = gameEnumFormatter.parse("TheBestGame", Locale.ENGLISH);
        assertNotNull(gameEnum);
        assertEquals("TheBestGame", gameEnum.getName());
    }
    @Test
    void ntestParseWrong() throws ParseException
    {
		Mockito.when(lobbyService.findGameTypes()).thenReturn(makeGameTypes());   
        assertThrows(ParseException.class, ()->gameEnumFormatter.parse("SomethingNotExpected", Locale.ENGLISH));
    }

    private Collection<GameEnum> makeGameTypes() {
		Collection<GameEnum> gameTypes = new ArrayList<>();
		gameTypes.add(new GameEnum() {
			{
				setName("Game 123");
			}
		});
		gameTypes.add(new GameEnum() {
			{
				setName("TheBestGame");
			}
		});
		return gameTypes;
	}
    
}
