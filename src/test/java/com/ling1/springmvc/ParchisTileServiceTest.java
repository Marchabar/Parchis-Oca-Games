package com.ling1.springmvc;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import com.ling1.springmvc.ocatile.TileType;
import com.ling1.springmvc.parchistile.ParchisTile;
import com.ling1.springmvc.parchistile.ParchisTileService;
import com.ling1.springmvc.parchistile.ParchisTileType;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class ParchisTileServiceTest {

    @Autowired
    ParchisTileService parchisTileService;

    @MockBean
    SessionRegistry sr;

    @Test
    void testGetAllTiles() {
        List<ParchisTile> tiles = parchisTileService.getAllTiles();
        assertEquals(100, tiles.size());
    }

    @Test
    void testDeleteTile() {
        parchisTileService.deleteTile(1);
        parchisTileService.deleteTile(5);
        List<ParchisTile> tiles = parchisTileService.getAllTiles();
        assertEquals(98, tiles.size());
        Set<Integer> ids = tiles.stream().map(t -> t.getId()).collect(Collectors.toSet());
        assert(!ids.contains(1));
        assert(!ids.contains(5));
        assert(ids.contains(99));

    }

    @Test
    void testGetOcaTileById() {
        ParchisTile tile = parchisTileService.getOcaTileById(20);
        assert(20 == tile.getId());
    }

    @Test
    void testFindParchisTileTypes() {
        Collection<ParchisTileType> tiles = parchisTileService.findParchisTileTypes();
        assertEquals(16, tiles.size());
    }

    @ParameterizedTest
    @CsvSource({"5,STARTRED", "6,NORMALRED", "20,NORMALBLUE"})
    void testFindTileTypeByPosition(String pos, String typename) {
        ParchisTile tile = parchisTileService.findTileTypeByPosition(Integer.parseInt(pos));

        assertEquals(typename, tile.getType().getName());
    }

    @Test
    void testSave() {
        Collection<ParchisTileType> types = parchisTileService.findParchisTileTypes();
        //ParchisTileType type = types.stream().findFirst().get();
        ParchisTile tile = new ParchisTile();
        parchisTileService.save(tile);

        List<ParchisTile> tiles = parchisTileService.getAllTiles();
        assertEquals(101, tiles.size());
    }

    @Test
    void testSaveFail() {
        ParchisTile tile = new ParchisTile();
        tile.setId(3);
        parchisTileService.save(tile);

        List<ParchisTile> tiles = parchisTileService.getAllTiles();
        assertEquals(100, tiles.size());
    }



    
}
