package com.github.SimonXianyu.codefather.model;

import com.github.SimonXianyu.codefather.util.DigesterHelper;
import org.apache.commons.digester3.Digester;
import org.apache.maven.plugin.logging.Log;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

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
        dh.createSetObject("entity", EntitySchema.class)
            .addMapChild("entity-desc", AttributeDef.class, "name");
        ;
        return dh.getDigester();
    }
}
