package com.github.SimonXianyu.codefather.templates;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

/**
 * Test class
 * Created by simon on 14-8-6.
 */
public class TemplateCollectorTest {

    private TemplateCollector collector;

    @Test
    public void testCollect() {
        String workDir = System.getProperty("user.dir");
        collector = TemplateCollector.createInstance(workDir+"/src/test/codefather/templates");
        collector.collect();

        assertTrue(collector.getNode()!=null);
        TemplateNode node = collector.getNode().getChild("db");
        assertNotNull(node);
        assertEquals("db", node.getPath());
        assertEquals(2,node.getTemplateDefMap().size());
    }
}
