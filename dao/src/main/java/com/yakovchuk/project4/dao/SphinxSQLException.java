package com.yakovchuk.project4.dao;

public class SphinxSQLException extends RuntimeException {

    public SphinxSQLException() {
    }

    public SphinxSQLException(String message) {
        super(message);
    }

    public SphinxSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public SphinxSQLException(Throwable cause) {
        super(cause);
    }

    public SphinxSQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
