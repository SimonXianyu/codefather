package com.github.SimonXianyu.codefather.model.funcmodule;

import com.github.SimonXianyu.codefather.model.AbstractDefinitionCollector;

import java.io.File;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class ModuleCollector extends AbstractDefinitionCollector<ModuleDef>
{
    private ModuleParser parser = new ModuleParser();

    public ModuleCollector(File targetDir) {
        super(targetDir);
    }

    @Override
    protected ModuleDef doParse(String path, File f) {
        ModuleDef def = parser.parse(f);
        def.setPath(path);
        return def;
    }
}
