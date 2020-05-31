package com.trklst.zuulserver.security.excpetions;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ZuulException {

    public InvalidTokenException() {
        super("Invalid Token", HttpStatus.BAD_REQUEST.value(), null);
    }
}
