package com.adi.fsmemory.uim.auth.exception;

import org.apache.shiro.authc.AuthenticationException;

public class WaitForCheckException extends AuthenticationException {

    public WaitForCheckException() {
    }

    public WaitForCheckException(String message) {
        super(message);
    }

    public WaitForCheckException(Throwable cause) {
        super(cause);
    }

    public WaitForCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
