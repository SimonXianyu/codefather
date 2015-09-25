package com.github.SimonXianyu.codefather.model.funcmodule;

import com.github.SimonXianyu.codefather.model.Described;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class FuncDef extends Described {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("    func["+name+"]");
    }
}
