package com.adi.fsmemory.uim.handler;

import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.auth.exception.SearchIdRepetitionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerGroupException {

    @ExceptionHandler(SearchIdRepetitionException.class)
    public RestResultObj searchIdRepetitionException(){
        return RestResultType.SEARCH_ID_REPETITION.toRestfulResult(null);
    }
}
