package com.wjy.ioi.runtime.web.exception;

public class ServletException extends Exception {

    public ServletException(String message) {
        super(message);
    }

    public ServletException(String message, Throwable cause) {
        super(message, cause);
    }
}
