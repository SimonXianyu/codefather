package com.github.SimonXianyu.codefather.model;

import com.github.SimonXianyu.codefather.util.DigesterHelper;
import com.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class to parse entity definition.
 * User: Simon Xianyu
 */
public class EntityParser {

    public EntityParser() {
    }

    /**
     * Parse entity with specified file.
     * The filename (without extension ) will be used as default name.
     **/
    public EntityDef parse(File sourceFile) {
        String name = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf('.'));
        InputStream in = null;
        try {
            in = new FileInputStream(sourceFile);
            return this.parse(name, in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse while reading file:"+sourceFile.getName(),e);
        } finally {
            LocalUtil.closeQuietly(in);
        }
    }
    public EntityDef parse(String name, InputStream in) throws IOException {
        Digester dig = createDig();
        try {
            Object resultObj = dig.parse(in);
            if (!(resultObj instanceof EntityDef) ){
                throw new RuntimeException("Failed to parse entity :"+name);
            }
            EntityDef def = (EntityDef) resultObj;
            def.setName(name);
            return def;
        } catch (SAXException e) {
            throw new RuntimeException("Failed to parse because xml error in entity :"+name,e);
        }
    }

    private Digester createDig() {
        DigesterHelper dh = new DigesterHelper();
        dh.createSetObject("entity", EntityDef.class)
                .setBodyText("entity/description", "setDescription")
            .createSetChild("entity/key", KeyDef.class, "addProperty")
            .createSetChild("entity/property", PropertyDef.class, "addProperty")
        ;

        return dh.getDigester();
    }
}
