package io.github.SimonXianyu.codefather.util;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public class DefParseException extends Exception {
    public DefParseException() {
    }

    public DefParseException(String message) {
        super(message);
    }

    public DefParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefParseException(Throwable cause) {
        super(cause);
    }

    public DefParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
