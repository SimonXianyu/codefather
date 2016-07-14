package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.LocalUtil;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

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

    @Test
    public void testReadEntityWithJaxb() {
      try (InputStream in = getClass().getResourceAsStream("VideoFile.xml")) {
        EntityDef def = entityParser.parseWithJaxb("VideoFile", in);
        assertEquals("VideoFile", def.getName());
        assertEquals("videofile", def.getUrlName());
        assertEquals("videofile", def.getAttrMap().get("urlname"));
        assertEquals("false", def.getAttrMap().get("ignoreJspIncList"));
        assertNotNull(def.getPropertyList());
        assertTrue(def.getPropertyList().size()>0);
        assertNotNull(def.getKeyDef());
        KeyDef keyDef = def.getKeyDef();
        assertEquals("id", keyDef.getName());
        assertEquals("long", keyDef.getType());
        PropertyDef propDef = def.getPropertyOf("video_set_id");
        assertNotNull(propDef);
        assertEquals("long", propDef.getType());
        assertEquals("hidden", propDef.getAttrMap().get("inputType"));

        propDef = def.getPropertyOf("play_type");
        assertEquals("1:m3u8", propDef.getAttrMap().get("valueList"));
      } catch (IOException e) {
        e.printStackTrace();
        fail("parse error");
      }
    }
}
