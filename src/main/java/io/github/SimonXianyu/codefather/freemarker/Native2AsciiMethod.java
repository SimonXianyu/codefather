package io.github.SimonXianyu.codefather.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Implement freemarker method of Native2Ascii.
 * User: Simon Xianyu
 * Date: 13-6-27
 * Time: 下午12:02
 */
public class Native2AsciiMethod implements TemplateMethodModel {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size()!=1) {
            throw new TemplateModelException("Wrong argument");
        }
        Object a = list.get(0);

        StringBuilder strb = new StringBuilder();
        if (a instanceof String) {
            String str = (String) a;
            for( int i =0; i<str.length();++i) {
                char chr = str.charAt(i);
                if (chr > 0xff) {
                    strb.append("\\u");
                    strb.append(Integer.toHexString(chr));
                } else {
                    strb.append(chr);
                }
            }
        }
        return strb.toString();
    }
}
