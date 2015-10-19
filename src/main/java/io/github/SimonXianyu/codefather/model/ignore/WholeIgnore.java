package io.github.SimonXianyu.codefather.model.ignore;

/**
 *
 * Created by simon on 2014/12/4.
 */
public class WholeIgnore extends IgnoreMethod {
    private String pattern;

    public WholeIgnore(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean match(String str) {
        if (pattern.equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }
}
