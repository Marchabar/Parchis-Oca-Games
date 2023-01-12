package com.ling1.springmvc;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ling1.springmvc.player.PlayerColor;
import com.ling1.springmvc.player.PlayerColorFormatter;
import com.ling1.springmvc.player.PlayerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
public class PlayerColorFormatterTest {

    @Mock
    PlayerService ps;

    PlayerColorFormatter pcm;

    PlayerColor pc1;
    PlayerColor pc2;

    @BeforeEach
    public void setup() {
        this.pcm = new PlayerColorFormatter(ps);

        pc1 = new PlayerColor();
        pc1.setId(1);
        pc1.setName("GREEN");
        pc2 = new PlayerColor();
        pc2.setId(2);
        pc2.setName("WHITE");
    }

    @Test
    public void testPrint() {
        assertEquals("GREEN", pcm.print(pc1, null));
        assertEquals("WHITE", pcm.print(pc2, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"GREEN", "WHITE"})
    public void testParseSuccess(String input) throws Exception {
        given(ps.findColors()).willReturn(Lists.newArrayList(pc1, pc2));
        PlayerColor c = pcm.parse(input, null);
        assertNotNull(c);
    }

    @Test
    public void testParseCorrect() throws Exception {
        given(ps.findColors()).willReturn(Lists.newArrayList(pc1, pc2));

        PlayerColor c = pcm.parse("GREEN", null);
        assertEquals("GREEN", c.getName());
        assertEquals(1, c.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"YELLOW","RED"})
    public void testParseFail(String input) {
        given(ps.findColors()).willReturn(Lists.newArrayList(pc1, pc2));
        
        assertThrows(ParseException.class, () -> {
            pcm.parse(input, null);
        });
    }
    
}
