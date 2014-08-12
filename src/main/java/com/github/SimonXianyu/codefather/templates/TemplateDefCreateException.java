package com.github.SimonXianyu.codefather.templates;

/**
 * Define an exception to be thrown out.
 * Created by simon on 14-8-8.
 */
public class TemplateDefCreateException extends Exception {
    public TemplateDefCreateException() {
    }

    public TemplateDefCreateException(String message) {
        super(message);
    }

    public TemplateDefCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateDefCreateException(Throwable cause) {
        super(cause);
    }

    public TemplateDefCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
