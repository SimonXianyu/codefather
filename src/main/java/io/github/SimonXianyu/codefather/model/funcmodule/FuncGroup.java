package io.github.SimonXianyu.codefather.model.funcmodule;

import io.github.SimonXianyu.codefather.model.Described;
import io.github.SimonXianyu.codefather.model.NamedDef;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class FuncGroup extends NamedDef {
    /** default show text */
    private String text;
    private String[] menus;
    private List<FuncDef> menuRef = new ArrayList<FuncDef>();
    private List<FuncDef> funcs = new ArrayList<FuncDef>();

    public void setMenu(String s) {
        menus = StringUtils.split(s, ",");

    }
    public void addFunc(FuncDef def) {
        funcs.add(def);
        if (null!=menus && ArrayUtils.contains(menus, def.getName())) {
            menuRef.add(def);
        }
    }

    public List<FuncDef> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<FuncDef> funcs) {
        this.funcs = funcs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void print() {
        System.out.println("  group["+name+"]: {");
        for(FuncDef f : funcs) {
            f.print();
        }
        System.out.println("  }");
    }
}
