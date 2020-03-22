package com.adi.fsmemory.uim.auth.exception;

public class SearchIdRepetitionException extends RuntimeException {


    public SearchIdRepetitionException(String message) {
        super(message);
    }

    public SearchIdRepetitionException(Throwable cause) {
        super(cause);
    }

    public SearchIdRepetitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
