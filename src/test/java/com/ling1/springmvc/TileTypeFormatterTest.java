package com.ling1.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ling1.springmvc.ocatile.OcaTileService;
import com.ling1.springmvc.ocatile.TileType;
import com.ling1.springmvc.ocatile.TileTypeFormatter;

import static org.mockito.BDDMockito.given;

import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
class TileTypeFormatterTest {

    @Mock
    OcaTileService ots;
    
    TileTypeFormatter ttf;

    TileType type1;
    TileType type2;

    @BeforeEach
    void setup() {
        ttf = new TileTypeFormatter(ots);

        type1 = new TileType();
        type1.setName("OCA");
        type1.setId(1);

        type2 = new TileType();
        type2.setName("NORMAL");
        type2.setId(2);
    }

    @Test
    public void testPrint() {
        assertEquals("OCA", ttf.print(type1, null));
        assertEquals("NORMAL", ttf.print(type2, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"OCA", "NORMAL"})
    public void testParseSuccess(String input) throws Exception {
        given(ots.findOcaTileTypes()).willReturn(Lists.newArrayList(type1, type2));

        TileType tt = ttf.parse(input, null);
        assertNotNull(tt);
    }

    @Test
    public void testParseCorrect() throws Exception {
        given(ots.findOcaTileTypes()).willReturn(Lists.newArrayList(type1, type2));

        TileType tt = ttf.parse("OCA", null);
        assertEquals("OCA", tt.getName());
        assertEquals(1, tt.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"BRIDGE", "MAZE"})
    public void testParseFail(String input) {
        given(ots.findOcaTileTypes()).willReturn(Lists.newArrayList(type1, type2));
        
        assertThrows(ParseException.class, () -> {
            ttf.parse(input, null);
        });
    }

}