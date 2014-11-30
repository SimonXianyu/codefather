package com.github.SimonXianyu.codefather.model;

import com.github.SimonXianyu.codefather.util.DigesterHelper;
import com.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.commons.digester3.Digester;
import org.apache.maven.plugin.logging.Log;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * This class is used to read and parse EntitySchema.xml.
 * Created by simon on 2014/11/28.
 */
public class EntitySchemaParser {
    private Log log;

    public EntitySchemaParser() {
    }

    public EntitySchemaParser(Log log) {
        this.log = log;
    }

    public EntitySchema parseSchema(InputStream inputStream) throws IOException, SAXException {
        Digester digester = createDigester();
        return digester.parse(inputStream);
    }

    private Digester createDigester() {
        DigesterHelper dh = new DigesterHelper();
        dh.createSetObject("entity-desc", EntitySchema.class)
                .createSetChild("entity-desc/entity", HashMap.class, "setModelAttributeMap")
            .addMapChild("entity-desc/entity/attr", AttributeDef.class, "name")
            .createSetChild("entity-desc/property", HashMap.class, "setPropertyAttributeMap")
            .addMapChild("entity-desc/property/attr", AttributeDef.class, "name")
        ;
        return dh.getDigester();
    }

    public static void main(String[] args ) {
        EntitySchemaParser seParser = new EntitySchemaParser();

        InputStream in = null;
        try {
            in = EntitySchemaParser.class.getResourceAsStream("EntitySchema.xml");
            EntitySchema es = seParser.parseSchema(in);
            System.out.println("Table attributes:" + es.getModelAttributes().size());
            System.out.println("table attribute table " + es.getModelAttributes().get("table").toString());
            System.out.println("Property attributes:" + es.getPropertyAttributes().size());
            System.out.println("Property attribute name:" + es.getPropertyAttributes().get("name"));
            System.out.println("Property attribute name:" + es.getPropertyAttributes().get("name"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            LocalUtil.closeQuietly(in);
        }
    }
}
