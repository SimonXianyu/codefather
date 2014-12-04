package com.github.SimonXianyu.codefather.model.ignore;

/**
 * Check head string match.
 * Created by simon on 2014/12/4.
 */
public class HeadIgnore extends IgnoreMethod {
    private String headPattern;

    public HeadIgnore(String headPattern) {
        this.headPattern = headPattern;
    }

    @Override
    public boolean match(String str) {
        if (null == str) {
            return false;
        }
        if (str.startsWith(headPattern)) {
            return true;
        }
        return false;
    }
}
