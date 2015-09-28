package com.github.SimonXianyu.codefather.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Test class of @see EntityCollector
 * Created by simon on 2014/12/2.
 */
public class EntityCollectorTest {
    private EntityCollector collector;
    private String workPath;

    @Before
    public void setUp() {
        workPath = System.getProperty("user.dir");
    }

    @Test
    public void testCollectSimple() {
        collector = new EntityCollector(new File(workPath, "src/test/entity-test/simple"));
        collector.collect();
        assertEquals(1, collector.countEntity());
        assertNotNull(collector.getDefByName("VideoFile"));
    }

    @Test
    public void testInPath() {
        collector = new EntityCollector(new File(workPath, "src/test/entity-test/inpath"));
        collector.collect();
        assertTrue(collector.countEntity()>0);
        EntityDef entityManUser = collector.getDefByName("ManUser");
        assertNotNull(entityManUser);
        assertEquals("sys",entityManUser.getPath());
    }
}
