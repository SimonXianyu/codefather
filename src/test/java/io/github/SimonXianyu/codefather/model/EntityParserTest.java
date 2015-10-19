package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.LocalUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test
 * Created by simon on 2014/12/1.
 */
public class EntityParserTest {
    private EntityParser entityParser;

    @Before
    public void setUp() {
        entityParser = new EntityParser();
    }

    @Test
    public void testParse() {
        InputStream in =null;
        try {
            in = getClass().getResourceAsStream("VideoFile.xml");
            EntityDef def = entityParser.parse("VideoFile", in);
            assertEquals("VideoFile", def.getName());
            assertEquals("videofile", def.getUrlName());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to read test file");
        } finally {
            LocalUtil.closeQuietly(in);
        }
    }
}
