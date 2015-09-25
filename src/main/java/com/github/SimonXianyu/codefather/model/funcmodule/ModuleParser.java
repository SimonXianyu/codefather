package com.github.SimonXianyu.codefather.model.funcmodule;

import com.github.SimonXianyu.codefather.model.AbstractDefParser;
import com.github.SimonXianyu.codefather.model.PropertyDef;
import com.github.SimonXianyu.codefather.util.DigesterHelper;
import org.apache.commons.digester3.Digester;

import java.io.IOException;

/**
 * Use digester to parse module definition xml.
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class ModuleParser extends AbstractDefParser<ModuleDef> {


    @Override
    protected void processName(String name, ModuleDef def) {
        def.setName(name);
    }

    @Override
    protected Digester createDig() {
        DigesterHelper dh = new DigesterHelper();
        dh.createSetObject("module", ModuleDef.class)
                .setBodyText("module/description", "setDescription")
                .createSetChild("module/group", FuncGroup.class, "addGroup")
                .createSetChild("module/group/func", FuncDef.class, "addFunc")
                .createSetChild("module/func", PropertyDef.class, "addFunc")
        ;
        return dh.getDigester();
    }

    public static void main(String[] args) {
        ModuleParser mp = new ModuleParser();
        try {
            ModuleDef moduleDef =  mp.parse("SysUser", ModuleParser.class.getResourceAsStream("/SysUser.xml"));
            moduleDef.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
