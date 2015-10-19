package io.github.SimonXianyu.codefather.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Change argument to lower case underline string.
 * Created by simon on 2014/12/8.
 */
public class LowUnderMethod implements TemplateMethodModel {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size()!=1) {
            throw new TemplateModelException("Wrong argument");
        }
        Object a = list.get(0);
        if (!(a instanceof String)) {
            return null;
        }
        String str = (String) a;
        StringBuilder strb = new StringBuilder();
        StringBuilder part = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if ('A' <= ch && 'Z' >= ch) {
                if (part.length()>0) {
                    if (strb.length()>0) {
                        strb.append('_');
                    }
                    strb.append(part);
                    part.delete(0, part.length());
                }
            }
            part.append(Character.toLowerCase(ch));
        }
        if (part.length()>0) {
            if (strb.length()>0) {
                strb.append('_');
            }
            strb.append(part);
        }
        return strb.toString();
    }
}
