package io.github.SimonXianyu.codefather.util;

import io.github.SimonXianyu.codefather.templates.TemplateDefCreateException;

/**
 * Created by simon on 14-8-12.
 */
public class CodeFatherException extends RuntimeException {
    private String code;

    public CodeFatherException(String code, String message) {
        super(message);
        this.code = code;
    }
    public CodeFatherException(String code, String message, TemplateDefCreateException e) {
        super(message,e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
