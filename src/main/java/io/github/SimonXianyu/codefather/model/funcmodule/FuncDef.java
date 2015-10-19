package io.github.SimonXianyu.codefather.model.funcmodule;

import io.github.SimonXianyu.codefather.model.Described;
import io.github.SimonXianyu.codefather.model.NamedDef;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class FuncDef extends NamedDef {
    private String text;

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
