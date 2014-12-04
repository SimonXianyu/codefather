package com.github.SimonXianyu.codefather.model.ignore;

/**
 * Factory
 * Created by simon on 2014/12/4.
 */
public class IgnoreFactory {
    public static IgnoreMethod createMethod(String pattern) {
        if (null == pattern || "".equals(pattern)) {
            return null;
        }
        if (pattern.contains("*")) {
            int pos = pattern.indexOf("*");
            return new HeadIgnore(pattern.substring(0,pos));
        }
        return new WholeIgnore(pattern);
    }
}
