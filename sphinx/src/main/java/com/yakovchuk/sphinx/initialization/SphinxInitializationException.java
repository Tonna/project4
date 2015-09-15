package com.yakovchuk.sphinx.initialization;

public class SphinxInitializationException extends RuntimeException {
    public SphinxInitializationException() {
    }

    public SphinxInitializationException(String message) {
        super(message);
    }

    public SphinxInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SphinxInitializationException(Throwable cause) {
        super(cause);
    }

    public SphinxInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
