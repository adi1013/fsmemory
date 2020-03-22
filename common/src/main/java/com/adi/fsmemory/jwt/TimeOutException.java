package com.adi.fsmemory.jwt;

import io.jsonwebtoken.JwtException;

public class TimeOutException extends JwtException {
    public TimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutException(String message) {
        super(message);
    }
}
