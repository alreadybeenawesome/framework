package com.centryOf22th.framework.exception;

/**
 * Created by louis on 16-11-1.
 */
public class WithoutAnnotationException extends RuntimeException{


    public WithoutAnnotationException() {
        super();
    }

    public WithoutAnnotationException(String message) {
        super(message);
    }

    public WithoutAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithoutAnnotationException(Throwable cause) {
        super(cause);
    }

    protected WithoutAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
