package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.DigesterHelper;
import org.apache.commons.digester3.Digester;

/**
 * Class to parse entity definition.
 * User: Simon Xianyu
 */
public class EntityParser extends AbstractDefParser<EntityDef> {

    public EntityParser() {
    }

    @Override
    protected void processName(String name, EntityDef def) {
        def.setName(name);
    }

}
