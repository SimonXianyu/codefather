package io.github.SimonXianyu.codefather.model;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * This class is used to collect Entity definitions in specified directory
 * Created by simon on 2014/12/1.
 */
public class EntityCollector extends AbstractDefinitionCollector<EntityDef> {

    private EntityParser parser = new EntityParser();

    public EntityCollector(File targetDir) {
        this(targetDir, true);
    }

    public EntityCollector(File targetDir, boolean required) {
        super(targetDir);
        if (required &&(!baseDir.exists() || !baseDir.isDirectory())) {
            throw new IllegalArgumentException("Wrong path for entities directory under "+targetDir.getPath());
        }
    }

    @Override
    protected void onCollected() {
        for(EntityDef def : defList) {
            if (StringUtils.isNotBlank(def.getParent())) {
                EntityDef parentDef = getDefByName(def.getParent());
                if (null == parentDef) {
                    throw new IllegalArgumentException("No parent found :" +def.getParent()+" for "+def.getName());
                }
                def.setParentDef(parentDef);
            }
        }
    }

    @Override
    protected EntityDef doParse(String path, File f) {
        EntityDef def = parser.parse(f);
        def.setPath(path);
        defMap.put(def.getName(), def);
        return def;
    }

}
