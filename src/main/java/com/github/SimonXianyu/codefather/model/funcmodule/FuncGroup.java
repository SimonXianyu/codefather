package com.github.SimonXianyu.codefather.model.funcmodule;

import com.github.SimonXianyu.codefather.model.Described;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class FuncGroup extends Described {
    private String name;
    private List<FuncDef> funcs = new ArrayList<FuncDef>();

    public void addFunc(FuncDef def) {
        funcs.add(def);
    }

    public List<FuncDef> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<FuncDef> funcs) {
        this.funcs = funcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("  group["+name+"]: {");
        for(FuncDef f : funcs) {
            f.print();
        }
        System.out.println("  }");
    }
}
