package io.github.SimonXianyu.codefather.model.funcmodule;

import io.github.SimonXianyu.codefather.model.Described;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class FuncDef extends Described {
    private String name;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void print() {
        System.out.println("    func["+name+"]");
    }
}
