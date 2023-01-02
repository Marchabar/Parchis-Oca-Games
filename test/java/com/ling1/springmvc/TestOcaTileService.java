package com.ling1.springmvc;

import com.ling1.springmvc.friend.Friend;
import com.ling1.springmvc.friend.FriendService;
import com.ling1.springmvc.ocatile.OcaTile;
import com.ling1.springmvc.ocatile.OcaTileService;
import com.ling1.springmvc.ocatile.TileType;
import com.ling1.springmvc.user.User;
import com.ling1.springmvc.user.UserService;

import org.assertj.core.util.Lists;
import org.hibernate.validator.constraints.SafeHtml.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Sql({"/test-data.sql"})
public class TestOcaTileService {

    @Autowired
    OcaTileService ocaTileService;

    @Test
    void testGetAllTiles() {
        List<OcaTile> tiles = ocaTileService.getAllTiles();
        assertEquals(63, tiles.size());
    }

    @Test
    void testDeleteTile() {
        ocaTileService.deleteTile(1);
        List<OcaTile> tiles = ocaTileService.getAllTiles();
        assertEquals(62, tiles.size());
    }

    @Test
    void testGetOcaTileById() {
        OcaTile oca31 = ocaTileService.getOcaTileById(31);
        assertEquals(31, oca31.getId());
        assertEquals("WELL", oca31.getType().getName());
    }

    @Test
    void testFindOcaTileTypes() {
        Collection<TileType> tileTypes = ocaTileService.findOcaTileTypes();
        assertEquals(10, tileTypes.size());
        Set<String> names = tileTypes.stream().map(t -> t.getName()).collect(Collectors.toSet());
        assertTrue(names.contains("WELL"));
    }
	
    @Test
    void testFindTileTypeByPosition() {
        OcaTile tile = ocaTileService.findTileTypeByPosition(31);
        assertEquals(31, tile.getId());
    }

    @Test
    void testSave() {
        TileType type = ocaTileService.findOcaTileTypes().stream().findFirst().get();
        OcaTile tile = new OcaTile();
        tile.setType(type);
        tile = ocaTileService.save(tile);
        assertEquals(64, tile.getId());
    }

    @Test
    void testNextOca() {
        Integer next = ocaTileService.nextOca(34);
        assertEquals(36, next);
    }

    @Test
    void testAllOcas() {
        Collection<OcaTile> allOcas = ocaTileService.allOcas();
        assertEquals(14, allOcas.size());
        Set<String> typeNames = allOcas.stream().map(t -> t.getType().getName()).collect(Collectors.toSet());
        assertEquals(1, typeNames.size());
        assertTrue(typeNames.contains("OCA"));
    }

    @Test
    void testOtherBridge() {
        int otherBridge = ocaTileService.otherBridge(6);
        assertEquals(12, otherBridge);

    }

	@Test
    void testOtherDice() {
        int otherDice = ocaTileService.otherDice(26);
        assertEquals(53, otherDice);
    }
	
    @Test
    void testTilesFromPosition() {
        List<String> names = ocaTileService.tilesFromPosition(Lists.newArrayList(1,2,6,63));
        assertEquals("OCA", names.get(0));
        assertEquals("NORMAL", names.get(1));
        assertEquals("BRIDGE", names.get(2));
        assertEquals("END", names.get(3));

    }

}
