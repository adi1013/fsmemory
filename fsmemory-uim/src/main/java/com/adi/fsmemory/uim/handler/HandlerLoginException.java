package com.adi.fsmemory.uim.handler;

import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.auth.exception.WaitForCheckException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerLoginException {


    @ExceptionHandler(UnknownAccountException.class)
    public RestResultObj unknownAccountException() {
        return RestResultType.UN_KNOWN_ACCOUNT.toRestfulResult(null);
    }


    @ExceptionHandler(IncorrectCredentialsException.class)
    public RestResultObj incorrectCredentialsException() {
        return RestResultType.INCORRECT_CREDENTIALS.toRestfulResult(null);
    }


    @ExceptionHandler(UnauthenticatedException.class)
    public RestResultObj unauthenticatedException() {
        return RestResultType.UN_AUTHENTICATED.toRestfulResult(null);
    }

    @ExceptionHandler(WaitForCheckException.class)
    public RestResultObj waitForCheckException() {
        return RestResultType.WAIT_FOR_CHECK.toRestfulResult(null);
    }


}
